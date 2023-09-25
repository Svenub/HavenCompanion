package se.umu.svke0008.havencompanion.data.mappers

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import se.umu.svke0008.havencompanion.data.local.dto.GameCharacterJson

object JsonParser {

    fun parseGameCharactersFromJson(json: String): List<GameCharacterJson> {
        val gson = Gson()
        val type = object : TypeToken<List<GameCharacterJson>>() {}.type
        return gson.fromJson(json, type)
    }
}