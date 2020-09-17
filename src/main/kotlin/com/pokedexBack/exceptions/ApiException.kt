package com.luriam.exceptions

import java.lang.Exception

open class ApiException(val msg: String, val statusCode: Int, val causex: Throwable?, val reason: Any?) : Exception(msg, causex)
