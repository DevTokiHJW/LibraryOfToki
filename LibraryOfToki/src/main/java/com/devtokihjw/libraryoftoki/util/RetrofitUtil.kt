package com.devtokihjw.libraryoftoki.util

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

fun String.buildRetrofit() = Retrofit.Builder()
        .baseUrl(this)
        .client(buildClient())
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

private fun buildClient() = OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

fun Call<ResponseBody>.request(TAG: String, onResponse: (ResponseBody?, String?) -> Unit, onFailure: (Throwable) -> Unit) {
    enqueue(object : Callback<ResponseBody> {
        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            printLog(TAG, "onFailure = $t")
            onFailure(t)
        }

        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
            val body = response.body()
            val errorBody = response.errorBody()?.string()

            printLog(TAG, "onResponse = body = $body, errorBody = $errorBody")
            onResponse(body, errorBody)
        }
    })
}

class ErrorBodyException(message: String?) : Exception(message)