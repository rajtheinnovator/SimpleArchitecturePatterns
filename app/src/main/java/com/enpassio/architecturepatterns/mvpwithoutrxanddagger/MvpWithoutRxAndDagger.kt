package com.enpassio.architecturepatterns.mvpwithoutrxanddagger

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class MvpWithoutRxAndDagger : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mvp_without_rx_and_dagger_activity)
    }
}
