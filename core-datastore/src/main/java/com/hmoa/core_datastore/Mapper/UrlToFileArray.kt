package com.hmoa.core_datastore.Mapper

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.MultipartBody.Part.Companion.createFormData
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

fun Array<File>.transformToMultipartBody() : Array<MultipartBody.Part>{
    val returnArray = arrayListOf<MultipartBody.Part>()
    this.map{
        val requestFile = it.asRequestBody("imag5/*".toMediaTypeOrNull())
        val image = createFormData("image", it.name, requestFile)
        returnArray.add(image)
    }
    return returnArray.toTypedArray()
}

fun File.transformToMultipartBody() : MultipartBody.Part {
    val requestFile = this.asRequestBody("imag5/*".toMediaTypeOrNull())
    return createFormData("image", this.name, requestFile)
}