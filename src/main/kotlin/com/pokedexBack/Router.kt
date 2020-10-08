package com.pokedexBack
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.AbstractModule
import com.google.inject.Guice
import com.luriam.exceptions.ApiException
import com.pokedexBack.providers.ObjectMapperProvider
import com.luriam.providers.ValidatorProvider
import com.pokedexBack.controllers.PokemonController
import spark.Request
import spark.Response
import spark.ResponseTransformer
import spark.Route
import spark.Spark.*
import spark.kotlin.before
import javax.validation.Validator

class Router {
    private val injector = Guice.createInjector(Module())

    fun addRoutes() {
        val pokemonController = injector.getInstance(PokemonController::class.java)

        port(80)

        path("pokemon/:id") {
            get(pokemonController::getPokemon, toJson())
            path("/catch") {
                get(pokemonController::getPokemonCatchWays, toJson())
            }
            path("/movements") {
                get(pokemonController::getPokemonMovements, toJson())
            }
        }

        get("ping") { _, _ ->
            "pong"
        }

        loadErrorHandlerRoutes()
        cors()
    }

    private fun cors() {
        options("/*"
        ) { request, response ->

            val accessControlRequestHeaders = request
                .headers("Access-Control-Request-Headers")
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers",
                    accessControlRequestHeaders)
            }

            val accessControlRequestMethod = request
                .headers("Access-Control-Request-Method")
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods",
                    accessControlRequestMethod)
            }

            "OK"
        }

        before {
            response.header("Access-Control-Allow-Origin", "*")
            response.header("Content-Type", "Application/Json")
        }
    }

    private fun loadErrorHandlerRoutes() {
        loadBadRequestHandler()
    }

    private fun loadBadRequestHandler() {
        exception(ApiException::class.java) { e, _, response ->
            val mapper = injector.getInstance(ObjectMapper::class.java)
            val err = mapOf(
                "except" to e::class.java,
                "error" to e.message,
                "stack_trace" to e.stackTrace!!.contentToString()
            )
            response.status(e.statusCode)
            response.body(mapper.writeValueAsString(err))
        }
    }

    private fun delete(function: (Request, Response) -> Any, t: ResponseTransformer) {
        return delete("", Route(function), t)
    }

    private fun post(path: String, function: (Request, Response) -> Any, t: ResponseTransformer) {
        return post(path, Route(function), t)
    }

    private fun post(function: (Request, Response) -> Any, t: ResponseTransformer) {
        return post("", Route(function), t)
    }

    private fun put(function: (Request, Response) -> Any, t: ResponseTransformer) {
        return put("", Route(function), t)
    }

    private fun get(path: String, function: (Request, Response) -> Any?, t: ResponseTransformer) {
        return get(path, Route(function), t)
    }

    private fun get(function: (Request, Response) -> Any?, t: ResponseTransformer) {
        return get("", Route(function), t)
    }

    private fun toJson(): ResponseTransformer = object : ResponseTransformer {
        val objectMapper = injector.getInstance(ObjectMapper::class.java)
        override fun render(model: Any?): String =
            objectMapper.writeValueAsString(model)
    }
}

class Module : AbstractModule() {
    override fun configure() {
        bind(ObjectMapper::class.java)
            .toProvider(ObjectMapperProvider::class.java)
        bind(Validator::class.java).toProvider(ValidatorProvider::class.java)
    }
}
