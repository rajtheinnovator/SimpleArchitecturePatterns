package com.enpassio.architecturepatterns.mvpExample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.enpassio.architecturepatterns.R

class MvpPatternActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mvp_pattern_activity)
    }
}
