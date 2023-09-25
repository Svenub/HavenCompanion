package se.umu.svke0008.havencompanion.domain.model.enhancement

import se.umu.svke0008.havencompanion.data.local.entities.enhancement_entity.EnhancementEntity
import se.umu.svke0008.havencompanion.presentation.util_components.ResourceUtils

data class Enhancement(
    val id: Int,
    val name: String,
    val baseCost: Int,
    val calculatedCost: Int,
    val enhancementType: EnhancementType,
    val icon: Pair<Int, Boolean>
)

fun EnhancementEntity.toDomain(calculatedCost: Int): Enhancement {
    return Enhancement(
        id = this.id,
        name = this.name,
        baseCost = this.baseCost,
        calculatedCost = calculatedCost,
        enhancementType = this.enhancementType,
        icon = ResourceUtils.isEnhancementIconColored(this.name)
    )
}