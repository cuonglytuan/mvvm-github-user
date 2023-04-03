package com.example.mvvmnew.api

import com.example.mvvmnew.vo.error.GithubError
import com.google.gson.Gson
import retrofit2.HttpException
import timber.log.Timber
import java.util.concurrent.CancellationException
import javax.inject.Inject

class Api @Inject constructor(
    private val gson: Gson,
) {

    suspend fun <T : Any?> connect(call: suspend () -> T): ApiResult<T> =
        try {
            ApiResult.Success(call())
        } catch (e: CancellationException) {
            ApiResult.Canceled
        } catch (e: HttpException) {
            val error = e.response()?.errorBody()?.let {
                kotlin.runCatching {
                    gson.fromJson(it.charStream(), GithubError::class.java)
                }.onFailure {
                    Timber.d(it)
                }.getOrNull()
            }

            ApiResult.Error.ApiError(e.code(), error)
        } catch (e: Exception) {
            ApiResult.Error.Exception(e)
        }
}