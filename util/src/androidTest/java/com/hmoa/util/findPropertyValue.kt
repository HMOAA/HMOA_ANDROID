package com.hmoa.util

import android.util.Log
import org.json.JSONObject

fun findPropertyValue(jsonString: String, propertyName: String): String? {
    return try {
        val jsonObject = JSONObject(jsonString)
        jsonObject.optString(propertyName)
    } catch (e: Exception) {
        Log.e("[FindPropertyValue]", "Error finding property value: $e")
        null
    }
}