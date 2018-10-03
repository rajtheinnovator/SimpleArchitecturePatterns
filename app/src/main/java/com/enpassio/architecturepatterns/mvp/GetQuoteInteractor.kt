package com.enpassio.architecturepatterns.mvp

interface GetQuoteInteractor {

    interface OnFinishedListener {
        fun onFinished(string: String)
    }

    fun getNextQuote(listener: OnFinishedListener)
}