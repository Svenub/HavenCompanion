package se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.items

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import se.umu.svke0008.havencompanion.presentation.enhancement_view.EnhancementConstants.AREA_OF_EFFECT_TOOLTIP
import se.umu.svke0008.havencompanion.presentation.util_components.ToolTipItem
import se.umu.svke0008.havencompanion.ui.padding
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EnhancementHexInput(
    modifier: Modifier = Modifier,
    text: String = "Existing hexes",
    existingHexes: Int,
    toolTipText: String = AREA_OF_EFFECT_TOOLTIP,
    onValueChange: (Int) -> Unit
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var currentHexes by remember { mutableStateOf(existingHexes.toString()) }

    if (!WindowInsets.isImeVisible) {
        focusManager.clearFocus()
        if (currentHexes.isBlank()) {
            currentHexes = existingHexes.toString()
        }
    }

    ToolTipItem(toolTipText = toolTipText) { trigger ->

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                start = MaterialTheme.padding.medium,
                end = MaterialTheme.padding.medium
            )
        ) {


            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground
            )

            IconButton(
                onClick = {
                    trigger.invoke()
                }
            ) {

                Icon(
                    Icons.Outlined.Info,
                    contentDescription = "Information",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }

            TextField(
                modifier = Modifier
                    .focusRequester(focusRequester),

                textStyle = MaterialTheme.typography.bodyLarge,
                value = currentHexes,
                onValueChange = {
                    val filteredInput = it.filter { char -> char.isDigit() }
                    if (filteredInput.length <= 5) {
                        currentHexes = filteredInput
                        if (currentHexes.isNotBlank()) {
                            onValueChange(currentHexes.toInt())
                        } else {
                            onValueChange(1)
                        }
                    }
                },
                singleLine = true,

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
            )


        }

    }
}

@Preview
@Composable
fun EnhancementHexInputPrev() {
    HavenCompanionTheme() {
        EnhancementHexInput(text = "Existing hexes", existingHexes = 1, onValueChange = {})
    }
}
