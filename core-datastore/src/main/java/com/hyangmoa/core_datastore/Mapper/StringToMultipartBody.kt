package com.hyangmoa.core_datastore.Mapper

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun String.transformMultipartBody(type : String) : MultipartBody.Part {
    val requestBody = this.toRequestBody("text/plain".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData(type, null, requestBody)
}

fun String.transformRequestBody() : RequestBody {
    return this.toRequestBody("text/plain".toMediaTypeOrNull())
}