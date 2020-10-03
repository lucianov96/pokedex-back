package com.pokedexBack.dao

import com.birras.model.DBProperties
import com.pokedexBack.model.DBProperties
import java.io.FileNotFoundException
import java.util.*

open class PokemonDAO {
    val DB_CONNECTION by lazy { getConnection() }

    private fun getConnection(): String {
        val properties = getProperties()
        return "jdbc:mysql://${properties.host}/${properties.database}?user=${properties.user}&password=${properties.password}&useSSL=false"
    }

    private fun getProperties(): DBProperties {
        val prop = Properties()
        val propFileName = "database.properties"
        val inputStream = javaClass.classLoader.getResourceAsStream(propFileName)
        if (inputStream != null) {
            prop.load(inputStream)
        } else {
            throw FileNotFoundException("property file $propFileName not found")
        }
        val host = prop.getProperty("host")
        val database = prop.getProperty("database")
        val user = prop.getProperty("user")
        val password = prop.getProperty("password")
        return DBProperties(host, database, user, password)
    }
}