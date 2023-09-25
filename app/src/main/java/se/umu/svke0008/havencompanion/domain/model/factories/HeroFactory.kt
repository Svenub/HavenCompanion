package se.umu.svke0008.havencompanion.domain.model.factories

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterEntity
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterType
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.presentation.util_components.UiUtils

class HeroFactory : GameCharacterFactory() {


    override fun createGameCharacter(name: String, nameAlias: String, color: Color): GameCharacter {
        return GameCharacter.Hero(
            id = 0,
            characterName = name,
            nameAlias = nameAlias.split(","),
            colorInt = color.toArgb()
        )
    }

    override fun createCharacterEntity(
        name: String,
        nameAlias: String,
        color: Color
    ): GameCharacterEntity {
        return GameCharacterEntity(
            characterName = name,
            characterType = GameCharacterType.Hero,
            nameAlias = nameAlias,
            color = UiUtils.colorToARGBString(color),
            revealed = true
        )
    }
}