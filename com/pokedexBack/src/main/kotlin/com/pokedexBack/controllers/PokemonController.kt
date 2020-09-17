package com.pokedexBack.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import spark.Request
import spark.Response
import javax.servlet.http.HttpServletResponse
import javax.validation.Validator

class PokemonController @Inject constructor(
    private val mapper: ObjectMapper,
    validator: Validator
) : Controller(mapper, validator) {

    companion object {
        private val POKEMON_PARAM = ":id"
    }

    fun getPokemon(request: Request, response: Response): Long {
        val pokemonId = getParamFromRequest(POKEMON_PARAM, request)
        response.status(HttpServletResponse.SC_OK)
        return pokemonId
    }
}
