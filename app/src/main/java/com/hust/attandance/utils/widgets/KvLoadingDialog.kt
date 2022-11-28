package com.hust.attandance.utils.widgets

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.SparseArray
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import androidx.annotation.StringRes
import com.hust.attandance.R
import kotlinx.android.synthetic.main.dialog_processing.*
import java.lang.ref.WeakReference
import kotlin.math.min

class KvLoadingDialog(context: Context) : Dialog(context) {

    private var id: Int? = null
    private var textResId = R.string.label_processing_content
    private val screenWidthPercent = 0.85F

    companion object {

        @JvmStatic
        private val cache = SparseArray<WeakReference<KvLoadingDialog>>()

        @JvmStatic
        fun getDialog(
            context: Context,
            @StringRes textResId: Int = R.string.label_processing_content
        ): KvLoadingDialog {
            val id = context.hashCode()
            var dialog = cache[id]?.get()
            if (dialog == null) {
                dialog = KvLoadingDialog(context)
                dialog.id = id
                dialog.textResId = textResId
                cache.put(id, WeakReference(dialog))
            }
            return dialog
        }

        @JvmStatic
        fun dismiss(context: Context) {
            cache[context.hashCode()]?.get()?.dismiss()
        }
    }

    override fun dismiss() {
        super.dismiss()
        id?.let { cache.remove(it) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.dialog_processing)

        val size = Point()
        val display = window?.windowManager?.defaultDisplay
        display?.getSize(size)

        val width = min(size.x * screenWidthPercent, dp2px(context, 400F))

        window?.setLayout(width.toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)

        tv_process_content.setText(textResId)
    }

    private fun dp2px(context: Context?, dp: Float): Float {
        if (context == null) return dp
        val scale = context.resources.displayMetrics.density
        return dp * scale + 0.5f
    }

//    private fun tintIndeterminateProgress(
//        progress: ProgressBar,
//        @ColorInt colorInt: Int = Utils.getColorResBy(
//            context, R.attr.colorPrimary
//        )
//    ) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            progress.indeterminateTintList =
//                ColorStateList.valueOf(colorInt)
//        } else {
//            (progress.indeterminateDrawable as? LayerDrawable)?.apply {
//                if (numberOfLayers >= 2) {
//                    setId(0, android.R.id.progress)
//                    setId(1, android.R.id.secondaryProgress)
//                    val progressDrawable = findDrawableByLayerId(android.R.id.progress).mutate()
//                    progressDrawable.setColorFilter(
//                        colorInt,
//                        PorterDuff.Mode.SRC_ATOP
//                    )
//                }
//            }
//        }
//    }
}