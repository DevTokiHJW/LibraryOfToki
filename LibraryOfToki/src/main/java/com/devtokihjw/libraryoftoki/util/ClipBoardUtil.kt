package com.devtokihjw.libraryoftoki.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun Context.copyText(text: String) {
    printLog("copyText", text)
    (getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager).setPrimaryClip(ClipData.newPlainText(null, text))
}