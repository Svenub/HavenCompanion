package se.umu.svke0008.havencompanion.domain.actions

import se.umu.svke0008.havencompanion.domain.model.enhancement.EnhancementRuleState
import se.umu.svke0008.havencompanion.domain.utils.EnhancementFilter
import se.umu.svke0008.havencompanion.domain.utils.OrderType

sealed class EnhancementAction {
    data class ApplyRuleState(val enhancementRuleState: EnhancementRuleState): EnhancementAction()
    data class ApplyFilter(val filter: EnhancementFilter) : EnhancementAction()
    data class ApplySorter(val sorter: OrderType) : EnhancementAction()
    data class ApplyCurrentGold(val gold: Int?) : EnhancementAction()
}
