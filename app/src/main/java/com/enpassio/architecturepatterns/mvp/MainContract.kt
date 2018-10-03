package com.enpassio.architecturepatterns.mvp

interface MainView {

    fun showProgress()

    fun hideProgress()

    fun setQuote(string: String)
}

interface GetQuoteInteractor {

    interface OnFinishedListener {
        fun onFinished(string: String)
    }

    fun getNextQuote(listener: OnFinishedListener)
}

interface Presenter {

    fun onButtonClick()

    fun onDestroy()
}