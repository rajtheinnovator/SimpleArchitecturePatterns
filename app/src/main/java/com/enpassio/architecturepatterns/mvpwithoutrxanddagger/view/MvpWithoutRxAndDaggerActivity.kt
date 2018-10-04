package com.enpassio.architecturepatterns.mvpwithoutrxanddagger.view

import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import com.enpassio.architecturepatterns.R
import com.enpassio.architecturepatterns.mvpwithoutrxanddagger.presenter.MainActivityPresenter


class MvpWithoutRxAndDaggerActivity : AppCompatActivity(), MainActivityPresenter.View {

    private var presenter: MainActivityPresenter? = null
    private var myTextView: TextView? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mvp_without_rx_and_dagger_activity)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        presenter = MainActivityPresenter(this)

        myTextView = findViewById(R.id.myTextView)
        val userName = findViewById<EditText>(R.id.username)
        val email = findViewById<EditText>(R.id.email)

        initProgressBar()

        userName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                presenter!!.updateFullName(s.toString())
                showProgressBar()
            }

            override fun afterTextChanged(s: Editable) {
                val handler = Handler()
                handler.postDelayed({
                    hideProgressBar()
                }, 1000)

            }
        })

        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                presenter!!.updateEmail(s.toString())
                showProgressBar()
            }

            override fun afterTextChanged(s: Editable) {
                val handler = Handler()
                handler.postDelayed({
                    hideProgressBar()
                }, 1000)
            }

        })

    }

    private fun initProgressBar() {
        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleSmall)
        progressBar!!.setIndeterminate(true)
        val params = RelativeLayout.LayoutParams(Resources.getSystem().getDisplayMetrics().widthPixels,
                250)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        this.addContentView(progressBar, params)
        hideProgressBar()

    }

    override fun updateUserInfoTextView(info: String) {
        myTextView!!.text = info
    }

    override fun showProgressBar() {
        progressBar!!.setVisibility(View.VISIBLE)
    }

    override fun hideProgressBar() {
        progressBar!!.setVisibility(View.INVISIBLE)
    }
}
