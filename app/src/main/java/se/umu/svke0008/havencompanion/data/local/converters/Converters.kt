package se.umu.svke0008.havencompanion.data.local.converters

import androidx.room.TypeConverter
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterType
import se.umu.svke0008.havencompanion.domain.model.enhancement.EnhancementType

class Converters {

    @TypeConverter
    fun fromCharacterType(type: GameCharacterType): String = type.name

    @TypeConverter
    fun toCharacterType(name: String): GameCharacterType = GameCharacterType.valueOf(name)

    @TypeConverter
    fun fromEnhancementType(type: EnhancementType): String {
        return when (type) {
            is EnhancementType.NeutralEnhancementType -> "Neutral"
            is EnhancementType.ElementEnhancementType -> "Element"
            is EnhancementType.PositiveEnhancementType -> "Positive"
            is EnhancementType.NegativeEnhancementType -> "Negative"
            is EnhancementType.AreaOfEffectEnhancement -> "Area-of-Effect Hex"
        }
    }

    @TypeConverter
    fun toEnhancementType(typeString: String): EnhancementType {
        return when (typeString) {
            "Neutral" -> EnhancementType.NeutralEnhancementType
            "Element" -> EnhancementType.ElementEnhancementType
            "Positive" -> EnhancementType.PositiveEnhancementType
            "Negative" -> EnhancementType.NegativeEnhancementType
            "Area-of-Effect Hex" -> EnhancementType.AreaOfEffectEnhancement
            else -> throw IllegalArgumentException("Unknown EnhancementType: $typeString")
        }
    }

}
