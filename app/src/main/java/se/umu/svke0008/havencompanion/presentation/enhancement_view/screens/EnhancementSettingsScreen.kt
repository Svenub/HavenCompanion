package se.umu.svke0008.havencompanion.presentation.enhancement_view.screens

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.umu.svke0008.havencompanion.domain.actions.EnhancementAction
import se.umu.svke0008.havencompanion.domain.model.enhancement.EnhancementRuleState
import se.umu.svke0008.havencompanion.domain.model.enhancement.EnhancementType
import se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.items.EnhancementHexInput
import se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.items.EnhancementRuleCardLevel
import se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.items.EnhancementRuleExistingEnhancements
import se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.items.EnhancementTypeGroup
import se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.items.EnhancerLevelCard
import se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.items.LostIconRule
import se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.items.MultiTargetRule
import se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.items.PersistentBonusRule
import se.umu.svke0008.havencompanion.ui.padding
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

@Composable
private fun EnhancementSettingsScreen(
    enhancementRuleState: EnhancementRuleState,
    paddingValues: PaddingValues,
    onEnhancementTypeClick: (EnhancementType) -> Unit,
    onEnhancerLevelClick: (Int) -> Unit,
    onExistingHexes: (Int) -> Unit,
    onRule1Change: (Boolean) -> Unit,
    onRule2Change: (Boolean) -> Unit,
    onRule3Change: (Boolean) -> Unit,
    onRule4Change: (Int) -> Unit,
    onRule5Change: (Int) -> Unit,
) {

    val listState = rememberLazyListState()


    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
        verticalArrangement = Arrangement.spacedBy(space = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Spacer(modifier = Modifier.size(MaterialTheme.padding.small))
            EnhancerLevelCard(
                currentLevel = enhancementRuleState.enhancerLevel,
                onLevelClick = { onEnhancerLevelClick.invoke(it) })
        }

        item {
            EnhancementTypeGroup(
                selectedType = enhancementRuleState.enhancementType,
                onEnhancementTypeClicked = { onEnhancementTypeClick.invoke(it) })

        }

        item {
            AnimatedVisibility(
                visible = enhancementRuleState.enhancementType is EnhancementType.AreaOfEffectEnhancement,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it }) + expandVertically(
                    expandFrom = Alignment.Top
                ),
                exit = fadeOut() + slideOutVertically(targetOffsetY = { -it }) + shrinkVertically(
                    shrinkTowards = Alignment.Top
                )
            ) {
                EnhancementHexInput(
                    existingHexes = enhancementRuleState.existingHexes,
                    onValueChange = { onExistingHexes.invoke(it) })

            }
            Divider()
        }

        item {
            MultiTargetRule(
                checked = enhancementRuleState.multipleTargets,
                onCheckedChange = { onRule1Change.invoke(it) }
            )
        }

        item {
            LostIconRule(
                modifier = if (enhancementRuleState.hasPersistentBonus) Modifier.alpha(0.3f) else Modifier,
                checked = enhancementRuleState.hasLostIcon && !enhancementRuleState.hasPersistentBonus,
                onCheckedChange = { onRule2Change.invoke(it) })
        }

        item {

            PersistentBonusRule(
                checked = enhancementRuleState.hasPersistentBonus,
                onCheckedChange = { onRule3Change.invoke(it) })
        }
        item {
            EnhancementRuleCardLevel(
                modifier = Modifier.fillMaxWidth(),
                value = enhancementRuleState.abilityCardLevel,
                onValueChange = { onRule4Change.invoke(it) })

        }
        item {
            EnhancementRuleExistingEnhancements(
                modifier = Modifier.fillMaxWidth(),
                value = enhancementRuleState.existingEnhancements,
                onValueChange = { onRule5Change.invoke(it) })
        }
    }


}

@Composable
fun EnhancementSettingsScreen(
    enhancementRuleState: EnhancementRuleState,
    onEnhancementStateAction: (EnhancementAction) -> Unit,
    paddingValues: PaddingValues,
) {
    EnhancementSettingsScreen(
        enhancementRuleState = enhancementRuleState,
        paddingValues = paddingValues,
        onEnhancementTypeClick = {
            onEnhancementStateAction(
                EnhancementAction.ApplyRuleState(
                    enhancementRuleState.copy(enhancementType = it)
                )
            )
        },
        onExistingHexes = {
            onEnhancementStateAction(
                EnhancementAction.ApplyRuleState(
                    enhancementRuleState.copy(existingHexes = it)
                )
            )
        },
        onEnhancerLevelClick = {
            onEnhancementStateAction(
                EnhancementAction.ApplyRuleState(
                    enhancementRuleState.copy(enhancerLevel = it)
                )
            )
        },
        onRule1Change = {
            onEnhancementStateAction(
                EnhancementAction.ApplyRuleState(
                    enhancementRuleState.copy(multipleTargets = it)
                )
            )
        },
        onRule2Change = {
            onEnhancementStateAction(
                EnhancementAction.ApplyRuleState(
                    enhancementRuleState.copy(hasLostIcon = it)
                )
            )
        },
        onRule3Change = {
            onEnhancementStateAction(
                EnhancementAction.ApplyRuleState(
                    enhancementRuleState.copy(hasPersistentBonus = it)
                )
            )
        },
        onRule4Change = {
            onEnhancementStateAction(
                EnhancementAction.ApplyRuleState(
                    enhancementRuleState.copy(abilityCardLevel = it)
                )
            )
        },
        onRule5Change = {
            onEnhancementStateAction(
                EnhancementAction.ApplyRuleState(
                    enhancementRuleState.copy(existingEnhancements = it)
                )
            )
        }
    )
}


@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)

@Composable
fun EnhancementSettingsScreenPrev() {
    HavenCompanionTheme() {
        EnhancementSettingsScreen(
            enhancementRuleState = EnhancementRuleState(),
            paddingValues = PaddingValues(),
            onEnhancementTypeClick = {},
            onEnhancerLevelClick = {},
            onExistingHexes = {},
            onRule1Change = {},
            onRule2Change = {},
            onRule3Change = {},
            onRule4Change = {},
            onRule5Change = {}
        )
    }
}
