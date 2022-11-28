package com.hust.attandance.utils.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.SystemClock
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.children
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.Transformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.hust.attandance.R
import com.hust.attandance.utils.helpers.UIUtils
import timber.log.Timber

fun ImageView.showImage(
    source: Any,
    placeholderId: Int,
    onLoadCallback: ((res: Bitmap, imageView: ImageView) -> Unit)?,
    vararg transformations: Transformation<Bitmap>
) {
    val src = if (source is String && source.isBlank()) placeholderId else source
    if (source is String && (source.contains(".gif?") || source.endsWith(".gif")))
        UIUtils.showGifImage(this, src, placeholderId, *transformations)
    else
        UIUtils.showImage(this, src, placeholderId, onLoadCallback, *transformations)
}

fun ImageView.showAvatar(source: Any) {
    showImage(source, R.drawable.ic_placeholder_avatar, null, CenterCrop(), CircleCrop())
}

fun ViewGroup.inflate(@LayoutRes l: Int): View {
    return LayoutInflater.from(context).inflate(l, this, false)
}

class SafeClickListener(
    private var defaultInterval: Int = 500,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        Timber.d("onClick: ${this.hashCode()}")
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}

fun View.setSafeOnClickListener(delay: Int? = null, block: (View) -> Unit) {
    val safeClickListener = SafeClickListener(delay ?: 500) {
        block(it)
    }
    setOnClickListener(safeClickListener)
}

fun View.isVisible(): Boolean {
    return visibility == View.VISIBLE
}

fun View.isInvisible(): Boolean {
    return visibility == View.INVISIBLE
}

fun View.toggleVisibility() {
    if (visibility == View.VISIBLE)
        hideView()
    else
        showView()
}

fun View.toggleVisibility(toggle: Boolean, keepSpace: Boolean = false) {
    visibility = if (toggle) {
        if (keepSpace) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    } else {
        View.GONE
    }
}

fun View.hideView(keepSpace: Boolean = false) {
    visibility = if (keepSpace)
        View.INVISIBLE
    else
        View.GONE
}

fun View.showView() {
    visibility = View.VISIBLE
}

fun View.toggleAvailability(enabled: Boolean, alphaDisable: Float = 0.5f) {
    isFocusable = enabled
    isEnabled = enabled
    alpha = if (enabled) 1.0f else 0.5f
    if (this is ViewGroup) children.forEach { child -> child.toggleAvailability(enabled) }
}

fun View.toggleAvailabilityWithoutAlpha(enabled: Boolean) {
    isFocusable = enabled
    isEnabled = enabled
    if (this is ViewGroup) children.forEach { child -> child.toggleAvailabilityWithoutAlpha(enabled) }
}

fun <T : View> ViewGroup.getViewsByType(tClass: Class<T>): List<T> {
    return mutableListOf<T?>().apply {
        for (i in 0 until childCount) {
            val child = getChildAt(i)
            (child as? ViewGroup)?.let {
                addAll(child.getViewsByType(tClass))
            }
            if (tClass.isInstance(child))
                add(tClass.cast(child))
        }
    }.filterNotNull()
}

fun WebView.toBitmap(): Bitmap? {
    try {
        val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
        return bitmap
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return null
}

/** Combination of all flags required to put activity into immersive mode */
const val FLAGS_FULLSCREEN =
    View.SYSTEM_UI_FLAG_LOW_PROFILE or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION

/** Milliseconds used for UI animations */
const val ANIMATION_FAST_MILLIS = 50L
const val ANIMATION_SLOW_MILLIS = 100L

/**
 * Simulate a button click, including a small delay while it is being pressed to trigger the
 * appropriate animations.
 */
fun ImageButton.simulateClick(delay: Long = ANIMATION_FAST_MILLIS) {
    performClick()
    isPressed = true
    invalidate()
    postDelayed({
        invalidate()
        isPressed = false
    }, delay)
}

/** Pad this view with the insets provided by the device cutout (i.e. notch) */
@RequiresApi(Build.VERSION_CODES.P)
fun View.padWithDisplayCutout() {

    /** Helper method that applies padding from cutout's safe insets */
    fun doPadding(cutout: DisplayCutout) = setPadding(
        cutout.safeInsetLeft,
        cutout.safeInsetTop,
        cutout.safeInsetRight,
        cutout.safeInsetBottom
    )

    // Apply padding using the display cutout designated "safe area"
    rootWindowInsets?.displayCutout?.let { doPadding(it) }

    // Set a listener for window insets since view.rootWindowInsets may not be ready yet
    setOnApplyWindowInsetsListener { _, insets ->
        insets.displayCutout?.let { doPadding(it) }
        insets
    }
}

/** Same as [AlertDialog.show] but setting immersive mode in the dialog's window */
fun AlertDialog.showImmersive() {
    // Set the dialog to not focusable
    window?.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
        WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    )

    // Make sure that the dialog's window is in full screen
    window?.decorView?.systemUiVisibility = FLAGS_FULLSCREEN

    // Show the dialog while still in immersive mode
    show()

    // Set the dialog to focusable again
    window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
}

