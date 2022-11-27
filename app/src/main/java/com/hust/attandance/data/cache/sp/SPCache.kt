package net.citigo.kiotviet.pos.fb.data.cache.sp

import android.content.Context
import android.content.SharedPreferences
import com.hust.attandance.utils.Constants
import net.citigo.kiotviet.pos.fb.data.cache.CacheSource

abstract class SPCache(context: Context) : CacheSource {

    var sp: SharedPreferences = context.getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE)

    override fun getAll(): MutableMap<String?, *>? {
        return sp.all
    }

    override fun contains(key: String): Boolean = sp.contains(key)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean =
        sp.getBoolean(key, defaultValue)

    override fun getInt(key: String, defaultValue: Int): Int =
        sp.getInt(key, defaultValue)

    override fun getLong(key: String, defaultValue: Long): Long =
        sp.getLong(key, defaultValue)

    override fun getFloat(key: String, defaultValue: Float): Float =
        sp.getFloat(key, defaultValue)

    override fun getString(key: String, defaultValue: String): String =
        sp.getString(key, defaultValue) ?: defaultValue

    override fun putInt(key: String, value: Int) {
        sp.edit().putInt(key, value).apply()
        notifyDataChange(key, value)
    }

    override fun putLong(key: String, value: Long) {
        sp.edit().putLong(key, value).apply()
        notifyDataChange(key, value)
    }

    override fun putBoolean(key: String, value: Boolean) {
        sp.edit().putBoolean(key, value).apply()
        notifyDataChange(key, value)
    }

    override fun putFloat(key: String, value: Float) {
        sp.edit().putFloat(key, value).apply()
        notifyDataChange(key, value)
    }

    override fun putString(key: String, value: String?) {
        sp.edit().putString(key, value).apply()
        notifyDataChange(key, value)
    }

    override fun remove(key: String) {
        sp.edit().remove(key).apply()
        notifyDataChange(key, null)
    }

    override fun clear() {
        sp.edit().clear().apply()
        notifyDataChange("_all_", null)
    }
}