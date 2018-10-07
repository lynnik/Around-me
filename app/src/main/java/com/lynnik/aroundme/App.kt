package com.lynnik.aroundme

import android.app.Application
import android.content.Context
import timber.log.Timber

class App : Application() {

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
    }

    companion object {
        private var instance: App? = null

        fun applicationContext(): Context = instance!!.applicationContext
    }
}