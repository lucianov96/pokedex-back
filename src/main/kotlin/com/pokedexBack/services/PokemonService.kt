package com.pokedexBack.services

import com.google.inject.Inject
import com.pokedexBack.dao.PokemonDAO
import com.pokedexBack.model.Pokemon

class PokemonService @Inject constructor(
    private val pokemonDAO: PokemonDAO
) {
    fun getPokemon(id: Long): Pokemon {
        return pokemonDAO.getPokemon(id)
    }
}