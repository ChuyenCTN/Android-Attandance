package com.hust.attandance.ui.common.customviews.snackbar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.hust.attandance.R
import com.hust.attandance.utils.extensions.findSuitableParent

class StateSnackbar(
    parent: ViewGroup,
    content: StateSnackBarView
) : BaseTransientBottomBar<StateSnackbar>(parent, content, content) {

    init {
        getView().setBackgroundColor(
            ContextCompat.getColor(
                view.context,
                android.R.color.transparent
            )
        )
//        getView().setPadding(0, 0, 0, context.resources.getDimensionPixelSize(R.dimen.dp_36))

    }

    companion object {

        fun make(view: View, text: String, type: SnackbarType): StateSnackbar? {
            // First we find a suitable parent for our custom view
            val parent = view.findSuitableParent() ?: return null

            val snackbarView = LayoutInflater.from(view.context).inflate(
                R.layout.layout_snackbar_state,
                parent,
                false
            ) as StateSnackBarView
            snackbarView.updateLayoutParams<FrameLayout.LayoutParams> {
                updateMargins(bottom = parent.context.resources.getDimensionPixelSize(R.dimen.dp_36))
            }
            snackbarView.message(text).style(type)
            return StateSnackbar(
                parent, snackbarView
            )
        }
    }
}