package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.umu.svke0008.havencompanion.domain.model.game_character.CharacterState
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.model.game_character.camelCaseToWords
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme


/**
 * A composable function that displays a dialog allowing the user to manually set the initiative values for a game character.
 *
 * @param gameCharacter The [GameCharacter] for which the initiative values are to be set.
 * @param onSave A lambda that is invoked when the user decides to save the entered initiative values. It provides the updated game character as a parameter.
 * @param showDialog A lambda that is invoked to control the visibility of the dialog. It provides a boolean indicating if the dialog should be shown.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualInitiativeDialog(
    gameCharacter: GameCharacter,
    onSave: (GameCharacter) -> Unit,
    showDialog: (Boolean) -> Unit
) {


    var firstInitiative by rememberSaveable { mutableStateOf("") }
    var secondInitiative by rememberSaveable { mutableStateOf("") }
    var heroState: CharacterState.HeroState by rememberSaveable {
        mutableStateOf(CharacterState.HeroState.Normal) }
    var monsterState: CharacterState.MonsterState by rememberSaveable {
        mutableStateOf(
            CharacterState.MonsterState.Normal
        )
    }


    AlertDialog(
        onDismissRequest = {
            showDialog(false)
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
                        onFirstInitiative = { firstInitiative = it },
                        onSecondInitiative = { secondInitiative = it },
                        onHeroState = { heroState = it }
                    )

                    is GameCharacter.Monster ->
                        MonsterInitiative(
                            monster = gameCharacter,
                            onFirstInitiative = { firstInitiative = it },
                            onMonsterState = { monsterState = it }
                        )
                }


                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {

                    TextButton(
                        onClick = {
                            showDialog(false)
                        },
                    ) {
                        Text("Cancel")
                    }
                    TextButton(

                        onClick = {
                            showDialog(false)
                            when (gameCharacter) {
                                is GameCharacter.Hero ->
                                    onSave(
                                        gameCharacter.copy(
                                            firstInitiative = firstInitiative.toIntOrNull(),
                                            secondInitiative = secondInitiative.toIntOrNull(),
                                            characterState = heroState
                                        )
                                    )

                                is GameCharacter.Monster ->
                                    onSave(
                                        gameCharacter.copy(
                                            firstInitiative = firstInitiative.toIntOrNull(),
                                            characterState = monsterState
                                        )
                                    )
                            }
                        },
                        enabled = firstInitiative.isNotBlank() ||
                                (heroState == CharacterState.HeroState.LongResting ||
                                        heroState == CharacterState.HeroState.Exhausted) ||
                                monsterState == CharacterState.MonsterState.Exhausted
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

/**
 * A private composable function that provides UI elements for setting initiative values for a hero character.
 *
 * @param hero The [GameCharacter.Hero] for which the initiative values are to be set.
 * @param onFirstInitiative A lambda that is invoked when the user enters the first initiative value.
 * @param onSecondInitiative A lambda that is invoked when the user enters the second initiative value.
 * @param onHeroState A lambda that is invoked when the user selects a hero state.
 */
