package com.luriam.providers
import com.google.inject.Provider
import javax.validation.Validation
import javax.validation.Validator

class ValidatorProvider : Provider<Validator> {
    override fun get(): Validator = Validation.buildDefaultValidatorFactory().validator
}
