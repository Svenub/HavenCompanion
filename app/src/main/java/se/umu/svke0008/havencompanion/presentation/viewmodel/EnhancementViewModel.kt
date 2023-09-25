package se.umu.svke0008.havencompanion.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import se.umu.svke0008.havencompanion.data.local.db.DatabaseResult
import se.umu.svke0008.havencompanion.domain.actions.EnhancementAction
import se.umu.svke0008.havencompanion.domain.model.enhancement.Enhancement
import se.umu.svke0008.havencompanion.domain.model.enhancement.EnhancementRuleState
import se.umu.svke0008.havencompanion.domain.model.enhancement.toDomain
import se.umu.svke0008.havencompanion.domain.model.enhancement.canHold
import se.umu.svke0008.havencompanion.domain.repository.EnhancementRepository
import se.umu.svke0008.havencompanion.domain.service.EnhancementService
import se.umu.svke0008.havencompanion.domain.strategies.FilterStrategy
import se.umu.svke0008.havencompanion.domain.strategies.SortStrategy
import se.umu.svke0008.havencompanion.presentation.enhancement_view.EnhancementSortFilterState
import javax.inject.Inject

@HiltViewModel
class EnhancementViewModel @Inject constructor(
    private val enhancementService: EnhancementService,
    enhancementRepository: EnhancementRepository
) : ViewModel() {


    private val _enhancementRuleState = MutableStateFlow(EnhancementRuleState())
    val enhancementRuleState = _enhancementRuleState.asStateFlow()

    private val _enhancementSortFilterState =
        MutableStateFlow(EnhancementSortFilterState())
    val enhancementSortFilterState = _enhancementSortFilterState.asStateFlow()

    val getCalculatedEnhancements: StateFlow<List<Enhancement>> =
        combine(
            enhancementRuleState,
            enhancementSortFilterState,
            enhancementRepository.getAllEnchantments()
        ) { rules, enhancementSortFilterState, result ->
            when (result) {
                is DatabaseResult.Success -> {
                    var enhancements = result.data.map { entity ->
                        entity.toDomain(
                            calculatedCost = enhancementService.calculateEnhancementCost(
                                entity,
                                rules
                            )
                        )
                    }.filter { rules.enhancementType.canHold(it.enhancementType) }

                    // Apply filters
                    enhancementSortFilterState.filters.filter { it.selected }
                        .forEach { filterStrategy ->
                            enhancements = filterStrategy.filter(enhancements)
                        }

                    enhancementSortFilterState.currentGold?.let { gold ->
                        enhancements = enhancements.filter { it.calculatedCost <= gold }
                    }

                    // Apply sorters
                    val selectedSorter =
                        enhancementSortFilterState.sorters.firstOrNull { it.selected }

                    selectedSorter?.let {
                        enhancements = it.sort(enhancements)
                    }

                    enhancements
                }

                else -> emptyList()
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun onEvent(action: EnhancementAction) {
        when (action) {
            is EnhancementAction.ApplyRuleState -> _enhancementRuleState.update { action.enhancementRuleState }
            is EnhancementAction.ApplyFilter -> {
                _enhancementSortFilterState.update { state ->
                    state.copy(filters = state.filters.map { filter ->
                        if (action.filter::class == filter::class) {
                            filter.copy(selected = !filter.selected)
                        } else {
                            filter
                        }
                    })
                }
            }

            is EnhancementAction.ApplySorter -> _enhancementSortFilterState.update { state ->
                state.copy(sorters = state.sorters.map { sorter ->
                    if (action.sorter::class == sorter::class) {
                        sorter.copy(selected = true)
                    } else {
                        sorter.copy(selected = false)
                    }
                })
            }

            is EnhancementAction.ApplyCurrentGold -> _enhancementSortFilterState.update { state ->
                state.copy(currentGold = action.gold)
            }
        }
    }


    private inline fun <reified T : FilterStrategy> transformFilterList(
        filterList: List<T>,
        action: T
    ): List<T> {
        return filterList.map { filter ->
            if (filter::class == action::class) {
                action
            } else {
                filter
            }
        }
    }

    private inline fun <reified T : SortStrategy> transformSortList(
        sortList: List<SortStrategy>,
        action: T
    ): List<SortStrategy> {
        return sortList.map { sorter ->
            when (sorter) {
                is SortStrategy.AscendingSort -> if (sorter is T) action else SortStrategy.AscendingSort()
                is SortStrategy.DescendingSort -> if (sorter is T) action else SortStrategy.DescendingSort()
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        Log.d("onCleared", "EnhancerViewmodel cleared");

    }
}