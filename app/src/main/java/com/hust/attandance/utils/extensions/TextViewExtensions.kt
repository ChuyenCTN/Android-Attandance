package com.hust.attandance.utils.extensions

import android.content.Context
import android.os.Build
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.hust.attandance.R
import com.hust.attandance.utils.helpers.NumberUtils

/** */
fun TextView.set3DecimalNumberText(value: Double) {
    val newText = NumberUtils.dfNumber.format(value)
    if (this.text != newText) this.text = newText
}

fun TextView.setNumberText(value: Double) {
    val newText = NumberUtils.df.format(value)
    if (this.text != newText) this.text = newText
}

fun TextView.setNumberTextAndChangeColor(value: Double) {
    val newText = NumberUtils.df.format(value)
    if (this.text != newText) this.text = newText
    setTextColor(
        if (value > 0) {
            ContextCompat.getColor(context, R.color.cl_text_minus)
        } else if (value < 0) {
            ContextCompat.getColor(context, R.color.cl_text_add)
        } else {
            ContextCompat.getColor(context, R.color.cl_text_normal)
        }
    )

}

fun TextView.setNumberText(value: Long) {
    val newText = NumberUtils.df.format(value)
    if (this.text != newText) this.text = newText
}

fun TextView.set2DecimalNumberText(value: Double) {
    val newText = NumberUtils.dfPercent.format(value)
    if (this.text != newText) this.text = newText
}

fun TextView.setPercentText(value: Double) {
    val newText = NumberUtils.dfPercent.format(value) + "%"
    if (this.text != newText) this.text = newText
}

fun TextView.setTextFromHtml(value: String) {
    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(value, Html.FROM_HTML_MODE_COMPACT)
    } else {
        Html.fromHtml(value)
    }
}

fun TextView.setTextAppearanceSystem(resId: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(resId)
    } else {
        setTextAppearance(context, resId)
    }
}

fun Context.resolvedResId(attrId: Int): Int {
    val attrs = intArrayOf(attrId)       // The array of attributes we're interested in.
    val ta =
        obtainStyledAttributes(attrs)        // Get the value referenced by the attributes in the array
    val resId =
        ta.getResourceId(0, 0)                    // The first 0 is the index in the 'attrs' array.
    ta.recycle()
    return resId
}

fun TextView.setTextAppearanceTheme(attrId: Int) {
    setTextAppearanceSystem(context.resolvedResId(attrId))
}

fun TextView.makeLinks(vararg links: Pair<String, View.OnClickListener>) {
    val spannableString = SpannableString(this.text)
    var startIndexOfLink = -1
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.isUnderlineText = true
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod =
        LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}