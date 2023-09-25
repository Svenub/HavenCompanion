package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme


/**
 * A composable function that displays a dialog allowing the user to set the second initiative for a hero character.
 *
 * @param gameCharacter The [GameCharacter.Hero] for which the second initiative is being set.
 * @param onCharacterInitiativeUpdated A lambda that is invoked when the hero's second initiative is updated. It provides the updated [GameCharacter].
 * @param onCancel A lambda that is invoked when the user decides to cancel the changes. It provides a boolean indicating if the dialog should be dismissed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecondInitiativeDialog(
    gameCharacter: GameCharacter.Hero,
    onCharacterInitiativeUpdated: (GameCharacter) -> Unit,
    onCancel: (Boolean) -> Unit
) {

    val focusRequester = remember { FocusRequester() }
    var secondInitiative by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(gameCharacter) {
        secondInitiative = ""
    }

    AlertDialog(
        onDismissRequest = {
            onCancel(false)
        }
    ) {


        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Second initiative for ${gameCharacter.characterName}",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    textAlign = TextAlign.Center
                )
                Divider()


                Column {

                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        TextField(
                            modifier = Modifier
                                .focusRequester(focusRequester)
                                .onGloballyPositioned {
                                    if (secondInitiative.isBlank())
                                        focusRequester.requestFocus()
                                },
                            value = secondInitiative,
                            onValueChange = {
                                val filteredInput = it.filter { char -> char.isDigit() }
                                secondInitiative = filteredInput

                                if (filteredInput.length == 2) {
                                    onCharacterInitiativeUpdated(
                                        gameCharacter.copy(
                                            secondInitiative = secondInitiative.toIntOrNull(),
                                        )
                                    )
                                }
                            },
                            label = { Text("Second Initiative") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                            )
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(
                        onClick = {
                            onCancel(false)
                        },
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun SecondIinitPrev() {
    val hero1 = GameCharacter.Hero(
        characterName = "Banner Spear",
        colorInt = MaterialTheme.colorScheme.background.toArgb(),
        firstInitiative = 50,
    )

    var showDialog by remember { mutableStateOf(true) }
    HavenCompanionTheme {
        if (showDialog)
            SecondInitiativeDialog(
                gameCharacter = hero1,
                onCancel = { showDialog = false },
                onCharacterInitiativeUpdated = {})
    }
}
