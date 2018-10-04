package com.enpassio.architecturepatterns.mvp


interface GetQuoteInteractor {

    interface OnFinishedListener {
        fun onFinished(string: String)
    }

    fun getNextQuote(listener: OnFinishedListener)
}

//view
interface MainView {

    fun showProgress()

    fun hideProgress()

    fun setQuote(string: String)
}


interface Presenter {

    fun onButtonClick()

    fun onDestroy()
}