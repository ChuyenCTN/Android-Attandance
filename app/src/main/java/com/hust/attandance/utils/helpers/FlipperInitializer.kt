package com.hust.attandance.utils.helpers

import android.content.Context
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.facebook.soloader.SoLoader
import com.hust.attandance.BuildConfig
import okhttp3.Interceptor

object FlipperInitializer {

    private val networkFlipperPlugin = NetworkFlipperPlugin()

    fun init(context: Context) {
        if (!BuildConfig.DEBUG) {
            return
        }
        SoLoader.init(context, false)
        val client = AndroidFlipperClient.getInstance(context)

        client.addPlugin(networkFlipperPlugin)
        client.addPlugin(InspectorFlipperPlugin(context, DescriptorMapping.withDefaults()))
        client.start()
    }

    fun setupInterceptor(): Interceptor? {
        return if (BuildConfig.DEBUG) {
            FlipperOkhttpInterceptor(networkFlipperPlugin)
        } else {
            null
        }
    }
}