package se.umu.svke0008.havencompanion.data.mappers

import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterEntity
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter

sealed interface EntityMapper<E, D>

interface GameCharacterMapper : EntityMapper<GameCharacterEntity, GameCharacter> {

    fun GameCharacter.toEntity(revealed: Boolean = true): GameCharacterEntity
    fun GameCharacterEntity.toDomain(): GameCharacter
}



