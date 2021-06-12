package com.gaurav.fretello.utils

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.gaurav.fretello.data.remote.ApiResponseHandler
import com.gaurav.fretello.databinding.CustomNetworkFailedBinding
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Fragment.handleApiError(
    error: ApiResponseHandler.Error,
    activity: FragmentActivity
) {
    when {
        error.isNetworkError -> {
            activity.checkNetworkConnection(activity as LifecycleOwner)
        }

        else -> {

            when (error.message) {
                // Handle expected scenarios. Example : Authentication failure, token expiry, etc
            }

            val errorMessage = error.data?.string().toString()
            activity.showLongToast(errorMessage)
        }
    }
}


fun Activity.checkNetworkConnection(lifecycleOwner: LifecycleOwner) {
    val networkConnection = NetworkConnection(applicationContext)
    networkConnection.observe(lifecycleOwner, { isNetworkConnected ->
        Timber.d("checkNetworkConnection isNetworkConnected : $isNetworkConnected")
        if (!isNetworkConnected) {
            val binding = CustomNetworkFailedBinding.inflate(layoutInflater)
            this.setContentView(binding.root)

            binding.buttonRetryConnection.setOnClickListener {
                Timber.d("Not connected to the network !! Retry called !!! ")
                this.recreate()
            }
        }
    })
}

fun getFormattedDateTime(dateTimeString: String): String {
    val parsedDate = LocalDateTime.parse(dateTimeString, DateTimeFormatter.ISO_DATE_TIME)
    return parsedDate.format(DateTimeFormatter.ofPattern(Constants.CONVERTED_DATE_FORMAT))
}

fun <T, L> responseLiveData(
    roomQueryToRetrieveData: () -> LiveData<T>,
    networkRequest: suspend () -> ApiResponseHandler<L>,
    roomQueryToSaveData: suspend (L) -> Unit
): LiveData<ApiResponseHandler<T>> = liveData(Dispatchers.IO) {

    emit(ApiResponseHandler.loading(null))

    // Get data from room db
    val source = roomQueryToRetrieveData().map { ApiResponseHandler.success(it) }
    emitSource(source)
    Timber.d("Room query to get session : $latestValue")

    // Update data to room db from API
    when (val apiResponse = networkRequest()) {

        is ApiResponseHandler.Success -> {
            apiResponse.data?.let {
                Timber.d("networkRequest to get session : $source")
                roomQueryToSaveData(it)
            }
        }

        is ApiResponseHandler.Error -> {
            emit(
                ApiResponseHandler.error(
                    false,
                    apiResponse.message ?: "API response error",
                    null
                )
            )
            emitSource(source)
        }

        else -> {
            emit(
                ApiResponseHandler.error(
                    true,
                    "Something went wrong, please try again later",
                    null
                )
            )
            emitSource(source)
        }
    }
}
