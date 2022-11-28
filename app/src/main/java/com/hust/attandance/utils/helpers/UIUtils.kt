package com.hust.attandance.utils.helpers

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.LightingColorFilter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.InputFilter
import android.text.Spanned
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions.withCrossFade
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.bumptech.glide.request.transition.Transition
import com.bumptech.glide.util.Preconditions
import com.hust.attandance.R
import com.hust.attandance.utils.exceptions.copyToClipboard
import com.hust.attandance.utils.extensions.isNotNullOrEmpty
import com.hust.attandance.utils.exceptions.DialogException
import com.hust.attandance.utils.exceptions.KvException
import com.hust.attandance.utils.exceptions.ToastException
import com.hust.attandance.utils.others.GlideApp

object UIUtils {
    fun callNumber(context: Context, phoneNumber: String) {
        try {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            intent.data = Uri.parse("tel:+84$phoneNumber")
            context.startActivity(intent)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    fun showException(context: Context, exception: KvException, callSupport: Boolean? = false) {

        if (exception.is5xxException()) {
            showGeneralException(context, exception)
            return
        }

        var exceptionObject = exception
        if (exceptionObject !is DialogException && exceptionObject !is ToastException) {
            exceptionObject = exception.generateException(context)
        }
        if (DeveloperMode.enable && exceptionObject is ToastException) {
            exceptionObject =
                DialogException(exception.code, "Error", exception.message, exception.cause)
        }
        if (exceptionObject is DialogException) {
            val title = exceptionObject.title
            val message = exceptionObject.message
            var positiveText: String? = null
            var onPositiveClick: DialogInterface.OnClickListener? = null
            var negativeText: String? = null
            val onNegativeClick: DialogInterface.OnClickListener? = null

            val dialog = buildAlertDialog(
                context, title, message,
                positiveText, onPositiveClick, negativeText, onNegativeClick
            )
            if (DeveloperMode.enable) {
                dialog.setButton(
                    DialogInterface.BUTTON_NEUTRAL,
                    "Rock"
                ) { dialogInterface, _ ->
                    dialogInterface.dismiss()
                    val traceLog = message + "\n" +
                            (exception.cause?.stackTraceToString() ?: exception.cause.toString())
                    context.copyToClipboard(content = traceLog)
                    buildAlertDialog(
                        context,
                        title,
                        traceLog,
                        negativeText = "Log",
                        onNegativeClick = { _, _ ->
//                            FirebaseCrashlytics.getInstance().apply {
//                                setCustomKey(
//                                    "ActiveLogTime",
//                                    System.currentTimeMillis().toTimeDDMMYYYYHHmm()
//                                )
//                                recordException(exception)
//                            }
                        }
                    ).show()
                }
            }
            dialog.show()
        } else {
            if (!exceptionObject.message.lowercase().startsWith("http")) {
                showToastMessage(context, exceptionObject.message)
            }
        }
    }

    /**
     * Build an alert dialog using standard android theme.
     */
    fun buildAlertDialog(
        context: Context, title: String?,
        message: CharSequence,
        positiveText: String? = null,
        onPositiveClick: DialogInterface.OnClickListener? = null,
        negativeText: String? = null,
        onNegativeClick: DialogInterface.OnClickListener? = null,
        neutralText: String? = null,
        onNeutralClick: DialogInterface.OnClickListener? = null
    ): AlertDialog {
        return AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton(
                positiveText ?: context.getString(R.string.label_ok_popup),
                onPositiveClick
            )
            if (neutralText.isNotNullOrEmpty() || onNeutralClick != null) {
                setNeutralButton(
                    neutralText,
                    onNeutralClick
                )
            }
            if (negativeText != null || onNegativeClick != null)
                setNegativeButton(
                    negativeText ?: context.getString(R.string.confirm_cancel),
                    onNegativeClick
                )
            setCancelable(false)
        }.create().apply {
            setCanceledOnTouchOutside(false)
        }
    }

    fun fixDialogStyle(alertDialog: AlertDialog) {
        alertDialog.apply {
            window?.decorView?.background?.setColorFilter(
                LightingColorFilter(-0x1000000, Color.WHITE)
            )
            getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorAppTheme
                )
            )
            getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorAppTheme
                )
            )
            getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(
                ContextCompat.getColor(
                    context,
                    R.color.colorAppTheme
                )
            )
        }
    }

    fun showDialogException(
        context: Context,
        message: String,
        title: String,
        negativeText: String,
        positiveText: String,
        negativeAction: DialogInterface.OnClickListener?,
        positionAction: DialogInterface.OnClickListener?
    ) {
        val dialog = buildAlertDialog(
            context = context,
            title = title,
            message = message,
            negativeText = negativeText,
            positiveText = positiveText,
            onNegativeClick = negativeAction,
            onPositiveClick = positionAction
        )
        dialog.show()
    }

    private fun showDetailException(context: Context, exception: KvException) {
        buildAlertDialog(
            context,
            context.getString(R.string.error),
            message = exception.message,
            positiveText = context.getString(R.string.ok),
        ).apply {
            (context as? Activity)?.let { activity ->
                if (activity.isFinishing) {
                    return
                }
                show()
                fixDialogStyle(this)
            }
        }
    }

    private fun showGeneralException(context: Context, exception: KvException) {
        buildAlertDialog(context,
            context.getString(R.string.dialog_general_error_title),
            context.getString(R.string.dialog_general_error_message),
            positiveText = context.getString(R.string.dialog_general_error_retry),
            negativeText = context.getString(R.string.dialog_general_error_detail),
            onNegativeClick = { p0, p1 -> showDetailException(context, exception) }).apply {
            show()
            fixDialogStyle(this)
        }
    }


    fun showToastMessage(context: Context?, text: String) {
        val duration = Toast.LENGTH_SHORT
        val toast = Toast.makeText(context, text, duration)
        toast.show()
    }

    /**
     * hide keyboard
     *
     * @param context Context
     * @param target  View that currently has focus
     */
    fun hideKeyboard(context: Context?, target: View?) {
        if (context == null || target == null) {
            return
        }
        val imm: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(target.windowToken, 0)
    }

    fun showKeyboard(context: Context?, target: View?) {
        if (context == null || target == null) {
            return
        }
        val imm: InputMethodManager =
            context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        //imm.showSoftInput(target, InputMethodManager.RESULT_SHOWN)
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }

    fun openBrowser(context: Context?, url: String?): Boolean {
        if (context == null || url == null) {
            return false
        }
        val localUri: Uri = Uri.parse(url)
        val intent = Intent(Intent.ACTION_VIEW, localUri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        return try {
            context.startActivity(intent)
            true
        } catch (exception: Exception) {
            false
        }
    }

    fun showImage(
        imageView: ImageView,
        source: Any,
        placeholderId: Int,
        onLoadCallback: ((res: Bitmap, imageView: ImageView) -> Unit)?,
        vararg transformations: Transformation<Bitmap>?
    ) {
        val factory: DrawableCrossFadeFactory =
            DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()
        val requestBuilder = GlideApp.with(
            imageView
        )
            .asBitmap()
            .transform(
                if (transformations.isNullOrEmpty()) CenterCrop() else MultiTransformation(
                    *transformations
                )
            )
            .fallback(placeholderId) // if load was null
            .error(placeholderId) // e.g. if path is not valid image
            .onlyRetrieveFromCache(source is GlideUrlWithToken && source.isOnlyRetrieveFromCache())//son.nd only load from cache if source has empty url
            .diskCacheStrategy(if (source is GlideUrlWithToken && source.isOnlyRetrieveFromCache()) DiskCacheStrategy.NONE else DiskCacheStrategy.AUTOMATIC)//son.nd don't cache source with empty url
            .load(source) // null or non-null image
            .placeholder(placeholderId) // while loading
            .transition(withCrossFade(factory))

        if (source.toString().startsWith("content://"))
            requestBuilder.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)

        if (onLoadCallback != null) {
            requestBuilder.into(
                object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        if (resource.width <= 500) {
                            onLoadCallback(resource, imageView)
                            imageView.setImageBitmap(resource)
                        } else {
                            requestBuilder.into(
                                object : CustomTarget<Bitmap>(500, 1) {
                                    override fun onResourceReady(
                                        resource: Bitmap,
                                        transition: Transition<in Bitmap>?
                                    ) {
                                        onLoadCallback(resource, imageView)
                                        imageView.setImageBitmap(resource)
                                    }

                                    override fun onLoadCleared(placeholder: Drawable?) {}
                                }
                            )
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                        // this is called when imageView is cleared on lifecycle call or for
                        // some other reason.
                        // if you are referencing the bitmap somewhere else too other than this imageView
                        // clear it here as you can no longer have the bitmap
                    }
                }
            )
        } else {
            requestBuilder.into(imageView)
        }
    }

    fun showGifImage(
        imageView: ImageView,
        source: Any,
        placeholderId: Int,
        vararg transformations: Transformation<Bitmap>?
    ) {
        val requestBuilder = GlideApp.with(
            imageView
        )
            .asGif()
            .transform(
                if (transformations.isNullOrEmpty()) CenterCrop() else MultiTransformation(
                    *transformations
                )
            )
            .fallback(placeholderId) // if load was null
            .override(500, 200)
            .error(placeholderId) // e.g. if path is not valid image
            .onlyRetrieveFromCache(source is GlideUrlWithToken && source.isOnlyRetrieveFromCache())//son.nd only load from cache if source has empty url
            .diskCacheStrategy(if (source is GlideUrlWithToken && source.isOnlyRetrieveFromCache()) DiskCacheStrategy.NONE else DiskCacheStrategy.AUTOMATIC)//son.nd don't cache source with empty url
            .load(source) // null or non-null image
            .placeholder(placeholderId) // while loading

        if (source.toString().startsWith("content://"))
            requestBuilder.skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE)

        requestBuilder.into(imageView)
    }
}

