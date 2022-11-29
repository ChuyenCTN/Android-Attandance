package com.hust.attandance.utils.helpers

import android.text.TextUtils
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.roundToInt
import kotlin.math.roundToLong

object NumberUtils {
    val df = DecimalFormat("###,###,###,###", DecimalFormatSymbols(Locale.US))
    val dfPercent = DecimalFormat("###,###,###,###.##", DecimalFormatSymbols(Locale.US))
    val dfNumber = DecimalFormat("###,###,###,###.###", DecimalFormatSymbols(Locale.US))

    fun formatThreeDecimal(percent: Double): String? {
        val dfPercen = DecimalFormat("#.###", DecimalFormatSymbols(Locale.US))
        dfPercen.decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
        return dfPercen.format(percent)
    }

    fun formatDecimalNotRounding(number: Double): String {
        val dfThreeSymbols = dfNumber
        dfThreeSymbols.roundingMode = RoundingMode.FLOOR
        return dfThreeSymbols.format(number)
    }

    fun formatDecimalRound(number: Double?): String {
        return number?.let {
            NumberFormat.getNumberInstance(Locale.US).format(it.roundToInt())
        } ?: ""
    }

    fun formatDecimalRoundToLong(number: Double?): String {
        return number?.let {
            NumberFormat.getNumberInstance(Locale.US).format(it.roundToLong())
        } ?: ""
    }

    fun format2Decimal(number: Double): String {
        val dfThreeSymbols = dfPercent
        dfThreeSymbols.roundingMode = RoundingMode.FLOOR
        return dfThreeSymbols.format(number)
    }

    fun formatPercent(percent: Double): String? {
        //return NumberFormat.getPercentInstance(Locale.US).format(percent);
        return dfPercen.format(percent) + "%"
    }

    fun formatPercentPrint(percent: Double): String {
        val dfPercenPrint = DecimalFormat("#.##", DecimalFormatSymbols(Locale.US))
        return dfPercenPrint.format(percent) + "%"
    }

    fun formatPercent(percent: Double?): String {
        return DecimalFormat("#.##", DecimalFormatSymbols(Locale.US)).format(percent ?: 0.0)
    }

    fun formatDecimal(number: Float): String {
        return NumberFormat.getNumberInstance(Locale.US).format(number.toDouble())
    }

    fun formatNumberNoneDecimal(number: Double): String? {
        return df.format(number)
    }

    fun formatDecimal(number: Double): String {
        return NumberFormat.getNumberInstance(Locale.US).format(number)
    }

    private val dfPercen = DecimalFormat("#.##", DecimalFormatSymbols(Locale.US))

    fun formatQuantity(quantity: Double): String {
        val dfPercen = DecimalFormat("#.###", DecimalFormatSymbols(Locale.US))
        dfPercen.decimalFormatSymbols = DecimalFormatSymbols(Locale.US)
        return dfPercen.format(quantity)
    }

    fun roundDecimal(value: Double): Double {
        val dfPercent = DecimalFormat("#.##", DecimalFormatSymbols(Locale.US))
        return dfPercent.format(value).toDouble()
    }

    fun cleanPhoneNumber(phone: String): String? {
        return if (TextUtils.isEmpty(phone)) "" else phone
            .replace(" ", "")
            .replace("-", "")
            .replace("(", "")
            .replace(")", "")
            .replace(".", "")
    }

    var ChuSo = arrayOf(
        " không ",
        " một ",
        " hai ",
        " ba ",
        " bốn ",
        " năm ",
        " sáu ",
        " bảy ",
        " tám ",
        " chín "
    )
    var Tien = arrayOf("", " nghìn", " triệu", " tỷ", " nghìn tỷ", " triệu tỷ")

