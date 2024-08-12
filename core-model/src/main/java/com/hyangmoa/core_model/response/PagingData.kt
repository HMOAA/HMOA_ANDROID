package com.hyangmoa.core_model.response

import kotlinx.serialization.Serializable

@Serializable
data class PagingData<T>(
    val data : List<T>,
    val lastPage : Boolean
)
