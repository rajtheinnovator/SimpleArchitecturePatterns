package com.enpassio.architecturepatterns

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.enpassio.architecturepatterns.mvpfromjournaldev.MvpPatternActivity

class MainActivity : AppCompatActivity() {

    lateinit var mvpButton:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mvpButton  = findViewById(R.id.button_mvp)
        mvpButton.setOnClickListener { startMvpActivity() }
    }

    private fun startMvpActivity() {
        startActivity(Intent(this@MainActivity, MvpPatternActivity::class.java))
    }
}
