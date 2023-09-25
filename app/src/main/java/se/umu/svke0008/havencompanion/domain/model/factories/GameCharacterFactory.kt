package se.umu.svke0008.havencompanion.domain.model.factories

import androidx.compose.ui.graphics.Color
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterEntity
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter

abstract class GameCharacterFactory {

    abstract fun createGameCharacter(
        name: String, nameAlias: String, color: Color
    ): GameCharacter

    abstract fun createCharacterEntity(
        name: String, nameAlias: String, color: Color
    ): GameCharacterEntity
}