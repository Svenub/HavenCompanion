package se.umu.svke0008.havencompanion.presentation.enhancement_view.screens

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import se.umu.svke0008.havencompanion.domain.actions.EnhancementAction
import se.umu.svke0008.havencompanion.domain.model.enhancement.Enhancement
import se.umu.svke0008.havencompanion.domain.model.enhancement.EnhancementRuleState
import se.umu.svke0008.havencompanion.presentation.Screen
import se.umu.svke0008.havencompanion.presentation.enhancement_view.EnhancementSortFilterState
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun EnhancementScreen(
    enhancementRuleState: EnhancementRuleState,
    enhancementList: List<Enhancement>,
    filterSortState: EnhancementSortFilterState,
    onCurrentGold: (Int?) -> Unit,
    onEnhancementStateAction: (EnhancementAction) -> Unit,
    onNavigationClick: () -> Unit

) {


    val titles = listOf("Settings", "Enhancements")
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { titles.size }
    )
    val tabState = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    var selectedContent by remember { mutableIntStateOf(0) }



    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = Screen.EnhancementScreen.title,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onNavigationClick.invoke() }) {
                        Icon(Icons.Default.Menu, null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )

        },
        containerColor = MaterialTheme.colorScheme.secondary
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            TabRow(selectedTabIndex = tabState) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = tabState == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                                selectedContent = index
                            }

                        },
                        text = { Text(text = title, style = MaterialTheme.typography.titleMedium) })
                }
            }

            HorizontalPager(
                state = pagerState,
            ) { page ->
                when (page) {
                    0 -> {
                        EnhancementSettingsScreen(
                            enhancementRuleState = enhancementRuleState,
                            onEnhancementStateAction = { onEnhancementStateAction(it) },
                            paddingValues = paddingValues
                        )
                    }

                    1 -> {
                        EnhancementListScreen(
                            enhancementList = enhancementList,
                            filterSortState = filterSortState,
                            paddingValues = paddingValues,
                            onFilterSelect = {
                                onEnhancementStateAction(
                                    EnhancementAction.ApplyFilter(
                                        it
                                    )
                                )
                            },
                            onSortSelect = {
                                onEnhancementStateAction(
                                    EnhancementAction.ApplySorter(
                                        it
                                    )
                                )
                            },
                            onCurrentGold = { onCurrentGold(it) }
                        )
                    }
                }
            }
        }


    }

}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)

@Composable
fun EnhanceMentScreenPrev() {
    HavenCompanionTheme() {
        EnhancementScreen(
            enhancementRuleState = EnhancementRuleState(),
            enhancementList = emptyList(),
            filterSortState = EnhancementSortFilterState(),
            onCurrentGold = {},
            onEnhancementStateAction = {}
        ) {

        }
    }
}
