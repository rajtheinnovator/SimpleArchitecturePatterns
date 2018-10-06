package com.enpassio.architecturepatterns.mvpExample

/**
 * Created by Greta Grigutė on 2018-10-06.
 */

interface MainView {

    fun showProgress()

    fun hideProgress()

    fun setQuote(string: String)
}
