package com.luriam.exceptions

open class BadRequestException(message: String, cause: Throwable? = null, reason: Any? = null) : ApiException(message, 403, cause, reason)
