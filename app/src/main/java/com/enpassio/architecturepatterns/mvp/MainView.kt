package com.enpassio.architecturepatterns.mvp

interface MainView {

    fun showProgress()

    fun hideProgress()

    fun setQuote(string: String)
}