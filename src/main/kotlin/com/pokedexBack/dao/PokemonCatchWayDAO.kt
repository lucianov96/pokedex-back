package com.pokedexBack.dao

import com.pokedexBack.model.PokemonCatchWay
import java.sql.Connection
import java.sql.DriverManager

class PokemonCatchWayDAO : BaseDAO() {

    companion object {
        val SELECT_QUERY = """
            SELECT pokemon_version, location, way 
            FROM pokemon_catch_way
            WHERE id_pokemon = ?
        """.trimIndent().replace("\\n", " ")
    }

    fun getPokemonCatchWays(id: Long): List<PokemonCatchWay> {
        var connection: Connection? = null
        try {
            connection = DriverManager.getConnection(DB_CONNECTION)
            connection.autoCommit = false
            var ps = connection.prepareStatement(SELECT_QUERY)
            val pokemonCatchWays = mutableListOf<PokemonCatchWay>()

            ps.setLong(1, id)

            val rs = ps.executeQuery()

            while(rs.next()) {
                pokemonCatchWays.add(
                    PokemonCatchWay(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3)
                ))
            }

            rs.close()
            ps.close()

            return pokemonCatchWays
        } catch (e: Exception) {
            throw RuntimeException(e)
        } finally {
            connection?.close()
        }
    }
}