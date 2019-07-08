package com.devtokihjw.libraryoftoki.util

import java.io.Serializable

interface Callback<T> : Serializable {

    fun callback(data: T)
}