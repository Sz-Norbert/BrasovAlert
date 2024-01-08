package com.nika.brasovalert.repoitory

sealed class Resource<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = null
) {
    enum class Status {
        SUCCESS,
        ERROR
    }

    class Success<T>(data: T) : Resource<T>(Status.SUCCESS, data)
    class Error<T>(message: String?) : Resource<T>(Status.ERROR, null, message)
}