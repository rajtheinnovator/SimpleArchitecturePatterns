package com.enpassio.core

import android.app.Application
import com.squareup.leakcanary.LeakCanary

class AvengingApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)
    }
}