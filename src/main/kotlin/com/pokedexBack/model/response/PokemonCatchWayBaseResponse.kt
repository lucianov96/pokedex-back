package com.pokedexBack.model.response

import com.pokedexBack.model.PokemonCatchWay

class PokemonCatchWayBaseResponse private constructor(
    val pokemonCatchWays: List<PokemonCatchWay>
) {
    data class Builder(
        private var pokemonCatchWays: List<PokemonCatchWay> = mutableListOf()
    ) {
        fun createFromPokemonCatchWays(pokemonCatchWays: List<PokemonCatchWay>): Builder {
            apply { this.pokemonCatchWays = pokemonCatchWays }
            return this
        }
        fun build() = PokemonCatchWayBaseResponse(pokemonCatchWays)
    }
}