package se.umu.svke0008.havencompanion.presentation.util_components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import se.umu.svke0008.havencompanion.ui.padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToolTipItem(
    modifier: Modifier = Modifier,
    toolTipText: String,
    content: @Composable (tooltipInvoker: () -> Unit) -> Unit
) {
    val scope = rememberCoroutineScope()
    val tooltipState = remember { RichTooltipState() }

    var showToolTip by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = tooltipState.isVisible) {
        if (!tooltipState.isVisible) {
            showToolTip = false
        }
    }

    val tooltipInvoker = {
        showToolTip = !showToolTip
        scope.launch {
            while (showToolTip) {
                tooltipState.show()
            }
        }
    }

    RichTooltipBox(
        text = {
            Text(
                text = toolTipText,
                softWrap = true,
                modifier = modifier.padding(MaterialTheme.padding.small)
            )
        },
        tooltipState = tooltipState,
    ) {
        content(tooltipInvoker)
    }
}

