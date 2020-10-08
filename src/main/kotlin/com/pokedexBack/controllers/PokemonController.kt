package com.pokedexBack.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import com.pokedexBack.model.response.PokemonBaseResponse
import com.pokedexBack.model.response.PokemonCatchWayBaseResponse
import com.pokedexBack.model.response.PokemonMovementBaseResponse
import com.pokedexBack.services.PokemonService
import spark.Request
import spark.Response
import javax.servlet.http.HttpServletResponse
import javax.validation.Validator

class PokemonController @Inject constructor(
    private val mapper: ObjectMapper,
    validator: Validator,
    val pokemonService: PokemonService
) : Controller(mapper, validator) {

    companion object {
        private val POKEMON_PARAM = ":id"
    }

    fun getPokemon(request: Request, response: Response): PokemonBaseResponse {
        val pokemonId = getParamFromRequest(POKEMON_PARAM, request)
        val pokemon = pokemonService.getPokemon(pokemonId)
        response.status(HttpServletResponse.SC_OK)
        return PokemonBaseResponse.Builder().createFromPokemon(pokemon).build()
    }

    fun getPokemonCatchWays(request: Request, response: Response): PokemonCatchWayBaseResponse {
        val pokemonId = getParamFromRequest(POKEMON_PARAM, request)
        val pokemonCatchWays = pokemonService.getPokemonCatchWays(pokemonId)
        response.status(HttpServletResponse.SC_OK)
        return PokemonCatchWayBaseResponse.Builder().createFromPokemonCatchWays(pokemonCatchWays).build()
    }

    fun getPokemonMovements(request: Request, response: Response): PokemonMovementBaseResponse {
        val pokemonId = getParamFromRequest(POKEMON_PARAM, request)
        val pokemonMovements = pokemonService.getPokemonMovements(pokemonId)
        response.status(HttpServletResponse.SC_OK)
        return PokemonMovementBaseResponse.Builder().createFromPokemonMovements(pokemonMovements).build()
    }
}
