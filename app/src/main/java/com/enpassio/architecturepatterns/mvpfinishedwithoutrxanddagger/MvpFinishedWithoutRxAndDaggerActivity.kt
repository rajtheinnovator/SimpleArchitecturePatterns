package com.enpassio.architecturepatterns.mvpfinishedwithoutrxanddagger

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.enpassio.architecturepatterns.R

class MvpFinishedWithoutRxAndDaggerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mvp_finished_without_rx_and_dagger_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.list_container, ListFragment())
                    .commit()
        }
    }
}