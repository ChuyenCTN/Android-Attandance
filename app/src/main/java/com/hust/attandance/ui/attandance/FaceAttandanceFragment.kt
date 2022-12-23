package com.hust.attandance.ui.attandance

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.*
import android.media.Image
import android.os.*
import android.util.Log
import android.util.Pair
import android.util.Size
import android.view.View
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.LifecycleOwner
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.common.util.concurrent.ListenableFuture
import com.google.gson.Gson
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.face.Face
import com.google.mlkit.vision.face.FaceDetection
import com.google.mlkit.vision.face.FaceDetector
import com.google.mlkit.vision.face.FaceDetectorOptions
import com.hust.attandance.R
import com.hust.attandance.databinding.FragmentFaceAttandanceBinding
import com.hust.attandance.ui.common.BaseViewBindingFragment
import com.hust.attandance.ui.schedule.StudentsDetectAdapter
import com.hust.attandance.utils.Constants
import com.hust.attandance.utils.exceptions.hasPermissions
import com.hust.attandance.utils.extensions.safeNavigateUp
import com.hust.attandance.utils.extensions.setSafeOnClickListener
import com.hust.attandance.utils.extensions.toggleVisibility
import com.hust.attandance.utils.helpers.UIUtils
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.single.PermissionListener
import kotlinx.android.synthetic.main.fragment_face_attandance.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.tensorflow.lite.Interpreter
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.ReadOnlyBufferException
import java.nio.channels.FileChannel
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

    private val args by navArgs<FaceAttandanceFragmentArgs>()

    private val SELECT_PICTURE = 1
    var cameraProvider: ProcessCameraProvider? = null
    var cameraSelector: CameraSelector? = null
    var cam_face = CameraSelector.LENS_FACING_BACK //Default Back Camera
    var detector: FaceDetector? = null
    var inputSize = 112 //Input size for model
    lateinit var intValues: IntArray
    var isModelQuantized = false
    var distance = 1.0f
    private var IMAGE_MEAN = 128.0f
    private var IMAGE_STD = 128.0f
    private var OUTPUT_SIZE = 192 //Output size of model
    lateinit var embeedings: Array<FloatArray>
    var tfLite: Interpreter? = null
    var start = true
    var flipX = false

    var modelFile = "mobile_face_net.tflite" //model name


    private val registered: HashMap<String, SimilarityClassifier.Recognition> =
        HashMap<String, SimilarityClassifier.Recognition>() //saved Faces


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        //Load model
        try {
            tfLite = loadModelFile(requireActivity(), modelFile)?.let { Interpreter(it) }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        //Initialize Face Detector
        val highAccuracyOpts = FaceDetectorOptions.Builder()
            .setPerformanceMode(FaceDetectorOptions.PERFORMANCE_MODE_ACCURATE)
            .build()
        detector = FaceDetection.getClient(highAccuracyOpts)
        checkPermission() {
            cameraBind()
        }

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

    @SuppressLint("UnsafeOptInUsageError")
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
            val result: com.google.android.gms.tasks.Task<List<Face>>? =
                image?.let {
                    detector?.process(it)
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
                                    Log.d("qazxcv,", faces.toString())
                                } else {
                                    Log.d(
                                        "zxcvbnm,",
                                        "${if (registered.isEmpty()) "Add face" else "No Face Detected!"}"
                                    )
                                }
                            })
                        ?.addOnFailureListener(
                            OnFailureListener {
                                // Task failed with an exception
                                // ...
                            })?.addOnCompleteListener(OnCompleteListener<List<Face?>?> {
                            imageProxy.close() //v.important to acquire next frame for analysis
                        })
                }
        }
        cameraSelector?.let {
            cameraProvider.bindToLifecycle(
                (this as LifecycleOwner),
                it,
                imageAnalysis,
                preview
            )
        }

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

    private fun recognizeImage(bitmap: Bitmap?) {

        try {// set Face to Preview
            viewBinding.facePreview.setImageBitmap(bitmap)
        } catch (e: Exception) {
        }

        //Create ByteBuffer to store normalized image
        val imgData = ByteBuffer.allocateDirect(1 * inputSize * inputSize * 3 * 4)
        imgData.order(ByteOrder.nativeOrder())
        intValues = IntArray(inputSize * inputSize)

        //get pixel values from Bitmap to normalize
        bitmap?.getPixels(intValues, 0, bitmap.width, 0, 0, bitmap.width, bitmap.height)
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


        if (!args.isAttandance) {
            val nearest: List<Pair<String?, Float?>?> =
                myFindNearest(
                    embeedings[0],
                    viewModel.registeredList
                ) //Find 2 closest matching face
            if (nearest[0] != null) {
                distance_local = nearest[0]!!.second!!
                if (distance_local < distance)
                    viewModel.checkStudentAttan(nearest[0]!!.first!!)
            }
        }


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

                Log.d("zxcvbnm,", "${if (distance_local < distance) "name" else "unknown"}")


            }
        }

    }


    private fun findNearest(emb: FloatArray): MutableList<Pair<String?, Float?>?> {
        val neighbour_list: MutableList<Pair<String?, Float?>?> = ArrayList()
        var ret: Pair<String?, Float?>? = null //to get closest match
        var prev_ret: Pair<String?, Float?>? = null //to get second closest match

        for ((name, value) in registered) {
            val knownEmb = (value.getExtra() as Array<ArrayList<Any>>)[0]
            var distance = 0f
            for (i in emb.indices) {
                val diff = emb[i] - knownEmb[i] as Float
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


    private fun myFindNearest(
        emb: FloatArray,
        inputList: List<Pair<String, SimilarityClassifier.Recognition>>
    ): MutableList<Pair<String?, Float?>?> {
        val neighbour_list: MutableList<Pair<String?, Float?>?> = ArrayList()
        var ret: Pair<String?, Float?>? = null //to get closest match
        var prev_ret: Pair<String?, Float?>? = null //to get second closest match

        for (input in inputList) {
            val knownEmb = (input.second.extra as ArrayList<ArrayList<Any>>)[0]
            var distance = 0f
            for (i in emb.indices) {
                val diff = emb[i] - (knownEmb[i] as Double).toFloat()
                distance += diff * diff
            }
            distance = Math.sqrt(distance.toDouble()).toFloat()
            if (ret == null || distance < (ret.second ?: 0F)) {
                prev_ret = ret
                ret = Pair(input.first, distance)
            }
        }

        if (prev_ret == null) prev_ret = ret
        neighbour_list.add(ret)
        neighbour_list.add(prev_ret)
        return neighbour_list
    }

    @Throws(IOException::class)
    private fun loadModelFile(activity: Activity, MODEL_FILE: String): MappedByteBuffer? {
        val fileDescriptor = activity.assets.openFd(MODEL_FILE)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun initView() {
        viewBinding.apply {

            tvTitle.text = if (!args.isAttandance) "Điểm danh" else "Nhận diện khuôn mặt"
            lButtonSave.toggleVisibility(args.isAttandance)
            lBottomSheet.toggleVisibility(!args.isAttandance)
            if (!args.isAttandance) initBottomSheet()
            btnBack.setSafeOnClickListener {
                findNavController().safeNavigateUp()
            }
            btnFlipCamera.setSafeOnClickListener {
                if (cam_face == CameraSelector.LENS_FACING_BACK) {
                    cam_face = CameraSelector.LENS_FACING_FRONT
                    flipX = true
                } else {
                    cam_face = CameraSelector.LENS_FACING_BACK
                    flipX = false
                }
                cameraProvider?.unbindAll()
                cameraBind()
            }

            btnSave.setSafeOnClickListener {
                try {//Create and Initialize new object with Face embeddings and Name.
                    if (this@FaceAttandanceFragment::embeedings.isInitialized) {
                        val result = SimilarityClassifier.Recognition(
                            "0", "", -1f
                        )
                        result.extra = embeedings

                        setFragmentResult(
                            Constants.FACE_ID_KEY,
                            bundleOf(Constants.FACE_ID_DATA to Gson().toJson(result))
                        )

                        Log.d("qazxcvbn", "$result")
                        Log.d("qazxcvbn", "$embeedings")
                        findNavController().safeNavigateUp()
                    }
                } catch (e: Exception) {
                }
            }

            btnNavToCart.setSafeOnClickListener {
                showProcessing()
                viewModel.saveAttandance()
            }
        }
        with(viewModel) {
            viewModel.students.observe(viewLifecycleOwner) {
                if (it != null && !args.isAttandance) {
                    productAdapter.setData(it)
                    productAdapter.notifyDataSetChanged()
                }
            }

            viewModel.createSchedule.observe(viewLifecycleOwner) {
                hideProcessing()
                findNavController().safeNavigateUp()
            }
        }
    }


    private fun startVibrate() {
        val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                requireActivity().getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            requireActivity().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }
        val vibrationEffect1: VibrationEffect
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrationEffect1 = VibrationEffect.createOneShot(100, VibrationEffect.DEFAULT_AMPLITUDE)
            vib.cancel()
            vib.vibrate(vibrationEffect1)
        } else vib.vibrate(100)
    }


    private fun checkPermission(onGranted: () -> Unit) {
        when {
            requireContext().hasPermissions(
                arrayOf(
                    Manifest.permission.CAMERA
                )
            ) -> {
                onGranted.invoke()
            }
            else -> {
                showDialogCamera {
                    Dexter.withContext(requireActivity()).withPermission(Manifest.permission.CAMERA)
                        .withListener(object : PermissionListener {
                            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                                onGranted.invoke()
                            }

                            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                                findNavController().safeNavigateUp()
                            }

                            override fun onPermissionRationaleShouldBeShown(
                                p0: com.karumi.dexter.listener.PermissionRequest?,
                                p1: PermissionToken?
                            ) {

                            }

                        }).check()
                }
            }
        }
    }

    private fun showDialogCamera(onPositive: () -> Unit) {
        UIUtils.buildAlertDialog(requireContext(),
            title = "Yêu cầu quyền truy cập camera",
            message = "Để sử dụng tính năng này, bạn cần cấp quyền truy cập camera cho ứng dụng. Bạn có muốn thực hiện cấp quyền?",
            positiveText = getString(R.string.label_ok_popup),
            onPositiveClick = { p0, p1 -> onPositive.invoke() },
            negativeText = getString(R.string.confirm_cancel),
            onNegativeClick = { p0, p1 -> findNavController().safeNavigateUp() }).apply {
            show()
            UIUtils.fixDialogStyle(this)
        }
    }

    private lateinit var productAdapter: StudentsDetectAdapter
    private fun initBottomSheet() {

        productAdapter = StudentsDetectAdapter() {}
        viewBinding.rvProductList.apply {
            adapter = productAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        viewModel.getStudents()


    }
}