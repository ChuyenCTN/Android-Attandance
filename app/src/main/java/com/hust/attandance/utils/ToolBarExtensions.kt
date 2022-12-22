package com.hust.attandance.utils

import android.view.View
import androidx.appcompat.widget.Toolbar
import com.hust.attandance.utils.extensions.SafeClickListener


fun Toolbar.setSafeNavClickListener(delay: Int? = null, block: (View) -> Unit) {
    val safeClickListener = SafeClickListener(delay ?: 500) {
        block(it)
    }
    setNavigationOnClickListener(safeClickListener)
}