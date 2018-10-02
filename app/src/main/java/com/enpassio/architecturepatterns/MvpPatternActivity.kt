package com.enpassio.architecturepatterns

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class MvpPatternActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mvp_pattern_activity)
    }
}
