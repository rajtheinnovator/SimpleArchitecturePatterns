package com.enpassio.architecturepatterns.mvpwithoutrxanddagger.model

class User(var fullName: String = "",
           var email: String = "") {

    override fun toString(): String {
        return "Email : $email\nFullName : $fullName"
    }
}