package com.prmto.inviostaj.data.util

import com.prmto.inviostaj.constant.Resource

fun <T> createResourceWithStatus(isSuccess: Boolean, data: T): Resource<T> {
    return if (isSuccess) {
        Resource.Success(data)
    } else {
        Resource.Error(Exception("Error"))
    }
}
