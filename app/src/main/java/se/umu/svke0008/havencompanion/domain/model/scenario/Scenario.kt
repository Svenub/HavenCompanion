package se.umu.svke0008.havencompanion.domain.model.scenario

import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter


data class Scenario(
    val id: Int? = null,
    val name: String,
    val gameCharacters: List<GameCharacter>? = emptyList()
)

