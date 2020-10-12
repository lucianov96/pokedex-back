package com.pokedexBack.controllers

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.JsonMappingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.exc.MismatchedInputException
import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.luriam.exceptions.ApiException
import spark.Request
import javax.validation.Validator

open class Controller(
    private val mapper: ObjectMapper,
    private val validator: Validator
) {
    protected fun <T> parseRequestBody(request: Request, desiredClass: Class<T>): T {
        val parsed = try {
            mapper.readValue(request.body(), desiredClass)
        } catch (e: MissingKotlinParameterException) {
            throw ApiException("key [${getExceptionParameter(e)}] not found", 500, e, null)
        } catch (e: MismatchedInputException) {
            throw ApiException("type mismatch for key [${getExceptionParameter(e)}]", 500, e, null)
        } catch (e: JsonProcessingException) {
            throw ApiException("error in parsing body -> ${e.message}", 500, e, null)
        }

        val errors = validator.validate(parsed)

        if (errors.isNotEmpty()) {
            val message = errors.joinToString(prefix = "[", postfix = "]") { "\"${it.message}\"" }
            throw ApiException(message, 500, null, null)
        }
        return parsed
    }

    fun getLongParamFromRequest(param: String, request: Request): Long {
        return request.params().getOrDefault(param, "0").toLong()
    }

    fun getStringParamFromRequest(param: String, request: Request): String {
        return request.queryParamOrDefault(param, "?")
    }

    private fun getExceptionParameter(e: JsonMappingException): String =
        e.path.joinToString(".") {
            if (it.index >= 0)
                "[${it.index}]"
            else it.fieldName
        }
}
