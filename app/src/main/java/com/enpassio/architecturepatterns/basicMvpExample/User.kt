package com.enpassio.architecturepatterns.basicMvpExample

/**
 * Created by Greta Grigutė on 2018-10-08.
 */

data class User(
        var fullName: String = "",
        var email: String = "") {
    override fun toString(): String {
        return "Email : $email\nFullName : $fullName"
    }
}