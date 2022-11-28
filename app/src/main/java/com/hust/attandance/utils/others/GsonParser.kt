package com.hust.attandance.utils.others

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class GsonParser(val gson: Gson) {
    inline fun <reified T> toJson(src: T): String {
        return gson.toJson(src)
    }

    inline fun <reified T> fromJson(src: String): T {
        val type = object : TypeToken<T>() {}.type
        return gson.fromJson(src, type)
    }
}