package com.enpassio.architecturepatterns.mvpExample

/**
 * Created by Greta Grigutė on 2018-10-06.
 */
class MainPresenterImpl(private var mainView: MainView?, private val getQuoteInteractor: GetQuoteInteractor) : MainPresenter, GetQuoteInteractor.OnFinishedListener {

    override fun onButtonClick() {
        if (mainView != null) {
            mainView!!.showProgress()
        }

        getQuoteInteractor.getNextQuote(this)
    }

    override fun onDestroy() {
        mainView = null
    }

    override fun onFinished(string: String) {
        if (mainView != null) {
            mainView!!.setQuote(string)
            mainView!!.hideProgress()
        }
    }
}