package se.umu.svke0008.havencompanion.data.local.dto

data class GameCharacterJson(
    val expansion: String,
    val character_name: String,
    val character_type: String,
    val name_alias: List<String>,
    val icon: String,
    val color: String,
    val image: String,
    val revealed: Boolean
)
