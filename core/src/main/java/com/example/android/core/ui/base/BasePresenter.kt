package com.example.android.core.ui.base

/**
 * Created by Greta Grigutė on 2018-10-17.
 */

abstract class BasePresenter<V> {

    protected var mView: V? = null

    /**
     * Check if the view is attached.
     * This checking is only necessary when returning from an asynchronous call
     */
   fun isViewAttached():Boolean{
        return mView != null}

    fun attachView(view: V) {
        mView = view
    }

    fun detachView() {
        mView = null
    }
}
