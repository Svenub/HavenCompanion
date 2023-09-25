package se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.items

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import se.umu.svke0008.havencompanion.presentation.enhancement_view.EnhancementConstants
import se.umu.svke0008.havencompanion.presentation.util_components.ToolTipItem
import se.umu.svke0008.havencompanion.ui.padding
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme
import se.umu.svke0008.havencompanion.ui.theme.PirataOne

@Composable
fun EnhancementRuleCardItem(
    modifier: Modifier = Modifier,
    toolTipText: String,
    containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    content: @Composable (trigger: () -> Unit) -> Unit
) {
    ToolTipItem(
        toolTipText = toolTipText
    ) { trigger ->

        Card(
            modifier = modifier,
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = containerColor,
                contentColor = contentColor
            )
        ) {
            content(trigger)
        }
    }
}

@Composable
fun EnhancementCardItemSwitch(
    modifier: Modifier = Modifier,
    mainText: String,
    toolTipText: String,
    checked: Boolean,
    containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    onCheckedChange: (Boolean) -> Unit
) {
    EnhancementRuleCardItem(
        containerColor = containerColor,
        contentColor = contentColor,
        toolTipText = toolTipText
    ) { trigger ->
        Row(
            modifier = modifier.padding(MaterialTheme.padding.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = mainText,
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.weight(0.6f)
            )
            IconButton(
                onClick = {
                    trigger.invoke()
                }
            ) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = "Information",
                    modifier = modifier.weight(0.2f)
                )
            }
            Switch(
                checked = checked, onCheckedChange = { onCheckedChange.invoke(it) },
                thumbContent = {
                    if (checked) Icon(
                        Icons.Default.Check,
                        contentDescription = "Checked"
                    )
                },
                modifier = modifier.weight(0.2f)
            )
        }
    }
}

@Composable
fun EnhancerLevelCard(
    modifier: Modifier = Modifier,
    currentLevel: Int,
    onLevelClick: (Int) -> Unit
) {
    val toolTipText =
        """Level 1: 
            |${EnhancementConstants.ENHANCER_LEVEL_1}
            |Level 2: 
            |${EnhancementConstants.ENHANCER_LEVEL_2}
            |
            |Level 3: 
            |${EnhancementConstants.ENHANCER_LEVEL_3}
            |
            |Level 4: 
            |${EnhancementConstants.ENHANCER_LEVEL_4}
            |
        """.trimMargin()


    var expanded by remember { mutableStateOf(false) }
    val enhancerLevels = listOf(1, 2, 3, 4)
    var selectedLevel by remember { mutableIntStateOf(currentLevel) }

    EnhancementRuleCardItem(modifier = modifier, toolTipText = toolTipText) { trigger ->
        Row(
            modifier = modifier.padding(MaterialTheme.padding.small),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Enhancer building level",
                style = MaterialTheme.typography.titleMedium,
                modifier = modifier.weight(0.6f)
            )
            IconButton(
                onClick = {
                    trigger.invoke()
                }
            ) {
                Icon(
                    Icons.Outlined.Info,
                    contentDescription = "Information",
                    modifier = modifier.weight(0.2f)
                )
            }
            Box {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Level: $selectedLevel",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.bodyLarge,
                        fontFamily = PirataOne
                    )
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                    }
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    enhancerLevels.forEachIndexed { index, level ->
                        DropdownMenuItem(
                            text = { Text(text = "Level $level") },
                            onClick = {
                                onLevelClick(level)
                                selectedLevel = enhancerLevels[index]
                                expanded = false
                            })
                    }
                }

            }

        }
    }
}

@Composable
fun MultiTargetRule(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    EnhancementCardItemSwitch(
        mainText = "Ability targets multiple figures or tiles?",
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        toolTipText = EnhancementConstants.ENHANCEMENT_RULE_1,
        checked = checked,
        onCheckedChange = {
            onCheckedChange(it)
        })
}


@Composable
fun LostIconRule(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    EnhancementCardItemSwitch(
        modifier = modifier,
        mainText = "Action has a lost icon?",
        toolTipText = EnhancementConstants.ENHANCEMENT_RULE_2,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        checked = checked,
        onCheckedChange = {
            onCheckedChange(it)
        })
}


@Composable
fun PersistentBonusRule(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    EnhancementCardItemSwitch(
        mainText = "Ability provides a persistent bonus?",
        toolTipText = EnhancementConstants.ENHANCEMENT_RULE_3,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        checked = checked,
        onCheckedChange = {
            onCheckedChange(it)
        })
}


@Preview
@Composable
fun PersistentBonusRulePrev() {
    HavenCompanionTheme() {
        PersistentBonusRule(checked = false, onCheckedChange = {})
    }
}