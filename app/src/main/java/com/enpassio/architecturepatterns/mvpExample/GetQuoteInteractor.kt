package com.enpassio.architecturepatterns.mvpExample

/**
 * Created by Greta Grigutė on 2018-10-06.
 */

interface GetQuoteInteractor {

    interface OnFinishedListener {
        fun onFinished(string: String)
    }

    fun getNextQuote(listener: OnFinishedListener)
}
