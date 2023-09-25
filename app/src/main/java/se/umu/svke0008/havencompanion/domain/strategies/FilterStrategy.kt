package se.umu.svke0008.havencompanion.domain.strategies

import se.umu.svke0008.havencompanion.domain.model.enhancement.Enhancement
import se.umu.svke0008.havencompanion.domain.model.enhancement.EnhancementType

sealed class FilterStrategy(var selected: Boolean = false, open var amount: Int = 0) {

    abstract fun filter(list: List<Enhancement>, amount: Int = this.amount): List<Enhancement>

    data class FilterNeutral(override var amount: Int = 0) : FilterStrategy() {

        override fun filter(list: List<Enhancement>, amount: Int): List<Enhancement> {
            return list.filter { it.enhancementType is EnhancementType.NeutralEnhancementType }
        }

        override fun toString(): String = "Neutral $selected"
    }
    data class FilterElement(override var amount: Int = 0) : FilterStrategy() {
        override fun filter(list: List<Enhancement>, amount: Int): List<Enhancement> {
            return list.filter { it.enhancementType is EnhancementType.ElementEnhancementType }
        }
        override fun toString(): String = "Element $selected"
    }

    data class FilterPositive(override var amount: Int = 0) : FilterStrategy() {
        override fun filter(list: List<Enhancement>, amount: Int): List<Enhancement> {
            return list.filter { it.enhancementType is EnhancementType.PositiveEnhancementType }
        }
        override fun toString(): String = "Positive $selected"
    }

    data class FilterNegative(override var amount: Int = 0) : FilterStrategy() {
        override fun filter(list: List<Enhancement>, amount: Int): List<Enhancement> {
            return list.filter { it.enhancementType is EnhancementType.NegativeEnhancementType }
        }
        override fun toString(): String = "Negative $selected"
    }

    data class FilterAffordable(override var amount: Int = 0) :
        FilterStrategy() {
        override fun filter(list: List<Enhancement>, amount: Int): List<Enhancement> {
            return list.filter { amount >= it.calculatedCost }
        }
        override fun toString(): String = "Affordable $selected"
    }

    companion object {
        fun filters(selected: Boolean = false): List<FilterStrategy> {
            return listOf(
                FilterAffordable(),
                FilterNeutral(),
                FilterElement(),
                FilterPositive(),
                FilterNegative()
            )
        }

    }
}