fun ImageView.showImage(
    url: Any?,
    placeholderId: Int = R.drawable.ic_loading_non_rounded_placeholder,
    errorId: Int = R.drawable.ic_loading_non_rounded_error,
    vararg transforms: Transformation<Bitmap> = arrayOf(CenterCrop())
) {
    Glide.with(context).clear(this)
    RequestOptions()
        .transform(*transforms)
        .placeholder(placeholderId)
        .error(errorId)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH).let { options ->
            Glide.with(this.context).load(url).apply(options).into(this)
        }
}

/**
 * Load image dạng thumb lên view như ảnh product, image trong một danh sách...
 */
fun ImageView.showImageMedium(
    url: Any?
) {
    showImage(
        url = url,
        transforms = arrayOf(
            CenterCrop(), RoundedCorners(
                this.resources.getDimension(
                    R.dimen.dp_8
                ).toInt()
            )
        )
    )
}


fun ImageView.showImage(
    url: Any?,
    placeholderId: Int = R.drawable.ic_loading_non_rounded_placeholder,
    errorId: Int = R.drawable.ic_loading_non_rounded_error,
    vararg transforms: Transformation<Bitmap> = arrayOf(CenterCrop()),
    onLoadFailed: () -> Unit,
    onLoadFailedAgain: (() -> Unit)? = null
) {
    RequestOptions()
        .transform(*transforms)
        .placeholder(placeholderId)
        .error(errorId)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH).let { options ->
            Glide.with(this.context).load(url).apply(options)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        if (isFirstResource)
                            onLoadFailed()
                        else {
                            onLoadFailedAgain?.invoke()
                        }
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }
                }).into(this)
        }
}



fun ImageView.loadImageFitToImageView(
    url: String?,
    placeholderId: Int = R.drawable.ic_loading_non_rounded_placeholder,
    errorId: Int = R.drawable.ic_loading_non_rounded_error
) {
    Glide.with(context).clear(this)
    val options = RequestOptions().fitCenter()
    Glide.with(context).load(url).apply(options)
        .placeholder(placeholderId)
        .error(errorId)
        .transition(DrawableTransitionOptions.withCrossFade()).into(this)
}

fun ImageView.clearPhoto() {
    Glide.with(context).clear(this)
}

fun View.showKeyboard() {
    context?.let { context ->
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let {
            it.showSoftInput(this, 0)
        }
    }
}

fun View.hideKeyboard() {
    context?.let { context ->
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.let {
            it.hideSoftInputFromWindow(this.windowToken, 0)
        }
    }
}

fun BottomNavigationView.setBadge(menuItemId: Int, badgeCount: Int) {
    getOrCreateBadge(menuItemId)?.apply {
        clearNumber()
        maxCharacterCount = 4
        isVisible = badgeCount != 0
        number = badgeCount
    }
}

internal fun View?.findSuitableParent(): ViewGroup? {
    var view = this
    var fallback: ViewGroup? = null
    do {
        if (view is CoordinatorLayout) {
            // We've found a CoordinatorLayout, use it
            return view
        } else if (view is FrameLayout) {
            if (view.id == android.R.id.content) {
                // If we've hit the decor content view, then we didn't find a CoL in the
                // hierarchy, so use it.
                return view
            } else {
                // It's not the content view but we'll use it as our fallback
                fallback = view
            }
        }

        if (view != null) {
            // Else, we will loop and crawl up the view hierarchy and try to find a parent
            val parent = view.parent
            view = if (parent is View) parent else null
        }
    } while (view != null)

    // If we reach here then we didn't find a CoL or a suitable content view so we'll fallback
    return fallback
}

fun NavController.safeNavigate(direction: NavDirections) {
    currentDestination?.getAction(direction.actionId)?.run {
        navigate(direction)
    }
}

//fun NavController.safeNavigateUp() {
//    currentDestination?.let {
//        if (it.id != R.id.homeFragment)
//            navigateUp()
//    }
//}