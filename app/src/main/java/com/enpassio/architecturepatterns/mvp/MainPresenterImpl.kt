package com.enpassio.architecturepatterns.mvp

//presenter
class MainPresenterImpl(private var mainView: MainView?, private val getQuoteInteractor: GetQuoteInteractor) : Presenter, GetQuoteInteractor.OnFinishedListener {

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