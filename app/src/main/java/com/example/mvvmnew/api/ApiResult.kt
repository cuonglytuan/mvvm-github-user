package com.example.mvvmnew.api

import com.example.mvvmnew.vo.error.GithubError

sealed class ApiResult<out T : Any?> {
    data class Success<out T : Any?>(val data: T) : ApiResult<T>()
    sealed class Error : ApiResult<Nothing>() {
        data class ApiError(val statusCode: Int, val githubError: GithubError?) : Error()
        data class Exception(val cause: Throwable) : Error()

        val errorMessage: String
            get() = when (this) {
                is ApiError -> githubError?.displayMessage ?: DEFAULT_ERROR_MESSAGE
                is Exception -> DEFAULT_ERROR_MESSAGE
            }

        fun createErrorMessage(defaultMessage: String) =
            when (this) {
                is ApiError -> githubError?.displayMessage ?: defaultMessage
                is Exception -> defaultMessage
            }
    }

    object Canceled : ApiResult<Nothing>()

    companion object {
        private const val DEFAULT_ERROR_MESSAGE = "Failed to get information\n" +
                "Please try again later"
    }
}

suspend fun <T> ApiResult<T>.onSuccess(callback: suspend (T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) {
        callback(this.data)
    }
    return this
}

suspend fun <T> ApiResult<T>.onError(callback: suspend (ApiResult.Error) -> Unit): ApiResult<T> {
    if (this is ApiResult.Error) {
        callback(this)
    }
    return this
}

suspend fun <T> ApiResult<T>.onFinally(callback: suspend () -> Unit): ApiResult<T> {
    callback()
    return this
}

suspend fun <T, R> ApiResult<T>.map(mapper: suspend (T) -> R): ApiResult<R> =
    when (this) {
        is ApiResult.Canceled -> this
        is ApiResult.Error.ApiError -> this
        is ApiResult.Error.Exception -> this
        is ApiResult.Success -> ApiResult.Success(
            mapper(this.data)
        )
    }

suspend fun <T, R : Any?> ApiResult<T>.onNext(mapper: suspend (T) -> ApiResult<R>): ApiResult<R> =
    when (this) {
        is ApiResult.Canceled -> this
        is ApiResult.Error.ApiError -> this
        is ApiResult.Error.Exception -> this
        is ApiResult.Success -> mapper(this.data)
    }