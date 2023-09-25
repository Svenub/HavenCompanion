package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs


import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.umu.svke0008.havencompanion.domain.model.game_character.CharacterState
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.model.game_character.camelCaseToWords
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme
import se.umu.svke0008.havencompanion.ui.theme.rememberKeyboardArrowLeft
import se.umu.svke0008.havencompanion.ui.theme.rememberKeyboardArrowRight

/**
 * A composable function that displays a dialog allowing the user to set the initiative for a game character.
 * The user can input the initiative value for the character and choose the character's state.
 *
 * @param gameCharacter The [GameCharacter] for which the initiative is being set.
 * @param clearFocus A boolean indicating if the focus should be cleared after input.
 * @param onCharacterInitiativeUpdated A lambda that is invoked when the character's initiative is updated. It provides the updated [GameCharacter].
 * @param onCancel A lambda that is invoked when the user decides to cancel the changes. It provides a boolean indicating if the dialog should be dismissed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewRoundInitiativeDialog(
    gameCharacter: GameCharacter,
    clearFocus: Boolean = false,
    onCharacterInitiativeUpdated: (GameCharacter) -> Unit,
    previousCharacter: () -> Unit = {},
    nextCharacter: () -> Unit = {},
    onCancel: (Boolean) -> Unit
) {


    var initiative by remember { mutableStateOf("") }



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
                    text = "Initiative for ${gameCharacter.characterName}",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    textAlign = TextAlign.Center
                )
                Divider()
                when (gameCharacter) {
                    is GameCharacter.Hero -> HeroInitiative(
                        hero = gameCharacter,
                        clearFocus = clearFocus,
                        onInitiativeChange = { initiative = it },
                        onDone = { initiative, heroState ->

                            onCharacterInitiativeUpdated(
                                gameCharacter.copy(
                                    firstInitiative = initiative.toIntOrNull(),
                                    characterState = heroState
                                )
                            )
                        }
                    )

                    is GameCharacter.Monster ->
                        MonsterInitiative(
                            monster = gameCharacter,
                            clearFocus = clearFocus,
                            onInitiativeChange = { initiative = it },
                            onDone = { initiative, monsterState ->
                                onCharacterInitiativeUpdated(
                                    gameCharacter.copy(
                                        firstInitiative = initiative.toIntOrNull(),
                                        characterState = monsterState
                                    )
                                )
                            }
                        )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    Column {
                        IconButton(onClick = previousCharacter) {
                            Icon(
                                rememberKeyboardArrowLeft(),
                                contentDescription = "Previous character"
                            )
                        }
                    }

                    Column {
                        TextButton(
                            onClick = {
                                onCancel(false)
                            },
                        ) {
                            Text("Cancel")
                        }
                    }
                    Column {
                        IconButton(onClick = nextCharacter) {
                            Icon(
                                rememberKeyboardArrowRight(),
                                contentDescription = "Next character"
                            )
                        }
                    }

                }

                /*
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

                 */
            }
        }
    }
}

/**
 * A composable function that displays the initiative input for a hero character.
 *
 * @param hero The [GameCharacter.Hero] for which the initiative is being set.
 * @param clearFocus A boolean indicating if the focus should be cleared after input.
 * @param onInitiativeChange A lambda that is invoked when the initiative value changes. It provides the updated initiative value.
 * @param onDone A lambda that is invoked when the initiative input is complete. It provides the initiative value and the selected [CharacterState.HeroState].
 */
