package com.enpassio.architecturepatterns.mvpfromjournaldev

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.View.GONE
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import com.enpassio.architecturepatterns.R


class MvpPatternActivity : AppCompatActivity(), MainView {

    lateinit var textView: TextView
    lateinit var button: Button
    lateinit var progressBar: ProgressBar
    internal var presenter: Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mvp_pattern_activity)

        textView = findViewById(R.id.textView)
        button = findViewById(R.id.button)
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        presenter = MainPresenterImpl(this, GetQuoteInteractorImpl())

        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                presenter?.onButtonClick()
            }
        })
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.onDestroy()
    }

    override fun showProgress() {
        progressBar.setVisibility(View.VISIBLE)
        textView.visibility = View.INVISIBLE
    }

    override fun hideProgress() {
        progressBar.setVisibility(GONE)
        textView.visibility = View.VISIBLE
    }

    override fun setQuote(string: String) {
        textView.text = string
    }
}
