package com.hust.attandance.di

import android.content.Context
import android.content.Intent
import android.util.Log
import com.hust.attandance.App
import com.hust.attandance.utils.Constants.ATTANDANCE_LOG
import net.citigo.kiotviet.pos.fb.data.cache.Cache
import net.citigo.kiotviet.pos.fb.data.cache.CacheSource
import net.citigo.kiotviet.pos.fb.data.cache.sp.SPCache

import org.koin.dsl.module

@JvmField
val cacheModule = module {
    single<CacheSource> {
        object : SPCache(get() as Context) {
            override fun notifyDataChange(key: String, data: Any?) {
                App.localBroadcastManager.sendBroadcast(Intent().apply {
                    action = "CACHE_SF"
                    putExtra("key", key)
                    when (data) {
                        is Int -> putExtra("value", data)
                        is Long -> putExtra("value", data)
                        is String -> putExtra("value", data)
                        is Double -> putExtra("value", data)
                        is Float -> putExtra("value", data)
                        is Boolean -> putExtra("value", data)
                    }
                    Log.d(ATTANDANCE_LOG + action, "$key: $data")
                })
            }
        }
    }
    single { Cache(get() as CacheSource) }
}