@Composable
private fun HeroInitiative(
    hero: GameCharacter.Hero,
    clearFocus: Boolean = false,
    onInitiativeChange: (String) -> Unit,
    onDone: (initiative: String, heroState: CharacterState.HeroState) -> Unit
) {

    val focusRequester = remember { FocusRequester() }
    var firstInitiative by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(hero) {
        firstInitiative = ""
    }

    Column {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val focusManager = LocalFocusManager.current
            TextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onGloballyPositioned {
                        if (firstInitiative.isBlank())
                            focusRequester.requestFocus()
                    },
                value = firstInitiative,
                onValueChange = {
                    val filteredInput = it.filter { char -> char.isDigit() }
                    firstInitiative = filteredInput
                    if (filteredInput.isNotBlank())
                        onInitiativeChange(filteredInput)
                    if (filteredInput.length == 2) {
                        onDone(firstInitiative, CharacterState.HeroState.Normal)
                        if (clearFocus)
                            focusManager.clearFocus()

                    }
                },
                label = { Text("First Initiative") },
                singleLine = true,

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )

            )
        }
        val heroStates = CharacterState.HeroState::class.sealedSubclasses
        var selectedState: CharacterState.HeroState? by remember { mutableStateOf(null) }

        Column {
            heroStates.forEachIndexed { index, state ->
                if (state.objectInstance != CharacterState.HeroState.Normal
                    && state.objectInstance != CharacterState.HeroState.Pending
                )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier
                            .clickable {
                                selectedState = state.objectInstance
                                selectedState?.let { onDone(firstInitiative, it) }
                            }
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        RadioButton(
                            selected = selectedState == heroStates[index],
                            onClick = {
                                selectedState = state.objectInstance
                                state.objectInstance?.let {
                                    selectedState?.let { onDone(firstInitiative, it) }
                                }

                            }
                        )
                        Text(text = state.simpleName?.camelCaseToWords() ?: "")
                    }
            }
        }

    }

}

/**
 * A composable function that displays the initiative input for a monster character.
 *
 * @param monster The [GameCharacter.Monster] for which the initiative is being set.
 * @param clearFocus A boolean indicating if the focus should be cleared after input.
 * @param onInitiativeChange A lambda that is invoked when the initiative value changes. It provides the updated initiative value.
 * @param onDone A lambda that is invoked when the initiative input is complete. It provides the initiative value and the selected [CharacterState.MonsterState].
 */
@Composable
private fun MonsterInitiative(
    monster: GameCharacter.Monster,
    clearFocus: Boolean = false,
    onInitiativeChange: (String) -> Unit,
    onDone: (initiative: String, heroState: CharacterState.MonsterState) -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var firstInitiative by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(monster) {
        firstInitiative = ""
    }

    Column {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            val focusManager = LocalFocusManager.current
            TextField(
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onGloballyPositioned {
                        if (firstInitiative.isBlank())
                            focusRequester.requestFocus()
                    },
                value = firstInitiative,
                onValueChange = {
                    val filteredInput = it.filter { char -> char.isDigit() }
                    firstInitiative = filteredInput
                    if (filteredInput.isNotBlank())
                        onInitiativeChange(filteredInput)

                    if (filteredInput.length == 2) {
                        onDone(firstInitiative, CharacterState.MonsterState.Normal)
                        if (clearFocus)
                            focusManager.clearFocus()

                    }
                },
                label = { Text("Initiative") },
                singleLine = true,

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )

            )

        }

        Column {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .clickable {
                        onDone(firstInitiative, CharacterState.MonsterState.Exhausted)
                    }
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                RadioButton(
                    selected = false,
                    onClick = {
                        onDone(firstInitiative, CharacterState.MonsterState.Exhausted)
                    }
                )
                Text(text = "Dead")
            }

        }

    }

}

@Preview
@Composable
fun NewRoundDialogPrev() {
    val hero1 = GameCharacter.Hero(
        characterName = "Banner Spear",
        colorInt = MaterialTheme.colorScheme.background.toArgb(),
        firstInitiative = 50,
    )


    HavenCompanionTheme {
        NewRoundInitiativeDialog(
            gameCharacter = hero1,
            onCancel = { /*TODO*/ },
            onCharacterInitiativeUpdated = {})
    }
}


@Preview
@Composable
fun NewRoundDialogPrev2() {
    val monster1 = GameCharacter.Monster(
        characterName = "Algox Guard",
        firstInitiative = 50,
    )

    var showDialog by remember { mutableStateOf(true) }
    HavenCompanionTheme {
        NewRoundInitiativeDialog(
            gameCharacter = monster1,
            onCharacterInitiativeUpdated = {},
            onCancel = {})
    }
}