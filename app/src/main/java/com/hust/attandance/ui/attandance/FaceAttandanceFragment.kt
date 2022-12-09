package com.hust.attandance.ui.attandance

import android.annotation.SuppressLint
import android.graphics.*
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.util.Size
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.gms.tasks.Task
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetector
import com.hust.attandance.databinding.FragmentFaceAttandanceBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import kotlinx.android.synthetic.main.fragment_face_attandance.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.tensorflow.lite.Interpreter
import java.io.ByteArrayOutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.ReadOnlyBufferException
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import kotlin.experimental.inv

class FaceAttandanceFragment :
    BaseViewBindingFragment<FragmentFaceAttandanceBinding, FaceAttandanceViewModel>(
        FragmentFaceAttandanceBinding::inflate
    ) {
    override val viewModel by viewModel<FaceAttandanceViewModel>()

    private var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>? = null

    private val SELECT_PICTURE = 1
    var cameraProvider: ProcessCameraProvider? = null
    var cameraSelector: CameraSelector? = null
    var cam_face = CameraSelector.LENS_FACING_BACK //Default Back Camera
    var detector: FaceDetector? = null
    var inputSize = 112 //Input size for model
    var intValues: IntArray
    var isModelQuantized = false
    var distance = 1.0f
    var IMAGE_MEAN = 128.0f
    var IMAGE_STD = 128.0f
    var OUTPUT_SIZE = 192 //Output size of model
    var embeedings: Array<FloatArray>
    var tfLite: Interpreter? = null
    private val registered: java.util.HashMap<String, SimilarityClassifier.Recognition> =
        java.util.HashMap<String, SimilarityClassifier.Recognition>() //saved Faces


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    //Bind camera and preview view
    private fun cameraBind() {
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture!!.addListener(Runnable {
            try {
                cameraProvider = cameraProviderFuture!!.get()
                bindPreview(cameraProvider!!)
            } catch (e: ExecutionException) {
                // No errors need to be handled for this in Future.
                // This should never be reached.
            } catch (e: InterruptedException) {
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    fun bindPreview(cameraProvider: ProcessCameraProvider) {
        val preview = Preview.Builder()
            .build()
        cameraSelector = CameraSelector.Builder()
            .requireLensFacing(cam_face)
            .build()
        preview.setSurfaceProvider(previewView.surfaceProvider)
        val imageAnalysis = ImageAnalysis.Builder()
            .setTargetResolution(Size(640, 480))
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST) //Latest frame is shown
            .build()
        val executor: Executor = Executors.newSingleThreadExecutor()
        imageAnalysis.setAnalyzer(executor) { imageProxy ->
            try {
                Thread.sleep(0) //Camera preview refreshed every 10 millisec(adjust as required)
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
            var image: InputImage? = null
            @SuppressLint("UnsafeExperimentalUsageError") val mediaImage// Camera Feed-->Analyzer-->ImageProxy-->mediaImage-->InputImage(needed for ML kit face detection)
                    = imageProxy.image
            if (mediaImage != null) {
                image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                //                    System.out.println("Rotation "+imageProxy.getImageInfo().getRotationDegrees());
            }

//                System.out.println("ANALYSIS");

            //Process acquired image to detect faces
            val result: Task<List<Face>> =
                detector?.process(image)
                    ?.addOnSuccessListener(
                        OnSuccessListener<List<Face>> { faces ->
                            if (faces.size != 0) {
                                val face = faces[0] //Get first face from detected faces
                                //                                                    System.out.println(face);

                                //mediaImage to Bitmap
                                val frame_bmp = toBitmap(mediaImage)
                                val rot = imageProxy.imageInfo.rotationDegrees

                                //Adjust orientation of Face
                                val frame_bmp1 =
                                    rotateBitmap(
                                        frame_bmp,
                                        rot,
                                        false,
                                        false
                                    )


                                //Get bounding box of face
                                val boundingBox = RectF(face.boundingBox)

                                //Crop out bounding box from whole Bitmap(image)
                                var cropped_face =
                                    getCropBitmapByCPU(
                                        frame_bmp1,
                                        boundingBox
                                    )
                                if (flipX) cropped_face =
                                    rotateBitmap(
                                        cropped_face,
                                        0,
                                        flipX,
                                        false
                                    )
                                //Scale the acquired Face to 112*112 which is required input for model
                                val scaled = getResizedBitmap(cropped_face, 112, 112)
                                if (start) recognizeImage(scaled) //Send scaled bitmap to create face embeddings.
                                //                                                    System.out.println(boundingBox);
                            } else {
                                if (registered.isEmpty()) reco_name.setText("Add Face") else reco_name.setText(
                                    "No Face Detected!"
                                )
                            }
                        })
                    .addOnFailureListener(
                        OnFailureListener {
                            // Task failed with an exception
                            // ...
                        })
                    .addOnCompleteListener(OnCompleteListener<List<Face?>?> {
                        imageProxy.close() //v.important to acquire next frame for analysis
                    })
        }
        cameraProvider.bindToLifecycle(
            (this as LifecycleOwner),
            cameraSelector,
            imageAnalysis,
            preview
        )
    }

    private fun getCropBitmapByCPU(source: Bitmap?, cropRectF: RectF): Bitmap? {
        val resultBitmap = Bitmap.createBitmap(
            cropRectF.width().toInt(),
            cropRectF.height().toInt(),
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(resultBitmap)

        // draw background
        val paint = Paint(Paint.FILTER_BITMAP_FLAG)
        paint.color = Color.WHITE
        canvas.drawRect(
            RectF(0F, 0F, cropRectF.width(), cropRectF.height()),
            paint
        )
        val matrix = Matrix()
        matrix.postTranslate(-cropRectF.left, -cropRectF.top)
        canvas.drawBitmap(source!!, matrix, paint)
        if (source != null && !source.isRecycled) {
            source.recycle()
        }
        return resultBitmap
    }


    private fun rotateBitmap(
        bitmap: Bitmap?, rotationDegrees: Int, flipX: Boolean, flipY: Boolean
    ): Bitmap? {
        val matrix = Matrix()

        // Rotate the image back to straight.
        matrix.postRotate(rotationDegrees.toFloat())

        // Mirror the image along the X or Y axis.
        matrix.postScale(if (flipX) -1.0f else 1.0f, if (flipY) -1.0f else 1.0f)
        return bitmap?.let {
            val rotatedBitmap =
                Bitmap.createBitmap(it, 0, 0, it.width, it.height, matrix, true)
            // Recycle the old bitmap if it has changed.
            if (rotatedBitmap != bitmap) {
                it.recycle()
            }
            rotatedBitmap
        }
    }

    //IMPORTANT. If conversion not done ,the toBitmap conversion does not work on some devices.
    private fun YUV_420_888toNV21(image: Image): ByteArray {
        val width = image.width
        val height = image.height
        val ySize = width * height
        val uvSize = width * height / 4
        val nv21 = ByteArray(ySize + uvSize * 2)
        val yBuffer = image.planes[0].buffer // Y
        val uBuffer = image.planes[1].buffer // U
        val vBuffer = image.planes[2].buffer // V
        var rowStride = image.planes[0].rowStride
        assert(image.planes[0].pixelStride == 1)
        var pos = 0
        if (rowStride == width) { // likely
            yBuffer[nv21, 0, ySize]
            pos += ySize
        } else {
            var yBufferPos = -rowStride.toLong() // not an actual position
            while (pos < ySize) {
                yBufferPos += rowStride.toLong()
                yBuffer.position(yBufferPos.toInt())
                yBuffer[nv21, pos, width]
                pos += width
            }
        }
        rowStride = image.planes[2].rowStride
        val pixelStride = image.planes[2].pixelStride
        assert(rowStride == image.planes[1].rowStride)
        assert(pixelStride == image.planes[1].pixelStride)
        if (pixelStride == 2 && rowStride == width && uBuffer[0] == vBuffer[1]) {
            // maybe V an U planes overlap as per NV21, which means vBuffer[1] is alias of uBuffer[0]
            val savePixel = vBuffer[1]
            try {
                vBuffer.put(1, savePixel.inv().toByte())
                if (uBuffer[0] == savePixel.inv().toByte()) {
                    vBuffer.put(1, savePixel)
                    vBuffer.position(0)
                    uBuffer.position(0)
                    vBuffer[nv21, ySize, 1]
                    uBuffer[nv21, ySize + 1, uBuffer.remaining()]
                    return nv21 // shortcut
                }
            } catch (ex: ReadOnlyBufferException) {
                // unfortunately, we cannot check if vBuffer and uBuffer overlap
            }

            // unfortunately, the check failed. We must save U and V pixel by pixel
            vBuffer.put(1, savePixel)
        }

        // other optimizations could check if (pixelStride == 1) or (pixelStride == 2),
        // but performance gain would be less significant
        for (row in 0 until height / 2) {
            for (col in 0 until width / 2) {
                val vuPos = col * pixelStride + row * rowStride
                nv21[pos++] = vBuffer[vuPos]
                nv21[pos++] = uBuffer[vuPos]
            }
        }
        return nv21
    }


    private fun toBitmap(image: Image?): Bitmap? {
        return image?.let {
            val nv21: ByteArray = YUV_420_888toNV21(it)
            val yuvImage = YuvImage(nv21, ImageFormat.NV21, it.width, it.height, null)
            val out = ByteArrayOutputStream()
            yuvImage.compressToJpeg(Rect(0, 0, yuvImage.width, yuvImage.height), 75, out)
            val imageBytes = out.toByteArray()
            //System.out.println("bytes"+ Arrays.toString(imageBytes));

            //System.out.println("FORMAT"+image.getFormat());
            BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        }
    }

    private fun getResizedBitmap(bm: Bitmap?, newWidth: Int, newHeight: Int): Bitmap? {
        return bm?.let {
            val width = it.width
            val height = it.height
            val scaleWidth = newWidth.toFloat() / width
            val scaleHeight = newHeight.toFloat() / height
            // CREATE A MATRIX FOR THE MANIPULATION
            val matrix = Matrix()
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight)

            // "RECREATE" THE NEW BITMAP
            val resizedBitmap = Bitmap.createBitmap(
                it, 0, 0, width, height, matrix, false
            )
            it.recycle()
            resizedBitmap
        }

    }

    private fun recognizeImage(bitmap: Bitmap) {

        // set Face to Preview
        viewBinding.facePreview.setImageBitmap(bitmap)

        //Create ByteBuffer to store normalized image
        val imgData = ByteBuffer.allocateDirect(1 * inputSize * inputSize * 3 * 4)
        imgData.order(ByteOrder.nativeOrder())
        intValues = IntArray(inputSize * inputSize)

        //get pixel values from Bitmap to normalize
        bitmap.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
        imgData.rewind()
        for (i in 0 until inputSize) {
            for (j in 0 until inputSize) {
                val pixelValue: Int = intValues.get(i * inputSize + j)
                if (isModelQuantized) {
                    // Quantized model
                    imgData.put((pixelValue shr 16 and 0xFF).toByte())
                    imgData.put((pixelValue shr 8 and 0xFF).toByte())
                    imgData.put((pixelValue and 0xFF).toByte())
                } else { // Float model
                    imgData.putFloat(((pixelValue shr 16 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
                    imgData.putFloat(((pixelValue shr 8 and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
                    imgData.putFloat(((pixelValue and 0xFF) - IMAGE_MEAN) / IMAGE_STD)
                }
            }
        }
        //imgData is input to our model
        val inputArray = arrayOf<Any>(imgData)
        val outputMap: MutableMap<Int, Any> = HashMap()
        embeedings =
            Array(1) { FloatArray(OUTPUT_SIZE) } //output of model will be stored in this variable
        outputMap[0] = embeedings
        tfLite?.runForMultipleInputsOutputs(inputArray, outputMap) //Run model
        var distance_local = Float.MAX_VALUE
        val id = "0"
        val label = "?"

        //Compare new face with saved Faces.
        if (registered.size > 0) {
            val nearest: List<Pair<String?, Float?>?> =
                findNearest(embeedings[0]) //Find 2 closest matching face
            if (nearest[0] != null) {
                val name = nearest[0]!!.first //get name and distance of closest matching face
                // label = name;
                distance_local = nearest[0]!!.second!!
//                if (developerMode) {
//                    if (distance_local < distance) //If distance between Closest found face is more than 1.000 ,then output UNKNOWN face.
//                        reco_name.setText(
//                            """
//                        Nearest: $name
//                        Dist: ${String.format("%.3f", distance_local)}
//                        2nd Nearest: ${nearest[1]!!.first}
//                        Dist: ${String.format("%.3f", nearest[1]!!.second)}
//                        """.trimIndent()
//                        ) else reco_name.setText(
//                        """
//                        Unknown
//                        Dist: ${String.format("%.3f", distance_local)}
//                        Nearest: $name
//                        Dist: ${String.format("%.3f", distance_local)}
//                        2nd Nearest: ${nearest[1]!!.first}
//                        Dist: ${String.format("%.3f", nearest[1]!!.second)}
//                        """.trimIndent()
//                    )
//
////                    System.out.println("nearest: " + name + " - distance: " + distance_local);
//                } else {
                //If distance between Closest found face is more than 1.000 ,then output UNKNOWN face.
                Log.d("zxcvbnm,", "${if (distance_local < distance) name else "unknown"}")


            }
        }

    }


    private fun findNearest(emb: FloatArray): MutableList<Pair<String?, Float?>?> {
        val neighbour_list: MutableList<Pair<String?, Float?>?> = ArrayList()
        var ret: Pair<String?, Float?>? = null //to get closest match
        var prev_ret: Pair<String?, Float?>? = null //to get second closest match

        for ((name, value) in registered) {
            val knownEmb = (value.getExtra() as Array<FloatArray>)[0]
            var distance = 0f
            for (i in emb.indices) {
                val diff = emb[i] - knownEmb[i]
                distance += diff * diff
            }
            distance = Math.sqrt(distance.toDouble()).toFloat()
            if (ret == null || distance < (ret.second ?: 0F)) {
                prev_ret = ret
                ret = Pair(name, distance)
            }
        }

        if (prev_ret == null) prev_ret = ret
        neighbour_list.add(ret)
        neighbour_list.add(prev_ret)
        return neighbour_list
    }

}