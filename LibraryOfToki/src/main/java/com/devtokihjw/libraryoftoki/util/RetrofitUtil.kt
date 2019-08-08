package com.devtokihjw.libraryoftoki.util

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.TimeUnit

fun buildClient() = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

fun <Type> Call<Type>.request(TAG: String, onResponse: (Type?, String?) -> Unit, onFailure: (Throwable) -> Unit) {
    enqueue(object : Callback<Type> {
        override fun onFailure(call: Call<Type>, t: Throwable) {
            printLog(TAG, "onFailure = $t")
            onFailure(t)
        }

        override fun onResponse(call: Call<Type>, response: Response<Type>) {
            val body = response.body()
            val errorBody = response.errorBody()?.string()

            printLog(TAG, "onResponse = body = $body, errorBody = $errorBody")
            onResponse(body, errorBody)
        }
    })
}

class ErrorBodyException(message: String?) : Exception(message)