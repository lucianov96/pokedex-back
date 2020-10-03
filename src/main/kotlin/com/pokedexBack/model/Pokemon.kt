package com.pokedexBack.model

data class Pokemon (
     val id: Long,
     val name: String,
     val ability1: String,
     val ability2: String?,
     val type1: String,
     val type2: String?,
     val hp: Long,
     val attack: Long,
     val defense: Long,
     val spAttack: Long,
     val spDefense: Long,
     val speed: Long
)