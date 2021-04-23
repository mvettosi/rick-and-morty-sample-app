package com.urban.androidhomework

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class UrbanHomeworkApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initialiseApp()
    }

    private fun initialiseApp() {
        // Setup logger
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}