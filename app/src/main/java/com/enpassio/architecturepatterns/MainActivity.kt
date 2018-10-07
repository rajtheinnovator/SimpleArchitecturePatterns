package com.enpassio.architecturepatterns

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.enpassio.architecturepatterns.basicMvpExample.BasicMvpPatternActivity
import com.enpassio.architecturepatterns.mvpExample.MvpPatternActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_mvp.setOnClickListener { startMvpActivity() }

        button_basic_mvp.setOnClickListener {startBasicMvpActivity() }
    }

    private fun startMvpActivity() {
        startActivity(Intent(this@MainActivity, MvpPatternActivity::class.java))
    }

    private fun startBasicMvpActivity(){
        startActivity(Intent(this@MainActivity, BasicMvpPatternActivity::class.java))
    }
}
