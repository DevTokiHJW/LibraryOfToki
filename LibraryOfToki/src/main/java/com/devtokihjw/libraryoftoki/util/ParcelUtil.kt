package com.devtokihjw.libraryoftoki.util

import android.os.Parcel

inline fun <reified T> Parcel.readHashMap() = readHashMap(T::class.java.classLoader) as HashMap<String, Any>