package com.prmto.inviostaj.data.util

import com.prmto.inviostaj.constant.Resource

suspend fun <T> tryCatchFetchDataReturnResource(fetchData: suspend () -> T): Resource<T> {
    return try {
        val response = fetchData()
        Resource.Success(response)
    } catch (e: Exception) {
        Resource.Error(e)
    }
}