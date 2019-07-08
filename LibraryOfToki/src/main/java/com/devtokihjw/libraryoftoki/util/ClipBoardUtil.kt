package com.devtokihjw.libraryoftoki.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

fun Context.copyText(text: String) {
    printLog("copyText", text)
    val mClipData = ClipData.newPlainText(null, text)
    val mClipboardManager = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    mClipboardManager.primaryClip = mClipData
}