@Composable
private fun HeroInitiative(
    hero: GameCharacter.Hero,
    onFirstInitiative: (String) -> Unit,
    onSecondInitiative: (String) -> Unit,
    onHeroState: (CharacterState.HeroState) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    var firstInitiative by rememberSaveable { mutableStateOf("") }
    var secondInitiative by rememberSaveable { mutableStateOf("") }
    var heroState: CharacterState.HeroState? by rememberSaveable { mutableStateOf(hero.characterState) }


    Column {

        val focusManager = LocalFocusManager.current


        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

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
                        onFirstInitiative(filteredInput)

                    if (filteredInput.length == 2) {
                        focusManager.moveFocus(FocusDirection.Next)
                    } else if (filteredInput.isBlank()) {
                        secondInitiative = ""
                    }
                },
                label = { Text("First Initiative") },
                singleLine = true,

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.clearFocus() }
                )

            )
            TextField(
                value = secondInitiative,
                onValueChange = {
                    val filteredInput = it.filter { char -> char.isDigit() }
                    secondInitiative = filteredInput
                    if (filteredInput.isNotBlank())
                        onSecondInitiative(secondInitiative)
                    if (filteredInput.length == 2) {
                        focusManager.clearFocus()
                    }
                },
                label = { Text("Second Initiative") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number,

                    ),
                keyboardActions = KeyboardActions(

                )
            )
        }
        val heroStates = CharacterState.HeroState::class.sealedSubclasses
            .filter{ it.objectInstance != CharacterState.HeroState.Pending }.reversed()
        var selected by remember { mutableIntStateOf(0) }
        Column {
            heroStates.forEachIndexed { index, state ->
                if (state.objectInstance != CharacterState.HeroState.Pending ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier
                            .clickable {
                                selected = index
                                heroState = state.objectInstance
                                state.objectInstance?.let {
                                    onHeroState(it)
                                }

                            }
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        RadioButton(
                            selected = selected == index,
                            onClick = {
                                selected = index
                                heroState = state.objectInstance
                                heroState?.let(onHeroState)
                            }
                        )
                        Text(text = state.simpleName?.camelCaseToWords() ?: "")
                    }
                }
            }
        }

    }

}

/**
 * A private composable function that provides UI elements for setting initiative values for a monster character.
 *
 * @param monster The [GameCharacter.Monster] for which the initiative values are to be set.
 * @param onFirstInitiative A lambda that is invoked when the user enters the initiative value.
 * @param onMonsterState A lambda that is invoked when the user selects a monster state.
 */
@Composable
private fun MonsterInitiative(
    monster: GameCharacter.Monster,
    onFirstInitiative: (String) -> Unit,
    onMonsterState: (CharacterState.MonsterState) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }
    var firstInitiative by rememberSaveable { mutableStateOf("") }
    var monsterState: CharacterState.MonsterState? by rememberSaveable { mutableStateOf(monster.characterState) }



    Column {

        val focusManager = LocalFocusManager.current

        var selected by remember { mutableStateOf<Int?>(null) }
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

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
                        onFirstInitiative(filteredInput)

                    if (filteredInput.length == 2) {
                        focusManager.clearFocus()
                    }
                },
                label = { Text("Initiative") },
                singleLine = true,

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.clearFocus() }
                )

            )

        }
        val monsterStates = CharacterState.MonsterState::class.sealedSubclasses.reversed()
        Column {
            monsterStates.forEachIndexed { index, state ->
                if (state.objectInstance != CharacterState.MonsterState.Pending)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier
                            .clickable {
                                selected = index
                                monsterState = state.objectInstance
                                monsterState?.let(onMonsterState)
                            }
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        RadioButton(
                            selected = selected == index,
                            onClick = {
                                monsterState = state.objectInstance
                                monsterState?.let(onMonsterState)
                            }
                        )
                        Text(text = state.simpleName?.camelCaseToWords() ?: "")
                    }
            }
        }

    }

}

@Preview
@Composable
fun Prew() {
    val hero1 = GameCharacter.Hero(
        characterName = "Banner Spear",
        colorInt = MaterialTheme.colorScheme.background.toArgb(),
        firstInitiative = 50,
    )

    var showDialog by remember { mutableStateOf(true) }
    HavenCompanionTheme {
        if (showDialog)
            ManualInitiativeDialog(
                gameCharacter = hero1,
                showDialog = { showDialog = false },
                onSave = {})
    }
}

@Preview
@Composable
fun Prew2() {
    val monster1 = GameCharacter.Monster(
        characterName = "Algox Guard",
        firstInitiative = 50,
    )

    var showDialog by remember { mutableStateOf(true) }
    HavenCompanionTheme {
        if (showDialog)
            ManualInitiativeDialog(
                gameCharacter = monster1,
                showDialog = { showDialog = false },
                onSave = {})
    }
}