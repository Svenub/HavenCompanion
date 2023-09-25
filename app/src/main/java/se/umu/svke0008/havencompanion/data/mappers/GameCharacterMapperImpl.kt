package se.umu.svke0008.havencompanion.data.mappers


import androidx.room.TypeConverter
import se.umu.svke0008.havencompanion.data.local.dto.GameCharacterJson
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterEntity
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterType
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.presentation.util_components.UiUtils


object GameCharacterMapperImpl : GameCharacterMapper, DtoMapper<GameCharacterJson, GameCharacterEntity> {


    @TypeConverter
    override fun GameCharacter.toEntity(revealed: Boolean): GameCharacterEntity {
        val concatString = this.nameAlias.joinToString(",")

        return when (this) {
            is GameCharacter.Hero -> GameCharacterEntity(
                id = this.id,
                characterType = GameCharacterType.Hero,
                characterName = this.characterName,
                color = UiUtils.colorToARGBString(this.color),
                nameAlias = concatString,
                revealed = revealed
            )

            is GameCharacter.Monster -> GameCharacterEntity(
                id = this.id,
                characterType = GameCharacterType.Monster,
                characterName = this.characterName,
                color = UiUtils.colorToARGBString(this.color),
                nameAlias = concatString,
                revealed = revealed
            )
        }
    }


    @TypeConverter
    override fun GameCharacterEntity.toDomain(): GameCharacter {
        val nameAliasList = this.nameAlias.split(",")

        return when (this.characterType) {
            GameCharacterType.Hero -> GameCharacter.Hero(
                id = this.id?: 0,
                characterName = this.characterName,
                nameAlias = nameAliasList,
                colorInt = this.colorInt,
            )

            GameCharacterType.Monster -> GameCharacter.Monster(
                id = this.id?: 0,
                characterName = this.characterName,
                nameAlias = nameAliasList,
                colorInt = this.colorInt
            )
        }
    }

    override fun GameCharacterJson.toEntity(): GameCharacterEntity {
        return  GameCharacterEntity(
            characterName = this.character_name,
            characterType = GameCharacterType.valueOf(this.character_type),
            color = this.color,
            expansion = this.expansion,
            icon = this.icon,
            image = this.image,
            nameAlias = this.name_alias.joinToString(","),
            revealed = this.revealed
        )
    }


}
