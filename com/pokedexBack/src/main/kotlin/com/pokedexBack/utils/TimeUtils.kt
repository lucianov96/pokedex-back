package com.pokedexBack.utils

import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object TimeUtils {
    fun getCurrentTimestamp(): String {
        return DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
    }
}
