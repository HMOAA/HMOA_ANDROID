package com.hmoa.util

import android.util.Log
import java.io.InputStream

fun getValueFromJson(inputStream: InputStream, key: String) {
    val jsonString = readJSONFromAssets(inputStream)
    val propertyValue = findPropertyValue(jsonString, key)
    if (propertyValue != null) {
        Log.i("[getValueFromJson]", "Value of $key: $propertyValue")
    } else {
        Log.i("[getValueFromJson]", "Property $key not found.")
    }
}