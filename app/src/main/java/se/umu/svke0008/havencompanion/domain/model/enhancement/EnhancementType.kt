package se.umu.svke0008.havencompanion.domain.model.enhancement


sealed class EnhancementType {
    object NeutralEnhancementType : EnhancementType() {
        override fun toString() = "Neutral"
    }

    object ElementEnhancementType : EnhancementType() {
        override fun toString() = "Element"
    }

    object PositiveEnhancementType : EnhancementType() {
        override fun toString() = "Positive"
    }

    object NegativeEnhancementType : EnhancementType() {
        override fun toString() = "Negative"
    }

    object AreaOfEffectEnhancement : EnhancementType() {
        override fun toString() = "AreaOfEffect"
    }
}


fun EnhancementType.canHold(type: EnhancementType): Boolean {
    return when (this) {
        is EnhancementType.NeutralEnhancementType -> type is EnhancementType.NeutralEnhancementType
        is EnhancementType.ElementEnhancementType -> type is EnhancementType.NeutralEnhancementType || type is EnhancementType.ElementEnhancementType
        is EnhancementType.PositiveEnhancementType -> type in listOf(
            EnhancementType.NeutralEnhancementType,
            EnhancementType.ElementEnhancementType,
            EnhancementType.PositiveEnhancementType
        )

        is EnhancementType.NegativeEnhancementType -> type in listOf(
            EnhancementType.NeutralEnhancementType,
            EnhancementType.ElementEnhancementType,
            EnhancementType.NegativeEnhancementType
        )

        is EnhancementType.AreaOfEffectEnhancement -> type is EnhancementType.AreaOfEffectEnhancement
    }
}

