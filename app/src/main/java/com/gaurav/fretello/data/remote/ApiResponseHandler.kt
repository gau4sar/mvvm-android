package com.gaurav.fretello.data.remote

import okhttp3.ResponseBody

/**
 * A generic class that holds a value with its loading status.
 *
 * ApiResponseHandler is usually used in the Repository classes where they return
 * `LiveData<Result<T>>` to pass back the latest data to the UI with its fetch status.
 */

sealed class ApiResponseHandler<out T> {

    data class Success<out T>(val data: T?) : ApiResponseHandler<T>()

    data class Error(
        val isNetworkError: Boolean,
        val message: String?,
        val data: ResponseBody?
    ) : ApiResponseHandler<Nothing>()

    data class Loading<out T>(val data: T?) : ApiResponseHandler<T>()

    companion object {
        fun <T> loading(value: T?): ApiResponseHandler<T> = Loading(value)

        fun <T> success(value: T): ApiResponseHandler<T> = Success(value)

        fun <T> error(
            isNetworkError: Boolean,
            errorMessage: String,
            data: ResponseBody?
        ): ApiResponseHandler<T> = Error(isNetworkError, errorMessage, data)
    }
}