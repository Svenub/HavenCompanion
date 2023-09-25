package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.controllers

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import se.umu.svke0008.havencompanion.ui.theme.rememberMic

@Composable
fun AnimationFab(
    primaryText: String,
    isListening: Boolean,
    showMoreControls: Boolean,
    containerColor: Color,
    contentColor: Color,
    stopListening: (Boolean) -> Unit,
    onExtendedFabActions: (ExtendedFabActions) -> Unit,
    onNewRound: () -> Unit,
) {
    var expanded by remember { mutableStateOf(false) }
    val buttonSpacing = 60.dp  // Spacing between each button when expanded

    val extendedFabs = listOf(
        ExtendedFab.ExtendedButton(
            title = ExtendedFabActions.ByName.displayName,
            onClick = { onExtendedFabActions(ExtendedFabActions.ByName) }
        ),
        ExtendedFab.ExtendedButton(
            title = ExtendedFabActions.ByOrder.displayName,
            onClick = { onExtendedFabActions(ExtendedFabActions.ByOrder) }
        )
    )


    // Animate the vertical offset for the floating buttons
    val buttonOffsets = List(extendedFabs.size) { index ->
        animateDpAsState(
            targetValue = if (expanded) buttonSpacing * (index + 1) else 0.dp
        ).value
    }

    LaunchedEffect(key1 = isListening) {
        if (isListening) {
            expanded = false
        }
    }

    LaunchedEffect(key1 = showMoreControls) {
        if (!showMoreControls) {
            expanded = false
        }
    }

    Box(contentAlignment = Alignment.BottomCenter) {
        buttonOffsets.forEachIndexed { index, offset ->
            val buttonAlpha by animateFloatAsState(
                targetValue = if (expanded) 1f else 0f
            )

            FloatingActionButton(
                containerColor = containerColor,
                contentColor = contentColor,
                onClick = extendedFabs[index].onClick,
                modifier = Modifier
                    .offset(y = if (!expanded) -offset else 0.dp)
                    .padding(bottom = if (expanded) offset else 0.dp)  // Space from the main FAB
                    .alpha(buttonAlpha)  // Set the button's visibility
            ) {
                Text(
                    text = extendedFabs[index].title,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        // Main FAB
        FloatingActionButton(
            containerColor = containerColor,
            contentColor = contentColor,
            onClick = {
                if (isListening) {
                    stopListening(false)
                } else if (showMoreControls) {
                    expanded = !expanded
                } else {
                    onNewRound()
                }
            }
        ) {

            AnimatedContent(targetState = isListening) { isCurrentlyListening ->
                if (isCurrentlyListening) {
                    Icon(
                        rememberMic(),
                        contentDescription = "Stop listening",
                        tint = Color.Red
                    )
                } else {
                    Text(
                        modifier = Modifier.padding(8.dp),
                        text = if (expanded) "Close" else primaryText,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }

    }


}

sealed interface ExtendedFab {
    val title: String
    val icon: ImageVector?
    val onClick: () -> Unit


    data class ExtendedButton(
        override val title: String,
        override val icon: ImageVector? = null,
        override val onClick: () -> Unit
    ) : ExtendedFab
}

enum class ExtendedFabActions {
    ByName, ByOrder;

    // This will give the corresponding display name for each enum value
    val displayName: String
        get() = when (this) {
            ByName -> "By name"
            ByOrder -> "By order"
        }
}