    //1. Hàm đọc số có ba chữ số;
    fun DocSo3ChuSo(baso: Double, firstNumber: Boolean): String {
        val tram: Int
        val chuc: Int
        val donvi: Int
        var KetQua = ""
        tram = (baso / 100).toInt()
        chuc = (baso % 100 / 10).toInt()
        donvi = baso.toInt() % 10
        if (tram == 0 && chuc == 0 && donvi == 0) return ""
        //        if (tram != 0)
        if (!(firstNumber && tram == 0)) {
            KetQua += ChuSo.get(tram).toString() + " trăm "
            if (chuc == 0 && donvi != 0) KetQua += " linh "
        }
        if (chuc != 0 && chuc != 1) {
            KetQua += ChuSo.get(chuc).toString() + " mươi"
            //if ((chuc == 0) && (donvi != 0)) KetQua = KetQua + " linh ";
        }
        if (chuc == 1) KetQua += " mười "
        when (donvi) {
            1 -> if (chuc != 0 && chuc != 1) {
                KetQua += " mốt "
            } else {
                KetQua += ChuSo.get(donvi)
            }
            5 -> if (chuc == 0) {
                KetQua += ChuSo.get(donvi)
            } else {
                KetQua += " lăm "
            }
            else -> if (donvi != 0) {
                KetQua += ChuSo.get(donvi)
            }
        }
        return KetQua
    }

    //2. Hàm đọc số thành chữ (Sử dụng hàm đọc số có ba chữ số)
    fun DocTienBangChu(SoTien: Double): String {
        var lan = 0
        var i = 0
        var so = 0.0
        var KetQua = ""
        var tmp = ""
        val ViTri = DoubleArray(6)
        if (SoTien < 0) return "Số tiền âm"
        if (SoTien == 0.0) return "Không"
        so = if (SoTien > 0) {
            SoTien
        } else {
            -SoTien
        }
        if (SoTien > 8999999999999999.0) {
            //SoTien = 0;
            return "Số quá lớn!"
        }
        ViTri[5] = floor(so / 1000000000000000.0)
        if (java.lang.Double.isNaN(ViTri[5])) ViTri[5] = 0.0
        so -= ViTri[5] * 1000000000000000.0
        ViTri[4] = floor(so / 1000000000000.0)
        if (java.lang.Double.isNaN(ViTri[4])) ViTri[4] = 0.0
        so -= ViTri[4] * 1000000000000.0
        ViTri[3] = floor(so / 1000000000.0)
        if (java.lang.Double.isNaN(ViTri[3])) ViTri[3] = 0.0
        so -= ViTri[3] * 1000000000
        ViTri[2] = (so.toInt() / 1000000).toDouble()
        if (java.lang.Double.isNaN(ViTri[2])) ViTri[2] = 0.0
        ViTri[1] = ((so % 1000000).toInt() / 1000).toDouble()
        if (java.lang.Double.isNaN(ViTri[1])) ViTri[1] = 0.0
        ViTri[0] = so % 1000
        if (java.lang.Double.isNaN(ViTri[0])) ViTri[0] = 0.0
        lan = if (ViTri[5] > 0) {
            5
        } else if (ViTri[4] > 0) {
            4
        } else if (ViTri[3] > 0) {
            3
        } else if (ViTri[2] > 0) {
            2
        } else if (ViTri[1] > 0) {
            1
        } else {
            0
        }
        i = lan
        while (i >= 0) {
            tmp = DocSo3ChuSo(ViTri[i], i == lan)
            KetQua += tmp
            if (ViTri[i] > 0) KetQua += Tien.get(i)
            i--
        }
        //        if (KetQua.endsWith(",")) {
//            KetQua = KetQua.substring(0, KetQua.length() - 1);
//        }
        KetQua = KetQua.substring(1, 2).toUpperCase() + KetQua.substring(2)
        return KetQua //.replace( /,/g, '');//.substring(0, 1);//.toUpperCase();// + KetQua.substring(1);
    }
}

fun String.reformatCurrencyTyping(): String {
    return this.trim { it <= ' ' }.replace(",", "")
}

fun Double.formatDecimal(): String {
    return NumberFormat.getNumberInstance(Locale.US).format(this)
}