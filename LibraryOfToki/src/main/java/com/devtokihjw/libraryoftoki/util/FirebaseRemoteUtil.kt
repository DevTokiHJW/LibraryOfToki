package com.devtokihjw.libraryoftoki.util

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

val mFirebaseRemoteConfigSettings: FirebaseRemoteConfigSettings by lazy {
    FirebaseRemoteConfigSettings.Builder()
            .setFetchTimeoutInSeconds(1000 * 30)
            .setMinimumFetchIntervalInSeconds(60 * 60)
            .build()
}

fun initFirebaseRemoteConfig(func: (FirebaseRemoteConfig?) -> Unit) {
    val mFirebaseRemoteConfig = FirebaseRemoteConfig.getInstance()
    mFirebaseRemoteConfig.setConfigSettingsAsync(mFirebaseRemoteConfigSettings).addOnCompleteListener {
        if (it.isSuccessful) {
            printLog(mFirebaseRemoteConfig.className(), "setConfigSettingsAsync isSuccessful")
            mFirebaseRemoteConfig.doFetchAndActivate(func)
        } else {
            printLog(mFirebaseRemoteConfig.className(), "setConfigSettingsAsync isFailure")
            func(null)
        }
    }
}

fun FirebaseRemoteConfig.doFetchAndActivate(func: (FirebaseRemoteConfig?) -> Unit) {
    fetchAndActivate().addOnCompleteListener {
        if (it.isSuccessful) {
            printLog(className(), "fetchAndActivate isSuccessful")
            func(this)
        } else {
            printLog(className(), "fetchAndActivate isFailure")
            func(null)
        }
    }
}