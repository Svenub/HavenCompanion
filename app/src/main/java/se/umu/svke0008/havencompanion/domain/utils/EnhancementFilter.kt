package se.umu.svke0008.havencompanion.domain.utils

import se.umu.svke0008.havencompanion.domain.model.enhancement.Enhancement
import se.umu.svke0008.havencompanion.domain.model.enhancement.EnhancementType

sealed class EnhancementFilter(val selected: Boolean = false) {

    abstract fun filter(list: List<Enhancement>): List<Enhancement>

    class FilterNeutral(selected: Boolean = false) : EnhancementFilter(selected) {
        override fun filter(list: List<Enhancement>): List<Enhancement> {
            return list.filter { it.enhancementType is EnhancementType.NeutralEnhancementType }
        }

        override fun toString(): String = "Neutral"
    }

    class FilterElement(selected: Boolean = false) : EnhancementFilter(selected) {
        override fun filter(list: List<Enhancement>): List<Enhancement> {
            return list.filter { it.enhancementType is EnhancementType.ElementEnhancementType }
        }
        override fun toString(): String = "Element"
    }

    class FilterPositive(selected: Boolean = false) : EnhancementFilter(selected) {
        override fun filter(list: List<Enhancement>): List<Enhancement> {
            return list.filter { it.enhancementType is EnhancementType.PositiveEnhancementType }
        }
        override fun toString(): String = "Positive"
    }

    class FilterNegative(selected: Boolean = false) : EnhancementFilter(selected) {
        override fun filter(list: List<Enhancement>): List<Enhancement> {
            return list.filter { it.enhancementType is EnhancementType.NegativeEnhancementType }
        }
        override fun toString(): String = "Negative"
    }



    fun copy(selected: Boolean): EnhancementFilter {
        return when (this) {
            is FilterElement -> FilterElement(selected)
            is FilterNegative -> FilterNegative(selected)
            is FilterNeutral -> FilterNeutral(selected)
            is FilterPositive -> FilterPositive(selected)
        }
    }
}
