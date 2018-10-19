package com.example.android.core

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/**
 * Created by Greta Grigutė on 2018-10-17.
 */
class AvengingApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LeakCanary.install(this)
    }
}
