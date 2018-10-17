package com.enpassio.core.ui.base

abstract class BasePresenter<V> {

    protected var mView: V? = null

    /**
     * Check if the view is attached.
     * This checking is only necessary when returning from an asynchronous call
     */
    protected val isViewAttached: Boolean
        get() = mView != null

    fun attachView(view: V) {
        mView = view
    }

    fun detachView() {
        mView = null
    }
}