package se.umu.svke0008.havencompanion.domain.model.enhancement

data class EnhancementRuleState(
    val enhancementType: EnhancementType = EnhancementType.NeutralEnhancementType,
    val enhancerLevel: Int = 1,
    val multipleTargets: Boolean = false,
    val hasLostIcon: Boolean = false,
    val hasPersistentBonus: Boolean = false,
    val abilityCardLevel: Int = 1,
    val existingEnhancements: Int = 0,
    val existingHexes: Int = 1
)
