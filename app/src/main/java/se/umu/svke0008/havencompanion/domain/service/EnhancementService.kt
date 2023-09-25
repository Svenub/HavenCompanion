package se.umu.svke0008.havencompanion.domain.service

import se.umu.svke0008.havencompanion.data.local.entities.enhancement_entity.EnhancementEntity
import se.umu.svke0008.havencompanion.domain.model.enhancement.EnhancementRuleState

interface EnhancementService {

    fun calculateEnhancementCost(enhancement: EnhancementEntity, enhancementRuleState: EnhancementRuleState): Int
}