package com.pokedexBack.services

import com.google.inject.Inject
import com.pokedexBack.dao.PokemonCatchWayDAO
import com.pokedexBack.dao.PokemonDAO
import com.pokedexBack.dao.PokemonMovementDAO
import com.pokedexBack.model.Movement
import com.pokedexBack.model.Pokemon
import com.pokedexBack.model.PokemonCatchWay

class PokemonService @Inject constructor(
    private val pokemonDAO: PokemonDAO,
    private val pokemonCatchWayDAO: PokemonCatchWayDAO,
    private val pokemonMovementDAO: PokemonMovementDAO
) {
    fun getPokemon(id: Long): Pokemon {
        return pokemonDAO.getPokemon(id)
    }

    fun getPokemonCatchWays(id: Long): List<PokemonCatchWay> {
        return pokemonCatchWayDAO.getPokemonCatchWays(id)
    }

    fun getPokemonMovements(id: Long): List<Movement> {
        return pokemonMovementDAO.getPokemonMovements(id)
    }
}