package com.devtokihjw.libraryoftoki.util

import com.google.firebase.iid.FirebaseInstanceId

fun initFirebaseCloudMessaging(func: (String?) -> Unit) {
    val mFirebaseInstanceId = FirebaseInstanceId.getInstance()
    mFirebaseInstanceId.instanceId.addOnCompleteListener { task ->
        if (task.isSuccessful) {
            printLog(mFirebaseInstanceId.className(), "isSuccessful = true")
            val token = task.result?.token
            printLog(mFirebaseInstanceId.className(), "token = $token")
            func(token)
        } else {
            printLog(mFirebaseInstanceId.className(), "isSuccessful = false")
            func(null)
        }
    }
}