package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.controllers

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme
import se.umu.svke0008.havencompanion.ui.theme.rememberMic
import se.umu.svke0008.havencompanion.ui.theme.rememberPersonAdd


/**
 * A composable function that represents the scenario controller UI.
 *
 * This composable provides a bottom app bar with various actions and a floating action button.
 * The actions include displaying recognized voice commands, initiating new rounds, and other scenario-related actions.
 *
 * @param onAddCharacter A lambda that is invoked when a character is added.
 * @param onEdit A lambda that is invoked when editing is triggered.
 * @param voiceActive A boolean indicating if voice activation is currently active.
 * @param onToggleVoiceActivation A lambda that toggles voice activation. Takes a boolean parameter indicating the desired state.
 * @param onSettings A lambda that is invoked to open settings.
 * @param onNewRound A lambda that is invoked to start a new round.
 * @param voiceActiveNewRoundByName A lambda that is invoked to activate a new round by name using voice commands.
 * @param voiceActiveNewRoundByOrder A lambda that is invoked to activate a new round by order using voice commands.
 * @param stopListening A lambda that stops the listening process.
 * @param partialResult A string representing the partial result from the voice recognition.
 * @param isListening A boolean indicating if the application is currently listening for voice commands.
 */
@Composable
fun ScenarioController(
    onAddCharacter: () -> Unit,
    onEdit: () -> Unit,
    voiceActive: Boolean,
    onToggleVoiceActivation: (Boolean) -> Unit,
    onSettings: () -> Unit,
    onNewRound: () -> Unit,
    voiceActiveNewRoundByName: () -> Unit,
    voiceActiveNewRoundByOrder: () -> Unit,
    stopListening: () -> Unit,
    partialResult: String,
    isListening: Boolean
) {

    var showVoiceCommands by remember { mutableStateOf(false) }

    BottomAppBar(
        actions = {

            Box {
                androidx.compose.animation.AnimatedVisibility(
                    visible = isListening,
                    exit = slideOutVertically(targetOffsetY = { it + 30 }),
                ) {
                    Column(modifier = Modifier.fillMaxWidth(0.8f)) {
                        Text(
                            text = partialResult,
                            overflow = TextOverflow.Ellipsis
                        )
                    }

                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = showVoiceCommands && !isListening,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it + 30 }),
                ) {
                    VoiceActiveNewRoundContent(
                        byName = voiceActiveNewRoundByName,
                        byOrder = voiceActiveNewRoundByOrder
                    )
                }

                androidx.compose.animation.AnimatedVisibility(
                    visible = !showVoiceCommands,
                    enter = fadeIn(),
                    exit = fadeOut(),
                ) {
                    InitialControllerContent(
                        onAddCharacter = onAddCharacter,
                        onEdit = onEdit,
                        toggleMicrophone = { onToggleVoiceActivation(it) },
                        microphoneOn = voiceActive,
                        settings = onSettings
                    )
                }


            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (isListening) {
                        stopListening()
                    } else if (voiceActive) {
                        showVoiceCommands = !showVoiceCommands
                    } else {
                        onNewRound()
                    }
                },
                containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(),
            ) {
                if (isListening) {
                    Icon(rememberMic(), contentDescription = "Stop listening", tint = Color.Red)
                } else {
                    Text(
                        text = if (!showVoiceCommands) "New round" else "Back",
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

        }
    )
}

@Composable
fun InitialControllerContent(
    onAddCharacter: () -> Unit,
    onEdit: () -> Unit,
    microphoneOn: Boolean,
    toggleMicrophone: (Boolean) -> Unit,
    settings: () -> Unit
) {
    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        IconButton(onClick = onAddCharacter) {
            Icon(
                rememberPersonAdd(),
                contentDescription = "Add character",
                modifier = Modifier.padding(start = 2.dp)
            )
        }

        IconToggleButton(checked = microphoneOn, onCheckedChange = { toggleMicrophone(it) }) {
            Icon(
                rememberMic(),
                contentDescription = "Start/Stop microphone",
            )
        }

        IconButton(onClick = settings) {
            Icon(
                Icons.Default.Settings,
                contentDescription = "Settings",
            )
        }

    }

}


@Composable
fun VoiceActiveNewRoundContent(
    byName: () -> Unit,
    byOrder: () -> Unit,
) {

    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
        Button(onClick = byName) {
            Text("By name")
        }

        Spacer(modifier = Modifier.size(10.dp))

        Button(onClick = byOrder) {
            Text("By order")
        }

    }
}

@Preview
@Composable
fun ControllerPrev() {
    HavenCompanionTheme {

        var miconOn by remember {
            mutableStateOf(false)
        }
        ScenarioController(
            onAddCharacter = { /*TODO*/ },
            onEdit = { /*TODO*/ },
            voiceActive = miconOn,
            onToggleVoiceActivation = { miconOn = !miconOn },
            onSettings = { /*TODO*/ },
            onNewRound = { /*TODO*/ },
            voiceActiveNewRoundByName = { /*TODO*/ },
            {

            },
            isListening = false,
            partialResult = "",
            stopListening = {}
        )
    }
}

@Preview
@Composable
fun ControllerPrev2() {
    HavenCompanionTheme {

        var miconOn by remember {
            mutableStateOf(false)
        }
        ScenarioController(
            onAddCharacter = { /*TODO*/ },
            onEdit = { /*TODO*/ },
            voiceActive = true,
            onToggleVoiceActivation = { miconOn = !miconOn },
            onSettings = { /*TODO*/ },
            onNewRound = { /*TODO*/ },
            voiceActiveNewRoundByName = { /*TODO*/ },
            {

            },
            isListening = true,
            partialResult = "Demonslayer 55, bajs korvemn 5345 434 3 344 343 43 3 43 43 43 ",
            stopListening = {}
        )
    }
}