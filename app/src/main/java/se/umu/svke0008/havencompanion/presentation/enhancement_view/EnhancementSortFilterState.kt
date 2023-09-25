package se.umu.svke0008.havencompanion.presentation.enhancement_view

import se.umu.svke0008.havencompanion.domain.utils.EnhancementFilter
import se.umu.svke0008.havencompanion.domain.utils.OrderType

data class EnhancementSortFilterState(
    val filters: List<EnhancementFilter> = listOf(
        EnhancementFilter.FilterNeutral(),
        EnhancementFilter.FilterElement(),
        EnhancementFilter.FilterPositive(),
        EnhancementFilter.FilterNegative(),
    ),
    val sorters: List<OrderType> = listOf(
        OrderType.Ascending(selected = true),
        OrderType.Descending(selected = false)
    ),
    val currentGold: Int? = null
) {
    override fun toString(): String {
        return "EnhancementSortFilterState(filters=$filters, sorters=$sorters, currentGold=$currentGold)"
    }
}
