package com.hust.attandance.ui.common.customviews.snackbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.ContentViewCallback
import com.hust.attandance.R

class StateSnackBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), ContentViewCallback {

    private val messageText: AppCompatTextView by lazy {
        findViewById(R.id.tvMessage)
    }

    private val backgroundCard: CardView by lazy {
        findViewById(R.id.cardBg)
    }

    init {
        View.inflate(context, R.layout.view_snackbar_state, this)
        clipToPadding = false
    }

    override fun animateContentIn(delay: Int, duration: Int) {
    }

    override fun animateContentOut(delay: Int, duration: Int) {
    }


    fun style(snackbarType: SnackbarType) = apply {
        when (snackbarType) {
            SnackbarType.SUCCESS -> {
                messageText.apply {
                    setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_snackbar_success, 0, 0, 0
                    )
                    val fontColor = ContextCompat.getColor(context, R.color.white)

                    setTextColor(fontColor)
                    compoundDrawables[0]?.setTint(fontColor)
                }
                backgroundCard.setCardBackgroundColor(
                    ContextCompat.getColor(context, R.color.bg_success)
                )
            }

            SnackbarType.ERROR -> {
                messageText.apply {
                    setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_snackbar_error, 0, 0, 0
                    )
                    val fontColor = ContextCompat.getColor(context, R.color.white)
                    setTextColor(fontColor)
                    compoundDrawables[0]?.setTint(fontColor)
                }
                backgroundCard.setCardBackgroundColor(
                    ContextCompat.getColor(context, R.color.bg_error)
                )

            }
        }
    }

    fun message(message: String) = apply {
        messageText.text = message
    }
}