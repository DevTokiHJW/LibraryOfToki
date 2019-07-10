package com.devtokihjw.libraryoftoki.util

import java.security.MessageDigest

fun String.formatSHA256(): String {
    printLog("formatSHA256", "beforeValue = $this")
    val mMessageDigest = MessageDigest.getInstance("SHA-256")
    mMessageDigest.update(toByteArray())
    val result = StringBuilder().apply {
        mMessageDigest.digest().forEach {
            val hex = Integer.toHexString(it.toInt() and 0xFF)
            if (hex.length == 1) {
                append("0")
            }
            append(hex)
        }
    }.toString().toLowerCase()
    printLog("formatSHA256", "afterValue = $this")
    return result
}