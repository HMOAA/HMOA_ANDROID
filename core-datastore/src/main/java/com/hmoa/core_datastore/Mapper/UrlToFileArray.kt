package com.hmoa.core_datastore.Mapper

import android.net.Uri
import android.util.Log
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun Array<File>.transformToMultipartBody() : Array<MultipartBody.Part>{
    val returnArray = arrayListOf<MultipartBody.Part>()
    this.map{
        val requestFile = it.asRequestBody("imag5/*".toMediaTypeOrNull())
        Log.d("TAG TEST", "request file : ${requestFile}")
        val image = createFormData("image", it.name, requestFile)
        Log.d("TAG TEST", "Image : ${image.body}")
        returnArray.add(image)
    }
    Log.d("TAG TEST", "return array : ${returnArray} / size : ${returnArray.size}")
    return returnArray.toTypedArray()
}