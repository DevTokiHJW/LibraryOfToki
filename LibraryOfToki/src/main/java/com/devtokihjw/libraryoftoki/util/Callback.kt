package com.devtokihjw.libraryoftoki.util

import android.os.Parcelable

interface Callback<T> : Parcelable {

    fun callback(data: T)
}