package com.enpassio.architecturepatterns.mvpExample

/**
 * Created by Greta Grigutė on 2018-10-06.
 */
class MainPresenterImpl(private var mainView: MainContract.MainView?, private val getQuoteInteractor: MainContract.GetQuoteInteractor) : MainContract.Presenter, MainContract.GetQuoteInteractor.OnFinishedListener {

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