package com.pokedexBack.model.response

import com.pokedexBack.model.Movement

class PokemonMovementBaseResponse private constructor(
    val pokemonMovements: List<Movement>
) {
    data class Builder(
        private var pokemonMovements: List<Movement> = mutableListOf()
    ) {
        fun createFromPokemonMovements(pokemonCatchWays: List<Movement>): Builder {
            apply { this.pokemonMovements = pokemonCatchWays }
            return this
        }
        fun build() = PokemonMovementBaseResponse(pokemonMovements)
    }
}