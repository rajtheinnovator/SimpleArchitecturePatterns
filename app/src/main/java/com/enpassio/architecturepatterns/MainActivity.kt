package com.enpassio.architecturepatterns

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.enpassio.architecturepatterns.mvpExample.MvpPatternActivity
import com.enpassio.architecturepatterns.mvpwithoutrxanddagger.view.MvpWithoutRxAndDaggerActivity

class MainActivity : AppCompatActivity() {

    lateinit var mvpButton:Button
    lateinit var mvpWithoutRxAndDagger: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mvpButton  = findViewById(R.id.button_mvp)
        mvpButton.setOnClickListener { startMvpActivity() }
        mvpWithoutRxAndDagger = findViewById(R.id.button_mvp_without_rx_and_dagger)
        mvpWithoutRxAndDagger.setOnClickListener { startMvpWithoutRxDaggerActivity() }
    }

    private fun startMvpWithoutRxDaggerActivity() {
        startActivity(Intent(this@MainActivity, MvpWithoutRxAndDaggerActivity::class.java))
    }

    private fun startMvpActivity() {
        startActivity(Intent(this@MainActivity, MvpPatternActivity::class.java))
    }
}