class DecimalDigitsInputFilter(private val decimalDigits: Int, var hasSelection: Boolean = false) :
    InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        if (hasSelection) return null
        var dotPos = -1
        val len = source.length
        for (i in 0 until len) {
            val c = source[i]
            if (c == '.') {
                dotPos = i
                break
            }
        }
        if (dotPos >= 0) {
            // protects against many dots
            if (source == "." && dest.contains(".")) {
                return ""
            }
            // if the text is entered before the dot
            if (dend <= dotPos) {
                return null
            }
            if (len - dotPos > decimalDigits) {
                return ""
            }
        }
        return null
    }
}

class GlideUrlWithToken(val url: String, val token: String) : GlideUrl(
    StringBuilder(if (url.isEmpty()) "cache" else url)
        .append(if (url.contains("?")) "&token=" else "?token=") //already has other parameters
        .append(token) // append the token at the end of url
        .toString()
) {

    override fun toString(): String {
        return super.getCacheKey()
    }

    override fun getCacheKey(): String? {
        return token
    }

    fun isOnlyRetrieveFromCache(): Boolean {
        return url.isEmpty()
    }

    init {
        Preconditions.checkNotNull(url)
        Preconditions.checkNotNull(token)
        Preconditions.checkNotEmpty(token)
    }
}