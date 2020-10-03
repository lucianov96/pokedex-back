package com.pokedexBack.dao

import com.birras.model.Beer
import java.sql.Connection
import java.sql.DriverManager

class BeerDAO : BaseDAO() {

    companion object {
        val SELECT_QUERY = """
            SELECT id, mark, quantity 
            FROM beer
        """.trimIndent().replace("\\n", " ")
    }

    fun getBeers(): List<Beer> {
        var connection: Connection? = null
        try {
            val beerList = mutableListOf<Beer>()
            connection = DriverManager.getConnection(DB_CONNECTION)
            connection.autoCommit = false
            var ps = connection.prepareStatement(SELECT_QUERY)

            val rs = ps.executeQuery()

            while (rs.next()) {
                beerList.add(Beer(
                    rs.getLong(1),
                    rs.getString(2),
                    rs.getLong(3)
                ))
            }

            rs.close()
            ps.close()

            return beerList
        } catch (e: Exception) {
            throw RuntimeException(e)
        } finally {
            connection?.close()
        }
    }
}