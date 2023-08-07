package com.prmto.inviostaj.data.util

import com.prmto.inviostaj.constant.Resource
import com.prmto.inviostaj.data.remote.dto.ListResponse

suspend fun <T> tryCatchApiCallReturnResource(apiCall: suspend () -> T): Resource<T> {
    return try {
        val response = apiCall()
        Resource.Success(response)
    } catch (e: Exception) {
        Resource.Error(e)
    }
}

fun <T> Resource<ListResponse<T>>.convertToListResource(): Resource<List<T>> {
    return this.data?.let {
        Resource.Success(this.data.results)
    } ?: Resource.Error(this.exception)
}