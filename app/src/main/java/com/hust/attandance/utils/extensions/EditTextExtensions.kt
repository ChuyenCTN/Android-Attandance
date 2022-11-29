package com.hust.attandance.utils.extensions

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.annotation.DrawableRes
import com.hust.attandance.R
import com.hust.attandance.utils.helpers.NumberUtils
import com.hust.attandance.utils.widgets.InstantAutoComplete
import com.hust.attandance.utils.widgets.StringNormalizer
import kotlin.math.max
import kotlin.math.min

/** Edit Text **/

fun EditText.checkDiffAndSetText(newText: String?) {
    newText?.let { _newText ->
        val currentText = this.text.toString()
        if (_newText != currentText) {
            val currentSelected = selectionEnd
            val newSelected =
                min(max(0, newText.length - currentText.length + currentSelected), newText.length)

            this.setText(newText)
            if (this.isFocused) {
                this.setSelection(newSelected)
            }
        }
    }
}

fun EditText.set3DecimalNumberText(value: Double) {
    val newText = NumberUtils.dfNumber.format(value)
    setNewText(newText)
}

fun EditText.setNumberText(value: Double) {
    val newText = NumberUtils.df.format(value)
    setNewText(newText)
}

fun EditText.set2DecimalNumberText(value: Double) {
    val newText = NumberUtils.dfPercent.format(value)
    setNewText(newText)
}

fun EditText.setupTextChangedAction(removeSpace: Boolean = false, trimText: Boolean = false) {
    setOnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            if (removeSpace)
                setText(text.toString().replace(" ", ""))
            else if (trimText)
                setText(text.toString().trim())
        }
    }
}

fun EditText.setupTextChangedNormalize() {
    setOnFocusChangeListener { _, hasFocus ->
        if (!hasFocus) {
            setText(
                StringNormalizer.normalize(text.toString().replace(" ", ""))
            )
        }
    }
}


fun EditText.setNewText(newText: String) {
    var curText = this.text.toString()
    if (curText.endsWith("."))
        curText = curText.replace(".", "")
    if (curText != newText) {
        val oldLength = this.text.length
        val selectionStart = selectionStart
        this.setText(newText)
        setSelection(0.coerceAtLeast(this.text.length - oldLength + selectionStart))//Move Cursor to correct position
    }
}

@SuppressLint("ClickableViewAccessibility")
fun EditText.setupClearButton(isQuantityOrPrice: Boolean = false) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            val clearIcon =
                if (editable?.isNotEmpty() == true && (if (isQuantityOrPrice) editable.toString() != "0" else true)) R.drawable.ic_clear_text else 0
            setCompoundDrawablesWithIntrinsicBounds(0, 0, clearIcon, 0)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) = Unit
    })

    setOnTouchListener(View.OnTouchListener { _, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= (this.right - this.compoundPaddingRight)) {
                this.setText("")
                if (this is InstantAutoComplete) {
                    this.showDropDown()
                }
                return@OnTouchListener false
            }
        }
        if (this is InstantAutoComplete) {
            this.showDropDown()
        }
        return@OnTouchListener false
    })
}

@SuppressLint("ClickableViewAccessibility")
fun EditText.setupEndDrawable(@DrawableRes drawable: Int, onClick: () -> Unit) {
    setCompoundDrawablesWithIntrinsicBounds(0, 0, drawable, 0)
    setOnTouchListener { v, event ->
        if (event.action == MotionEvent.ACTION_UP) {
            if (event.rawX >= (this.right - this.compoundPaddingRight)) {
                onClick.invoke()
                if (this is InstantAutoComplete) {
                    this.showDropDown()
                }
                return@setOnTouchListener false
            }
        }
        return@setOnTouchListener false
    }
}

fun EditText.getTextValue(): String {
    return text.toString()
}

fun EditText.cursorEnd() {
    post {
        setSelection(0.coerceAtLeast(text.length))//Move Cursor to correct position
    }
}