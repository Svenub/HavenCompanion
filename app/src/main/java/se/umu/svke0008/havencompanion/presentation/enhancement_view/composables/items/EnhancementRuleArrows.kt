package se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import se.umu.svke0008.havencompanion.presentation.enhancement_view.EnhancementConstants
import se.umu.svke0008.havencompanion.ui.padding
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme
import se.umu.svke0008.havencompanion.ui.theme.PirataOne

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EnhancementRuleArrows(
    modifier: Modifier = Modifier,
    text: String,
    toolTipText: String,
    value: Int = 1,
    minValue: Int,
    maxValue: Int,
    onValueChange: (Int) -> Unit
) {

    var currentValue by remember { mutableIntStateOf(value) }

    val scope = rememberCoroutineScope()
    val tooltipState = remember { RichTooltipState() }

    var showInfo by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = tooltipState.isVisible) {
        if (!tooltipState.isVisible) {
            showInfo = false
        }
    }

    RichTooltipBox(
        text = {
            Text(
                text = toolTipText,
                softWrap = true,
                modifier = Modifier.padding(MaterialTheme.padding.small)
            )
        },
        tooltipState = tooltipState,
    ) {


        Card(
            modifier = modifier,
            shape = MaterialTheme.shapes.medium,
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(MaterialTheme.padding.small)
            ) {

                Row(
                    modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = text,
                        maxLines = 2,
                        softWrap = true,
                        style = MaterialTheme.typography.titleMedium
                    )

                    IconButton(
                        onClick = {
                            showInfo = !showInfo
                            scope.launch {
                                while (showInfo) {
                                    tooltipState.show()
                                }
                            }
                        }
                    ) {
                        Icon(
                            Icons.Outlined.Info,
                            contentDescription = "Information",
                        )
                    }

                }
                Row(modifier = modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = {
                        if (currentValue > minValue) {
                            currentValue--
                            onValueChange(currentValue)
                        }
                    }) {
                        Icon(Icons.Default.KeyboardArrowLeft, contentDescription = null)
                    }

                    Text(
                        text = currentValue.toString(),
                        fontFamily = PirataOne,
                        color = MaterialTheme.colorScheme.primary
                    )

                    IconButton(onClick = {
                        if (currentValue < maxValue) {
                            currentValue++
                            onValueChange(currentValue)
                        }
                    }) {
                        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null)
                    }
                }
            }
        }
    }
}


@Composable
fun EnhancementRuleCardLevel(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    EnhancementRuleArrows(
        modifier = modifier,
        value = value,
        minValue = 1,
        maxValue = 9,
        text = "Ability card level",
        toolTipText = EnhancementConstants.ENHANCEMENT_RULE_4,
        onValueChange = onValueChange
    )
}

@Composable
fun EnhancementRuleExistingEnhancements(
    modifier: Modifier = Modifier,
    value: Int,
    onValueChange: (Int) -> Unit
) {
    EnhancementRuleArrows(
        modifier = modifier,
        value = value,
        minValue = 0,
        maxValue = 6,
        text = "Number of existing enhancements",
        toolTipText = EnhancementConstants.ENHANCEMENT_RULE_5,
        onValueChange = onValueChange
    )
}

@Preview(showBackground = true)
@Composable
fun EnhancementRuleArrowsPrev() {
    HavenCompanionTheme(darkTheme = true) {
        var value by remember { mutableStateOf(0) }

        EnhancementRuleArrows(
            value = value,
            minValue = 1,
            maxValue = 9,
            text = "Ability card level",
            toolTipText = EnhancementConstants.ENHANCEMENT_RULE_4,
            onValueChange = { value = it })
    }
}