package se.umu.svke0008.havencompanion.presentation.enhancement_view.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import se.umu.svke0008.havencompanion.domain.model.enhancement.Enhancement
import se.umu.svke0008.havencompanion.domain.utils.EnhancementFilter
import se.umu.svke0008.havencompanion.domain.utils.OrderType
import se.umu.svke0008.havencompanion.presentation.enhancement_view.EnhancementSortFilterState
import se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.list.EnhancementList
import se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.list.FilterHead

@Composable
fun EnhancementListScreen(
    enhancementList: List<Enhancement>,
    filterSortState: EnhancementSortFilterState,
    paddingValues: PaddingValues,
    onCurrentGold: (Int?) -> Unit,
    onFilterSelect: (EnhancementFilter) -> Unit,
    onSortSelect: (OrderType) -> Unit
) {

    val listState = rememberLazyListState()

    val filterHeadVisible by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex == 0
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        AnimatedVisibility(visible = filterHeadVisible) {
            FilterHead(
                filters = filterSortState.filters,
                sorters = filterSortState.sorters,
                gold = filterSortState.currentGold,
                onFilterSelect = { onFilterSelect(it) },
                onSortSelect = { onSortSelect(it) },
                onCurrentGold = { onCurrentGold(it) }

            )
        }
        EnhancementList(
            enhancements = enhancementList,
            listState = listState,
            paddingValues = paddingValues
        )
    }


}

@Composable
private fun LazyListState.isScrollingUp(): Boolean {
    var previousIndex by remember(this) { mutableIntStateOf(firstVisibleItemIndex) }
    var previousScrollOffset by remember(this) { mutableIntStateOf(firstVisibleItemScrollOffset) }
    return remember(this) {
        derivedStateOf {
            if (previousIndex != firstVisibleItemIndex) {
                previousIndex > firstVisibleItemIndex
            } else {
                previousScrollOffset >= firstVisibleItemScrollOffset
            }.also {
                previousIndex = firstVisibleItemIndex
                previousScrollOffset = firstVisibleItemScrollOffset
            }
        }
    }.value
}