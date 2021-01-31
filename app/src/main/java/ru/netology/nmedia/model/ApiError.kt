package ru.netology.nmedia.model

import android.content.res.Resources
import ru.netology.nmedia.R
import java.net.ConnectException

sealed class ApiError {
    object ServerError : ApiError()
    object NetworkError : ApiError()
    object UnknownError: ApiError()

    companion object {
        fun fromThrowable(throwable: Throwable) : ApiError =
            when (throwable) {
                is ApiException -> throwable.error
                is ConnectException -> NetworkError
                else -> UnknownError
            }
    }
}

fun ApiError?.getHumanReadableMessage(resources: Resources): String =
    when (this) {
        ApiError.ServerError -> resources.getString(R.string.server_error)
        ApiError.NetworkError -> resources.getString(R.string.network_error)
        ApiError.UnknownError, null -> resources.getString(R.string.unknown_error)
    }