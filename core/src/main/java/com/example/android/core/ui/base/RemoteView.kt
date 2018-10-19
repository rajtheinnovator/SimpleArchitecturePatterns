package com.example.android.core.ui.base

/**
 * Created by Greta Grigutė on 2018-10-17.
 */

interface RemoteView {

    fun showProgress()

    fun hideProgress()

    fun showUnauthorizedError()

    fun showEmpty()

    fun showError(errorMessage: String)

    fun showMessageLayout(show: Boolean)
}
