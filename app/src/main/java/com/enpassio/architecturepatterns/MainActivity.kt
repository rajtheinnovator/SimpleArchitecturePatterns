package com.enpassio.architecturepatterns

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.enpassio.architecturepatterns.mvpExample.MvpPatternActivity
import com.enpassio.architecturepatterns.mvpfinishedwithoutrxanddagger.MvpFinishedWithoutRxAndDaggerActivity
import com.enpassio.architecturepatterns.mvpwithoutrxanddagger.view.MvpWithoutRxAndDaggerActivity
import com.enpassio.architecturepatterns.mvvmExample.MvvmActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_mvp.setOnClickListener { startMvpActivity() }
        button_mvp_without_rx_and_dagger.setOnClickListener { startMvpWithoutRxDaggerActivity() }
        button_mvvm.setOnClickListener { startMvvmActivity() }
        button_mvp_finished.setOnClickListener { startMvpFinishedWithoutRxDaggerActivity() }
    }

    private fun startMvpFinishedWithoutRxDaggerActivity() {
        startActivity(Intent(this@MainActivity, MvpFinishedWithoutRxAndDaggerActivity::class.java))
    }

    private fun startMvpWithoutRxDaggerActivity() {
        startActivity(Intent(this@MainActivity, MvpWithoutRxAndDaggerActivity::class.java))
    }

    private fun startMvpActivity() {
        startActivity(Intent(this@MainActivity, MvpPatternActivity::class.java))
    }

    private fun startMvvmActivity() {
        startActivity(Intent(this@MainActivity, MvvmActivity::class.java))
    }
}
