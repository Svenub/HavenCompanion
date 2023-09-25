package se.umu.svke0008.havencompanion.data.local.entities.character_entity

import android.graphics.Color
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "game_characters")
data class GameCharacterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val characterName: String,
    val characterType: GameCharacterType,
    val color: String,
    val expansion: String? = null,
    val icon: String? = null,
    val image: String? = null,
    val nameAlias: String,
    val revealed: Boolean
): Parcelable {
    val colorInt: Int
        get() = Color.parseColor(color)
}



fun Int.toColorString(): String {
    return String.format("#%08X", this)
}
