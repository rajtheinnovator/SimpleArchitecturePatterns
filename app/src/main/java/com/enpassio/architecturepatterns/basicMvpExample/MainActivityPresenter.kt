package com.enpassio.architecturepatterns.basicMvpExample

/**
 * Created by Greta Grigutė on 2018-10-08.
 */
class MainActivityPresenter(private val view: View) {

    private val user: User

    init {
        this.user = User()
    }

    fun updateFullName(fullName: String) {
        user.fullName = fullName
        view.updateUserInfoTextView(user.toString())

    }

    fun updateEmail(email: String) {
        user.email = email
        view.updateUserInfoTextView(user.toString())

    }

    interface View {

        fun updateUserInfoTextView(info: String)
        fun showProgressBar()
        fun hideProgressBar()

    }
}