package com.enpassio.architecturepatterns.mvvmjournaldevexamplebyabhishek.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.util.Log
import com.enpassio.architecturepatterns.BR
import com.enpassio.architecturepatterns.mvvmjournaldevexamplebyabhishek.model.User

class LoginViewModel() : BaseObservable() {

    private val user: User
    private val successMessage = "Login was successful"
    private val errorMessage = "Email or Password not valid"

    init {
        user = User("", "")
    }

    var toastMessage: String? = null
        @Bindable
        get() = field
        @Bindable
        set(value) {
            Log.e("my_tag", "value is: " + value)
            field = value
            notifyPropertyChanged(BR.toastMessage)
        }

    fun afterEmailTextChanged(s: CharSequence) {
        user.email = (s.toString())
    }

    fun afterPasswordTextChanged(s: CharSequence) {
        user.password = (s.toString())
    }

    fun onLoginClicked() {
        if (user.isInputDataValid)
            toastMessage = (successMessage)
        else
            toastMessage = (errorMessage)
    }
}