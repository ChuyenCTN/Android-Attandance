package com.hust.attandance.utils.widgets

import java.text.Normalizer
import java.util.*
import java.util.regex.Pattern

/**
 * String utils to handle accented characters for search and highlighting
 */
object StringNormalizer {
    var nonAscii = Pattern.compile("(?i)([^a-z0-9 -])")
    private val NORMALIZE_PATTER = "[\\p{InCombiningDiacriticalMarks}\\p{IsLm}\\p{IsSk}]+"

    /**
     * Return the input string, lower-cased and with standard Ascii characters for common european accents
     *
     * @param input string input, with accents
     * @return normalized string
     */
    fun normalize(input: String): String {
        val preNormalizeInput = input.lowercase(Locale.getDefault())
            .replace("đ", "d").replace("Đ", "D")
        val unicodeFormNormalize = Normalizer.normalize(preNormalizeInput, Normalizer.Form.NFKD)
        return String(unicodeFormNormalize.replace(NORMALIZE_PATTER, "").toByteArray(charset(Charsets.US_ASCII.name())), Charsets.US_ASCII).replace("?","")
    }

    fun normalize(vararg str: String?): String {
        val strList = mutableListOf<String>()
        for (s in str) {
            s?.let {
                strList.add(
                    normalize(
                        s
                    )
                )
            }
        }
        return strList.joinToString(" ")
    }

    /**
     * Return a regexp matching common characters
     * For safe use, all non alpha characters are removed from input
     * Assume the input was previously sent to normalize()
     *
     *
     * "aze" => /[àâa]z[èéêë]/
     *
     * @param input string to "un-normalize"
     * @return a regexp
     */
    fun unNormalize(input: String): String {
        var newInput = input
        newInput = nonAscii.matcher(newInput.toLowerCase(Locale.getDefault())).replaceAll("")
        return newInput.replace("e".toRegex(), "[eèéêëẻẹẽềếểễệ]").replace("d".toRegex(), "dđ")
            .replace("u".toRegex(), "[uûùúủũụưừứủữự]").replace("i".toRegex(), "[iïîìíỉĩị]")
            .replace("a".toRegex(), "[aàâăàáảãạầấẩẫậằẳắẵặ]").replace("o".toRegex(), "[oôồốổỗộơờớởỡợòóỏõọ]").replace(" ".toRegex(), "[ -]")
    }

    fun normalCurrency(input: String): String {
        return Normalizer.normalize(input.replace("đ".toRegex(), "d").replace("Đ".toRegex(), "D"), Normalizer.Form.NFD).replace("[^\\p{ASCII}]".toRegex(), "")
    }

    fun containSign(input: String): Boolean {
        return !input.equals(
            normalCurrency(
                input
            ), ignoreCase = true
        )
    }

    //    fun stripAccents(input: String): String {
//        return StringUtils.stripAccents(input.replace("đ".toRegex(), "d").replace("Đ".toRegex(), "D"))
//    }
    fun removeSignFromPhone(input: String) : String {
        return input.replace("[-.()/ ]".toRegex(), "")
    }
}
