package se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RichTooltipBox
import androidx.compose.material3.RichTooltipState
import androidx.compose.material3.Slider
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
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EnhancementRuleSliderItem(
    modifier: Modifier = Modifier,
    text: String,
    toolTipText: String = "",
    sliderValue: Float = 0f,
    valueRange: ClosedFloatingPointRange<Float> = 1f..9f,
    steps: Int = 7,
    onValueChange: (Float) -> Unit
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
        modifier = modifier,
        text = { Text(text = toolTipText, softWrap = true, modifier = Modifier.padding(8.dp)) },
        tooltipState = tooltipState,
    ) {

        Column(
            modifier = modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "$text ")

                Text(text = sliderValue.toInt().toString(), color = MaterialTheme.colorScheme.primary)

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
            Row(modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
                Slider(
                    value = sliderValue.roundToInt().toFloat(),
                    onValueChange = {
                        onValueChange.invoke(it)
                    },
                    valueRange = valueRange,
                    steps = steps
                )

            }
        }
    }
}
