package com.pokedexBack.dao

import com.pokedexBack.model.MainPokemon
import com.pokedexBack.model.Pokemon
import com.pokedexBack.model.QueryParam
import java.sql.Connection
import java.sql.DriverManager

class PokemonDAO : BaseDAO() {

    companion object {
        val SELECT_QUERY_BY_ID = """
            SELECT id, name, ability_1, ability_2, type_1, type_2, hp, attack, defense, sp_attack, sp_defense, speed 
            FROM pokemon
            WHERE id = ?
        """.trimIndent().replace("\\n", " ")

        val SELECT_QUERY = """
            SELECT id, name 
            FROM pokemon
        """.trimIndent().replace("\\n", " ")
    }

    fun getPokemonList(): List<MainPokemon> {
        var connection: Connection? = null
        try {
            connection = DriverManager.getConnection(DB_CONNECTION)
            connection.autoCommit = false
            var ps = connection.prepareStatement(SELECT_QUERY)
            val mainPokemonList = mutableListOf<MainPokemon>()

            val rs = ps.executeQuery()
            while (rs.next()) {
                mainPokemonList.add(
                    MainPokemon(
                        rs.getLong(1),
                        rs.getString(2)
                    )
                )
            }

            rs.close()
            ps.close()

            return mainPokemonList
        } catch (e: Exception) {
            throw RuntimeException(e)
        } finally {
            connection?.close()
        }
    }

    fun getPokemonFilteredList(queryConditions: List<QueryParam>): List<MainPokemon> {
        var connection: Connection? = null
        try {
            val query =  """
                SELECT id, name 
                FROM pokemon
                WHERE """ +buildDynamicQueryCondition(queryConditions)+ """
            """.trimIndent().replace("\\n", " ")

            println(query)
            
            connection = DriverManager.getConnection(DB_CONNECTION)
            connection.autoCommit = false
            var ps = connection.prepareStatement(query)
            val mainPokemonList = mutableListOf<MainPokemon>()

            val rs = ps.executeQuery()
            while (rs.next()) {
                mainPokemonList.add(
                        MainPokemon(
                                rs.getLong(1),
                                rs.getString(2)
                        )
                )
            }

            rs.close()
            ps.close()

            return mainPokemonList
        } catch (e: Exception) {
            throw RuntimeException(e)
        } finally {
            connection?.close()
        }
    }

    fun getPokemon(id: Long): Pokemon {
        var connection: Connection? = null
        try {
            connection = DriverManager.getConnection(DB_CONNECTION)
            connection.autoCommit = false
            var ps = connection.prepareStatement(SELECT_QUERY_BY_ID)

            ps.setLong(1, id)

            val rs = ps.executeQuery()
            rs.next()

            val pokemon = Pokemon(
                rs.getLong(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getLong(7),
                rs.getLong(8),
                rs.getLong(9),
                rs.getLong(10),
                rs.getLong(11),
                rs.getLong(12)
            )

            rs.close()
            ps.close()

            return pokemon
        } catch (e: Exception) {
            throw RuntimeException(e)
        } finally {
            connection?.close()
        }
    }
}