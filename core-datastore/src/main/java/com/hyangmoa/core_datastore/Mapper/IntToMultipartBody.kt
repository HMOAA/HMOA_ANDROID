package com.hyangmoa.core_datastore.Mapper

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun Array<Int>.transformMultipartBody() : Array<MultipartBody.Part> {
    val array = ArrayList<MultipartBody.Part>()
    this.forEach{
        array.add(it.transformMultipartBody())
    }
    return array.toTypedArray()
}

fun Array<Int>.transformRequestBody() : Array<RequestBody> {
    val array = ArrayList<RequestBody>()
    this.forEach{
        array.add(it.toString().toRequestBody("text/plain".toMediaTypeOrNull()))
    }
    return array.toTypedArray()
}

fun Int.transformMultipartBody() : MultipartBody.Part {
    val result = this.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("deleteCommunityPhotoIds", null, result)
}