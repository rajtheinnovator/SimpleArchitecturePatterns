package com.enpassio.core.data.network

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.HttpURLConnection
import javax.net.ssl.HttpsURLConnection

abstract class RemoteCallback<T> : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        when (response.code()) {
            HttpsURLConnection.HTTP_OK, HttpsURLConnection.HTTP_CREATED, HttpsURLConnection.HTTP_ACCEPTED, HttpsURLConnection.HTTP_NOT_AUTHORITATIVE -> if (response.body() != null) {
                onSuccess(response.body())
            }

            HttpURLConnection.HTTP_UNAUTHORIZED -> onUnauthorized()

            else -> onFailed(Throwable("Default " + response.code() + " " + response.message()))
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        onFailed(t)
    }

    abstract fun onSuccess(response: T)

    abstract fun onUnauthorized()

    abstract fun onFailed(throwable: Throwable)
}