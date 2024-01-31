package com.hmoa.util

import android.util.Log
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

fun readJSONFromAssets(inputStream: InputStream): String {
    val identifier = "[ReadJSON]"
    return try {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val stringBuilder = StringBuilder()
        bufferedReader.useLines { lines ->
            lines.forEach {
                stringBuilder.append(it)
            }
        }
        val jsonString = stringBuilder.toString()
        Log.i(identifier, "JSON as String: $jsonString.")
        jsonString
    } catch (e: Exception) {
        Log.e(identifier, "Error reading JSON: $e.")
        e.printStackTrace()
        ""
    }
}