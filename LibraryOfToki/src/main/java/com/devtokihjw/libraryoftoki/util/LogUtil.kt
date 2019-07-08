package com.devtokihjw.libraryoftoki.util

import android.util.Log

var isLogEnable = true

fun Any.className() = javaClass.simpleName

fun printLog(tag: String, any: Any?) {
    if (isLogEnable) {
        Log.e(tag, "$any")
    }
}