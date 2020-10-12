package com.pokedexBack.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.Inject
import com.pokedexBack.enum.QueryCondition
import com.pokedexBack.model.QueryParam
import com.pokedexBack.model.response.PokemonBaseResponse
import com.pokedexBack.model.response.PokemonCatchWayBaseResponse
import com.pokedexBack.model.response.PokemonListResponse
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
        val pokemonId = getLongParamFromRequest(POKEMON_PARAM, request)
        val pokemon = pokemonService.getPokemon(pokemonId)
        response.status(HttpServletResponse.SC_OK)
        return PokemonBaseResponse.Builder().createFromPokemon(pokemon).build()
    }

    fun getPokemonCatchWays(request: Request, response: Response): PokemonCatchWayBaseResponse {
        val pokemonId = getLongParamFromRequest(POKEMON_PARAM, request)
        val pokemonCatchWays = pokemonService.getPokemonCatchWays(pokemonId)
        response.status(HttpServletResponse.SC_OK)
        return PokemonCatchWayBaseResponse.Builder().createFromPokemonCatchWays(pokemonCatchWays).build()
    }

    fun getPokemonMovements(request: Request, response: Response): PokemonMovementBaseResponse {
        val pokemonId = getLongParamFromRequest(POKEMON_PARAM, request)
        val pokemonMovements = pokemonService.getPokemonMovements(pokemonId)
        response.status(HttpServletResponse.SC_OK)
        return PokemonMovementBaseResponse.Builder().createFromPokemonMovements(pokemonMovements).build()
    }

    fun getPokemonList(request: Request, response: Response): PokemonListResponse {
        val pokemonList = pokemonService.getPokemonList()
        response.status(HttpServletResponse.SC_OK)
        return PokemonListResponse.Builder().createFromPokemonList(pokemonList).build()
    }

    fun getPokemonFilteredList(request: Request, response: Response): PokemonListResponse {
        val filters = getPokemonListFilters(request)
        val pokemonList = pokemonService.getPokemonFilteredList(filters)
        response.status(HttpServletResponse.SC_OK)
        return PokemonListResponse.Builder().createFromPokemonList(pokemonList).build()
    }

    private fun getPokemonListFilters (request: Request): List<QueryParam> {
        val queryParamList = listOf<QueryParam>(
            QueryParam(
                "ability_1",
                "=",
                getStringParamFromRequest("ability_1", request)
            ),
            QueryParam(
                "ability_2",
                "=",
                getStringParamFromRequest("ability_2", request)
            ),
            QueryParam(
                "type_1",
                "=",
                getStringParamFromRequest("type_1", request)
            ),
            QueryParam(
                "type_2",
                "=",
                getStringParamFromRequest("type_2", request)
            ),
            QueryParam(
                "hp",
                QueryCondition.queryParamOf(getStringParamFromRequest("ps", request)),
                getStringParamFromRequest("ps_value", request)
            ),
            QueryParam(
                "attack",
                QueryCondition.queryParamOf(getStringParamFromRequest("attack", request)),
                getStringParamFromRequest("attack_value", request)
            ),
            QueryParam(
                "defense",
                QueryCondition.queryParamOf(getStringParamFromRequest("defense", request)),
                getStringParamFromRequest("defense_value", request)
            ),
            QueryParam(
                "sp_attack",
                QueryCondition.queryParamOf(getStringParamFromRequest("sp_attack", request)),
                getStringParamFromRequest("sp_attack_value", request)
            ),
            QueryParam(
                "sp_defense",
                QueryCondition.queryParamOf(getStringParamFromRequest("sp_defense", request)),
                getStringParamFromRequest("sp_defense_value", request)
            ),
            QueryParam(
                "speed",
                QueryCondition.queryParamOf(getStringParamFromRequest("speed", request)),
                getStringParamFromRequest("speed_value", request)
            )
        ).filter { it.condition!="?" }
        .filter { it.value!="?" }
        return queryParamList
    }
}
