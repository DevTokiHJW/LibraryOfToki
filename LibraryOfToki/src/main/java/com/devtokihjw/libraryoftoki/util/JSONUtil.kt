package com.devtokihjw.libraryoftoki.util

import org.json.JSONArray
import org.json.JSONObject

fun JSONArray.foreach(func: (JSONObject) -> Unit) {
    for (index in 0 until length()) {
        func(getJSONObject(index))
    }
}