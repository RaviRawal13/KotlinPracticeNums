package com.foobles.kotlinnum

import android.app.Application
import timber.log.Timber

class NumSenseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}