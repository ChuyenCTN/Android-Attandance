package com.hust.attandance.utils.extensions

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.TypedValue
import androidx.annotation.*
import androidx.annotation.IntRange
import androidx.core.content.ContextCompat
import androidx.core.graphics.ColorUtils
import kotlin.math.roundToInt

fun Context.getColorCompat(@ColorRes colorResId: Int): Int {
    return ContextCompat.getColor(this, colorResId)
}

fun Context.getColorStateListCompat(@ColorRes colorResId: Int): ColorStateList? {
    return ContextCompat.getColorStateList(this, colorResId)
}

@ColorInt
fun Context.resolveColorAttr(@AttrRes colorAttr: Int): Int {
    val resolvedAttr = resolveThemeAttr(colorAttr)
    // resourceId is used if it's a ColorStateList, and data if it's a color reference or a hex color
    val colorRes = if (resolvedAttr.resourceId != 0) resolvedAttr.resourceId else resolvedAttr.data
    return ContextCompat.getColor(this, colorRes)
}

fun Context.resolveColorStateListAttr(@AttrRes colorAttr: Int): ColorStateList? {
    val resolvedAttr = resolveThemeAttr(colorAttr)
// resourceId is used if it's a ColorStateList, and data if it's a color reference or a hex color
    val colorRes = if (resolvedAttr.resourceId != 0) resolvedAttr.resourceId else resolvedAttr.data
    return ContextCompat.getColorStateList(this, colorRes)
}

fun Context.resolveThemeAttr(@AttrRes attrRes: Int): TypedValue {
    val typedValue = TypedValue()
    theme.resolveAttribute(attrRes, typedValue, true)
    return typedValue
}


fun String.toAlphaHex(@IntRange(from = 0, to = 255) percent: Int): String {
    if (percent < 0 || percent > 255)
        return this
    try {
        val color = Color.parseColor(this)
        if (this.length == 7) {
            return "#${percent.to00Hex()}${this.substring(1)}"
        }
    } catch (ex: Exception) {
        ex.printStackTrace()
    }
    return this
}


/**
 * Thêm alpha vào string color
 * VD: string: "0xFFFFFF"
 *     alpha: 0.15 (15%)
 *     => @return: "0x26FFFFFF"
 */
@ColorInt
fun String.toAlphaColor(@FloatRange(from = 0.0, to = 1.0) percent: Float): Int {
    return try {
        val toAlphaHex = this.toAlphaHex((percent * 255).roundToInt())
        Color.parseColor(toAlphaHex)
    } catch (ex: Exception) {
        ex.printStackTrace()
        0
    }
}

fun String.addAlphaColor(@FloatRange(from = 0.0, to = 1.0) percent: Float): Int {
    return try {
        ColorUtils.setAlphaComponent(Color.parseColor(this), (percent * 255).roundToInt())
    } catch (ex: Exception) {
        ex.printStackTrace()
        (0xffffffff).toInt()
    }
}

fun Int.addAlphaColor(@FloatRange(from = 0.0, to = 1.0) percent: Float): Int {
    return try {
        ColorUtils.setAlphaComponent(this, (percent * 255).roundToInt())
    } catch (ex: Exception) {
        ex.printStackTrace()
        (0xffffffff).toInt()
    }
}

fun Context.addAlphaColor(
    @ColorRes colorResId: Int,
    @FloatRange(from = 0.0, to = 1.0) percent: Float = 1.0F
): Int {
    return try {
        ColorUtils.setAlphaComponent(this.getColorCompat(colorResId), (percent * 255).roundToInt())
    } catch (ex: Exception) {
        ex.printStackTrace()
        (0xffffffff).toInt()
    }
}

fun Context.addAlphaColorStateList(
    @ColorRes colorResId: Int,
    @FloatRange(from = 0.0, to = 1.0) percent: Float = 1.0F
): ColorStateList? {
    return try {
        ColorStateList.valueOf(
            ColorUtils.setAlphaComponent(
                this.getColorCompat(colorResId),
                (percent * 255).roundToInt()
            )
        )
    } catch (ex: Exception) {
        ex.printStackTrace()
        ColorStateList.valueOf((0xffffffff).toInt())
    }
}

private fun Int.to00Hex(): String {
    val hex = "00" + Integer.toHexString(this)
    return hex.substring(hex.length - 2, hex.length)
}

fun colorStateListOf(vararg mapping: Pair<IntArray, Int>): ColorStateList {
    val (states, colors) = mapping.unzip()
    return ColorStateList(states.toTypedArray(), colors.toIntArray())
}

fun createColorStateList(@ColorInt normalColor: Int, @ColorInt activateColor: Int): ColorStateList {
    return colorStateListOf(
        intArrayOf(
            android.R.attr.state_enabled,
            android.R.attr.state_selected
        ) to activateColor,
        intArrayOf(
            android.R.attr.state_enabled,
            android.R.attr.state_checked
        ) to activateColor,
        intArrayOf(
            android.R.attr.state_enabled
        ) to normalColor,
        intArrayOf(
        ) to normalColor
    )
}