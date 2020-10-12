package com.pokedexBack.model.response

import com.pokedexBack.model.MainPokemon

class PokemonListResponse private constructor(
    val pokemonList: List<MainPokemon>
) {
    data class Builder(
        private var pokemonList: List<MainPokemon>? = null
    ) {
        fun createFromPokemonList(pokemonList: List<MainPokemon>): Builder {
            apply { this.pokemonList = pokemonList }
            return this
        }
        fun build() = PokemonListResponse(pokemonList!!)
    }
}