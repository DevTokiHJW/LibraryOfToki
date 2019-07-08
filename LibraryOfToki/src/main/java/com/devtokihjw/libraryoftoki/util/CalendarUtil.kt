package com.devtokihjw.libraryoftoki.util

import java.text.SimpleDateFormat
import java.util.*

fun Long.formatOfyyyyMMddHHmmss() = SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.getDefault()).format(Calendar.getInstance().apply { timeInMillis = this@formatOfyyyyMMddHHmmss }.time)