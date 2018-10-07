package com.lynnik.aroundme.data.error

class CommonThrowable(
        message: String,
        val errorsMap: Map<String, String>? = null
) : Throwable(message)