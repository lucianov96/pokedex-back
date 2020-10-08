package com.pokedexBack.model

data class Movement (
    val name: String,
    val type: String,
    val movementType: String,
    val points: Long,
    val accuracy: Long,
    val priority: Long
)