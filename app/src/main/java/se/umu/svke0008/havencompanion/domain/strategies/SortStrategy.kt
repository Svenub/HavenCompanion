package se.umu.svke0008.havencompanion.domain.strategies

import se.umu.svke0008.havencompanion.domain.model.enhancement.Enhancement

sealed class SortStrategy(open val selected: Boolean = false) {
    abstract fun sort(list: List<Enhancement>): List<Enhancement>

    data class AscendingSort(override val selected: Boolean = false) : SortStrategy(selected) {
        override fun sort(list: List<Enhancement>): List<Enhancement> {
            return list.sortedBy { it.calculatedCost }
        }
        override fun toString(): String = "Ascending"
    }

    data class DescendingSort(override val selected: Boolean = false) : SortStrategy(selected) {
        override fun sort(list: List<Enhancement>): List<Enhancement> {
            return list.sortedByDescending { it.calculatedCost }
        }
        override fun toString(): String = "Descending"
    }

    companion object {
        val sorters = listOf(
            AscendingSort(),
            DescendingSort()
        )
    }
}

