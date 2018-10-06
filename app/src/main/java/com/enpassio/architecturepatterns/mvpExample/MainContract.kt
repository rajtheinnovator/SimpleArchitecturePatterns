package com.enpassio.architecturepatterns.mvpExample

/**
 * Created by Greta Grigutė on 2018-10-06.
 */
interface MainContract {

    interface MainView {
        fun showProgress()

        fun hideProgress()

        fun setQuote(string: String)
    }

    interface GetQuoteInteractor {
        interface OnFinishedListener {
            fun onFinished(string: String)
        }

        fun getNextQuote(onFinishedListener: OnFinishedListener)
    }

    interface Presenter {
        fun onButtonClick()

        fun onDestroy()
    }
}
