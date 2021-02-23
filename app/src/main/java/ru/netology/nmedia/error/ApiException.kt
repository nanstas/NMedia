package ru.netology.nmedia.error

import java.io.IOException

class ApiException(val error : ApiError, throwable : Throwable? = null) : IOException(throwable)