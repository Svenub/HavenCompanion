package se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import se.umu.svke0008.havencompanion.presentation.enhancement_view.EnhancementConstants.ENHANCER_LEVEL_1
import se.umu.svke0008.havencompanion.presentation.enhancement_view.EnhancementConstants.ENHANCER_LEVEL_2
import se.umu.svke0008.havencompanion.presentation.enhancement_view.EnhancementConstants.ENHANCER_LEVEL_3
import se.umu.svke0008.havencompanion.presentation.enhancement_view.EnhancementConstants.ENHANCER_LEVEL_4
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancerLevelItem(
    level: Int,
    modifier: Modifier = Modifier,
    onLevelClick: (Int) -> Unit = {},
) {

    val scope = rememberCoroutineScope()
    val tooltipState = remember { RichTooltipState() }
    var showInfo by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }


    val enhancerLevels = listOf(1,2,3,4)
    var selectedLevel by remember { mutableStateOf(enhancerLevels[0]) }

    val toolTipText =
        """Level 1: 
            |$ENHANCER_LEVEL_1
            |Level 2: 
            |$ENHANCER_LEVEL_2
            |
            |Level 3: 
            |$ENHANCER_LEVEL_3
            |
            |Level 4: 
            |$ENHANCER_LEVEL_4
            |
        """.trimMargin()

    LaunchedEffect(key1 = tooltipState.isVisible) {
        if (!tooltipState.isVisible) {
            showInfo = false
        }
    }

    RichTooltipBox(
        text = { Text(text = toolTipText, softWrap = true, modifier = Modifier.padding(8.dp)) },
        tooltipState = tooltipState,
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Enhancer building level",
                maxLines = 2,
                softWrap = true,
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
                    contentDescription = null,
                )
            }

            Box() {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Level: $selectedLevel",
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
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

@Preview(showSystemUi = true)
@Composable
fun EnhancerLevelItemPrev() {
    HavenCompanionTheme() {
        EnhancerLevelItem(1)
    }
}