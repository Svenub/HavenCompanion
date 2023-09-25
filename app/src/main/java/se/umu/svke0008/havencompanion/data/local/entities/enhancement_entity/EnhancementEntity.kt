package se.umu.svke0008.havencompanion.data.local.entities.enhancement_entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import se.umu.svke0008.havencompanion.domain.model.enhancement.EnhancementType

@Entity(tableName = "enhancement_table")
data class EnhancementEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val baseCost: Int,
    val enhancementType: EnhancementType = EnhancementType.NeutralEnhancementType
)



