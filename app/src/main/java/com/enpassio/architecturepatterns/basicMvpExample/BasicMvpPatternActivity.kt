package com.enpassio.architecturepatterns.basicMvpExample

import android.content.res.Resources
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import com.enpassio.architecturepatterns.R
import kotlinx.android.synthetic.main.basic_mvp_pattern_activity.*
import kotlinx.android.synthetic.main.basic_mvp_pattern_content_main.*


/**
 * Created by Greta Grigutė on 2018-10-08.
 */

//example of basic MVP pattern from:
// https://medium.com/cr8resume/make-you-hand-dirty-with-mvp-model-view-presenter-eab5b5c16e42
class BasicMvpPatternActivity : AppCompatActivity(), MainActivityPresenter.View {

    private var presenter: MainActivityPresenter? = null
    private var progressBar: ProgressBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.basic_mvp_pattern_activity)
        setSupportActionBar(toolbar)

        presenter = MainActivityPresenter(this)
        initProgressBar()


        username.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                presenter!!.updateFullName(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                hideProgressBar()
            }
        })

        email.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                presenter!!.updateEmail(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                hideProgressBar()
            }
        })

    }

    private fun initProgressBar() {
        progressBar = ProgressBar(this, null, android.R.attr.progressBarStyleSmall)
        progressBar!!.isIndeterminate = true
        val params = RelativeLayout.LayoutParams(Resources.getSystem().getDisplayMetrics().widthPixels,
                200)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        this.addContentView(progressBar, params)
        showProgressBar()
    }

    override fun updateUserInfoTextView(info: String) {
        myTextView.text = info
    }

    override fun showProgressBar() {
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        progressBar!!.visibility = View.INVISIBLE
    }
}