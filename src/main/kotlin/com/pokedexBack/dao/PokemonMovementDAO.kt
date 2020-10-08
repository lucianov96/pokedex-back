package com.pokedexBack.dao

import com.pokedexBack.model.Movement
import com.pokedexBack.model.PokemonCatchWay
import java.sql.Connection
import java.sql.DriverManager

class PokemonMovementDAO : BaseDAO() {

    companion object {
        val SELECT_QUERY = """
            SELECT movement.name, movement.type, movement.movement_type, movement.points, movement.accuracy, movement.priority
            FROM movement
            INNER JOIN pokemon_movement ON movement.id = pokemon_movement.id_movement where id_pokemon = ?
        """.trimIndent().replace("\\n", " ")
    }

    fun getPokemonMovements(id: Long): List<Movement> {
        var connection: Connection? = null
        try {
            connection = DriverManager.getConnection(DB_CONNECTION)
            connection.autoCommit = false
            var ps = connection.prepareStatement(SELECT_QUERY)
            val movements = mutableListOf<Movement>()

            ps.setLong(1, id)

            val rs = ps.executeQuery()

            while(rs.next()) {
                movements.add(
                    Movement(
                    rs.getString(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getLong(4),
                    rs.getLong(5),
                    rs.getLong(6)
                ))
            }

            rs.close()
            ps.close()

            return movements
        } catch (e: Exception) {
            throw RuntimeException(e)
        } finally {
            connection?.close()
        }
    }
}