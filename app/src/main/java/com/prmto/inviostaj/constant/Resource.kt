package com.prmto.inviostaj.constant

sealed class Resource<T>(val data: T? = null, val exception: Exception? = null) {
    class Success<T>(data: T) : Resource<T>(data = data)
    class Error<T>(exception: Exception?) : Resource<T>(exception = exception, data = null)
}

fun <T> Resource<T>.onSuccess(action: (T?) -> Unit): Resource<T> {
    if (this is Resource.Success) {
        action(data)
    }
    return this
}

fun <T> Resource<T>.onError(action: (String) -> Unit): Resource<T> {
    if (this is Resource.Error) {
        action(exception?.localizedMessage.toString())
    }
    return this
}