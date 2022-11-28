package com.hust.attandance.utils.widgets

import android.app.Dialog
import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.util.SparseArray
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.annotation.StringRes
import com.hust.attandance.R
import kotlinx.android.synthetic.main.dialog_proress.*
import java.lang.ref.WeakReference
import kotlin.math.min

class KvProgressDialog(context: Context) : Dialog(context) {

    private var id: Int? = null
    private val screenWidthPercent = 0.85F

    private var title: String = ""
    private var message: String = ""

    private var max: Int = 100
    private var progress: Int = 0
    private var contents: ArrayList<String> = ArrayList()

    companion object {

        @JvmStatic
        private val cache = SparseArray<WeakReference<KvProgressDialog>>()

        @JvmStatic
        fun getDialog(
            context: Context,
            @StringRes titleResId: Int? = null,
            @StringRes messageResId: Int? = null
        ): KvProgressDialog {
            val id = context.hashCode()
            var dialog = cache[id]?.get()
            if (dialog == null) {
                dialog = KvProgressDialog(context)
                dialog.id = id
                dialog.title = if (titleResId != null) context.getString(titleResId) else ""
                dialog.message = if (messageResId != null) context.getString(messageResId) else ""
                cache.put(id, WeakReference(dialog))
            }
            return dialog
        }

        @JvmStatic
        fun addContent(context: Context, content: String) {
            cache[context.hashCode()]?.get()?.addContent(content)
        }

        @JvmStatic
        fun max(context: Context, max: Int) {
            cache[context.hashCode()]?.get()?.max(max)
        }

        @JvmStatic
        fun progress(context: Context, process: Int) {
            cache[context.hashCode()]?.get()?.progress(process)
        }

        @JvmStatic
        fun dismiss(context: Context) {
            cache[context.hashCode()]?.get()?.dismiss()
        }
    }

    private fun addContent(content: String) {

    }

    private fun max(max: Int) {
        if (progressBar != null) {
            progressBar.max = max
        }
    }

    private fun progress(progress: Int) {
        if (progressBar != null) {
            progressBar.progress = progress
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        window?.setBackgroundDrawableResource(android.R.color.transparent)
        setContentView(R.layout.dialog_proress)

        val size = Point()
        val display = window?.windowManager?.defaultDisplay
        display?.getSize(size)

        val width = min(size.x * screenWidthPercent, dp2px(context, 400F))

        window?.setLayout(width.toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.CENTER)

        progressBar.max = max
        progressBar.progress = progress

        tvTitle.visibility = if (title.isBlank()) View.GONE else View.VISIBLE
        if (!title.isBlank()) {
            tvTitle.text = title
        }

        tvMessage.visibility = if (message.isBlank()) View.GONE else View.VISIBLE
        if (!message.isBlank()) {
            tvMessage.text = message
        }
    }

    override fun dismiss() {
        super.dismiss()
        id?.let { cache.remove(it) }
    }

    private fun dp2px(context: Context?, dp: Float): Float {
        if (context == null) return dp
        val scale = context.resources.displayMetrics.density
        return dp * scale + 0.5f
    }
}