package com.enpassio.architecturepatterns.mvpExample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.enpassio.architecturepatterns.R
import kotlinx.android.synthetic.main.mvp_pattern_activity.*

//example of MVP pattern from: https://www.journaldev.com/14886/android-mvp

class MvpPatternActivity : AppCompatActivity(), MainContract.MainView {

    internal var presenter: MainContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mvp_pattern_activity)

        presenter = MainPresenterImpl(this, GetQuoteInteractorImpl())

        button.setOnClickListener {
                presenter!!.onButtonClick()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter!!.onDestroy()
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
        textView.visibility = View.INVISIBLE
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
        textView.visibility = View.VISIBLE
    }

    override fun setQuote(string: String) {
        textView.text = string
    }
}