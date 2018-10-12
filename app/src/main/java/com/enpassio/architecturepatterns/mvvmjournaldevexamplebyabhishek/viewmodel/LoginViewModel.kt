package com.enpassio.architecturepatterns.mvvmjournaldevexamplebyabhishek.viewmodel


import android.databinding.BaseObservable
import android.databinding.Bindable
import com.enpassio.architecturepatterns.BR
import com.enpassio.architecturepatterns.mvvmjournaldevexamplebyabhishek.model.User


class LoginViewModel(
        val successMessage: String = "Login was successful",
        val errorMessage: String = "Email or Password not valid",
        toastMessage: String) : BaseObservable() {

    private val user: User

    @Bindable
    var toastMessage: String = toastMessage
        set(value) {
            if (field != value) {
                field = value
                notifyPropertyChanged(BR.toastMessage)
            }
        }

    init {
        user = User("", "")
    }

    fun afterEmailTextChanged(s: CharSequence) {
        user.email = (s.toString())
    }

    fun afterPasswordTextChanged(s: CharSequence) {
        user.password = (s.toString())
    }

    fun onLoginClicked() {
        if (user.isInputDataValid)
            toastMessage = successMessage
        else
            toastMessage = errorMessage
    }
}