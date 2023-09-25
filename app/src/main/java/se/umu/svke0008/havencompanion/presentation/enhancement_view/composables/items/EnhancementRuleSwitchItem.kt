package se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.items

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipState
import androidx.compose.material3.Switch
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancementRuleSwitchItem(
    modifier: Modifier = Modifier,
    text: String,
    toolTipText: String = "",
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit = {},

    ) {

    val scope = rememberCoroutineScope()
    val tooltipState = remember { RichTooltipState() }

    var showInfo by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = tooltipState.isVisible) {
        if (!tooltipState.isVisible) {
            showInfo = false
        }
    }

    RichTooltipBox(
        text = { Text(text = toolTipText, softWrap = true,modifier = Modifier.padding(8.dp)) },
        tooltipState = tooltipState,
    ) {

        Row(
            modifier = modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                maxLines = 2,
                softWrap = true,
                modifier = Modifier.weight(0.6f)
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
                    modifier = Modifier.weight(0.2f)
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
                modifier = Modifier.weight(0.2f)
            )
        }
    }
}
