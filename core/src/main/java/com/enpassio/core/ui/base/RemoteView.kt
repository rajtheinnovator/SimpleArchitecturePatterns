package com.enpassio.core.ui.base

interface RemoteView {

    fun showProgress()

    fun hideProgress()

    fun showUnauthorizedError()

    fun showEmpty()

    fun showError(errorMessage: String)

    fun showMessageLayout(show: Boolean)
}