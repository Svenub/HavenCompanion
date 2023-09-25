package se.umu.svke0008.havencompanion.domain.utils

import se.umu.svke0008.havencompanion.domain.model.enhancement.Enhancement

sealed class OrderType(val selected: Boolean) {
    abstract fun sort(list: List<Enhancement>): List<Enhancement>

    class Ascending(selected: Boolean = true): OrderType(selected) {
        override fun sort(list: List<Enhancement>): List<Enhancement> {
            return list.sortedBy { it.calculatedCost }
        }
        override fun toString(): String = "Ascending"
    }
    class Descending(selected: Boolean = false): OrderType(selected) {
        override fun sort(list: List<Enhancement>): List<Enhancement> {
            return list.sortedByDescending { it.calculatedCost }
        }
        override fun toString(): String = "Descending"
    }

    fun copy(selected: Boolean = this.selected): OrderType {
        return when(this) {
            is Ascending -> Ascending(selected)
            is Descending -> Descending(selected)
        }

    }
}
