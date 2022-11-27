package com.hust.attandance

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.multidex.MultiDexApplication
import com.hust.attandance.di.cacheModule
import com.hust.attandance.di.networkModule
import com.hust.attandance.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class App : MultiDexApplication(), Application.ActivityLifecycleCallbacks {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var currentActivity: Activity? = null
        lateinit var instance: App
        lateinit var localBroadcastManager: LocalBroadcastManager
    }

    override fun onActivityCreated(p0: Activity, p1: Bundle?) {

    }

    override fun onActivityStarted(p0: Activity) {
        currentActivity = p0
    }

    override fun onActivityResumed(p0: Activity) {
        if (BuildConfig.DEBUG) {
            Timber.d("onActivityResumed: ${p0.javaClass.canonicalName}")
        }
    }

    override fun onActivityPaused(p0: Activity) {
        Timber.d("onActivityPaused: ${p0.javaClass.canonicalName}")
    }

    override fun onActivityStopped(p0: Activity) {

    }

    override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {

    }

    override fun onActivityDestroyed(p0: Activity) {

    }

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        instance = this

        registerActivityLifecycleCallbacks(this)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@App)
            modules(
                networkModule,
                cacheModule,
                viewModelModule,
            )
        }
    }
}