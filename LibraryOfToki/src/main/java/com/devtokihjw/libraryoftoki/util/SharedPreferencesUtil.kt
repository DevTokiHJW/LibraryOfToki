package com.devtokihjw.libraryoftoki.util

import android.content.Context
import android.content.SharedPreferences

fun Context.getSharedPreferences(appName: String): SharedPreferences = getSharedPreferences(appName, Context.MODE_PRIVATE)

fun Context.getSharedPreferencesEdit(appName: String): SharedPreferences.Editor = getSharedPreferences(appName).edit()