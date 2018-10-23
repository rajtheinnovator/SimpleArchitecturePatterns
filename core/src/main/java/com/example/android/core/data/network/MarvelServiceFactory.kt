package com.example.android.core.data.network

import com.squareup.leakcanary.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by Greta Grigutė on 2018-10-17.

 * Marvel Service interface factory methods
 */
object MarvelServiceFactory {

    private val BASE_URL = "http://gateway.marvel.com/v1/public/"
    private val HTTP_READ_TIMEOUT = 10000
    private val HTTP_CONNECT_TIMEOUT = 6000

    fun makeMarvelService(): MarvelService {
        return makeMarvelService(makeOkHttpClient())
    }

    private fun makeMarvelService(okHttpClient: OkHttpClient): MarvelService {
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build()

        return retrofit.create(MarvelService::class.java)
    }

    private fun makeOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient().newBuilder()
        httpClientBuilder.connectTimeout(HTTP_CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClientBuilder.readTimeout(HTTP_READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
        httpClientBuilder.addInterceptor(makeLoggingInterceptor())
        return httpClientBuilder.build()
    }

    private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG)
            HttpLoggingInterceptor.Level.BODY
        else
            HttpLoggingInterceptor.Level.NONE
        return logging
    }
}