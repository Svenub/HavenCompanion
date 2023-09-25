package se.umu.svke0008.havencompanion.presentation.initiative_view.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isUnspecified
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterEntity
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterType
import se.umu.svke0008.havencompanion.domain.model.factories.GameCharacterFactory
import se.umu.svke0008.havencompanion.domain.model.factories.HeroFactory
import se.umu.svke0008.havencompanion.domain.model.factories.MonsterFactory
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.utils.ValidationResult
import se.umu.svke0008.havencompanion.domain.utils.ValidationResult.Companion.SIMILAR_ALIAS_NAME
import se.umu.svke0008.havencompanion.domain.utils.ValidationResult.Companion.SIMILAR_CHARACTER_NAME
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs.ColorPickerDialog
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.game_character.InitiativeItem
import se.umu.svke0008.havencompanion.presentation.initiative_view.ui_events.CharacterOperationUIEvent
import se.umu.svke0008.havencompanion.presentation.util_components.UiUtils
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateCharacterScreen(
    validationResult: ValidationResult,
    onResetValidationResult: () -> Unit,
    onSave: (GameCharacterEntity) -> Unit,
    onSaveResult: CharacterOperationUIEvent = CharacterOperationUIEvent.NoEvent,
    navController: NavController
) {


    var isColorPickerDialogVisible by remember { mutableStateOf(false) }
    var characterName by remember { mutableStateOf("") }
    var selectedCharacterType by remember { mutableStateOf(GameCharacterType.Hero) }
    var aliasTags by remember { mutableStateOf("") }
    val characterTypes = GameCharacterType.values()
    val selectedIndex = remember { mutableIntStateOf(0) }

    val colorController = rememberColorPickerController()
    var color by remember { mutableStateOf(Color.Unspecified) }

    val factory: GameCharacterFactory by remember {
        derivedStateOf {
            when (selectedCharacterType) {
                GameCharacterType.Hero -> HeroFactory()
                GameCharacterType.Monster -> MonsterFactory()
            }
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }

    val focusManager = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )

        }
    ) { paddingValues ->

        Box(contentAlignment = Alignment.Center) {

            Box(
                modifier = Modifier
                    .padding(paddingValues),
                contentAlignment = Alignment.TopCenter
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "Create character",
                                fontSize = MaterialTheme.typography.displayMedium.fontSize
                            )

                        }
                        Divider()

                    }
                    item {
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            label = { Text("Character name") },
                            value = characterName,
                            onValueChange = {
                                if (validationResult is ValidationResult.Invalid) {
                                    onResetValidationResult()
                                }
                                characterName = UiUtils.filterAlphabeticWithSingleWhitespace(it)
                            },
                            singleLine = true,
                            trailingIcon = {
                                AnimatedVisibility(
                                    visible = characterName.isNotBlank(),
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    IconButton(onClick = { characterName = "" }) {
                                        Icon(Icons.Outlined.Clear, "Clear")
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Sentences
                            ),
                            keyboardActions = KeyboardActions {
                                focusManager.moveFocus(FocusDirection.Next)
                            },
                            isError = (validationResult is ValidationResult.Invalid.SimilarName),
                            supportingText = {
                                if (validationResult is ValidationResult.Invalid.SimilarName) {
                                    Text(SIMILAR_CHARACTER_NAME + " (" + validationResult.similarName + ")")
                                }
                            }
                        )

                    }
                    item {
                        var text by remember { mutableStateOf("") }
                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            label = { Text("Alias tags, separate with commas") },
                            value = aliasTags,
                            onValueChange = {
                                if (validationResult is ValidationResult.Invalid) {
                                    onResetValidationResult()
                                }
                                aliasTags = UiUtils.filterAlphabeticWithSingleWhitespaceAndComma(it)
                            },
                            singleLine = true,
                            trailingIcon = {
                                AnimatedVisibility(
                                    visible = text.isNotBlank(),
                                    enter = fadeIn(),
                                    exit = fadeOut()
                                ) {
                                    IconButton(onClick = { text = "" }) {
                                        Icon(Icons.Outlined.Clear, "Clear")
                                    }
                                }
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Done,
                                capitalization = KeyboardCapitalization.Sentences
                            ),
                            keyboardActions = KeyboardActions {
                                focusManager.clearFocus()
                            },
                            isError = (validationResult is ValidationResult.Invalid.AliasTooSimilar),
                            supportingText = {
                                if (validationResult is ValidationResult.Invalid.AliasTooSimilar) {
                                    Text(SIMILAR_ALIAS_NAME + " (" + validationResult.similarAlias + ")")
                                }
                            }
                        )
                    }

                    item {
                        Text(
                            text = "Character type",
                            fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 8.dp,
                                bottom = 8.dp
                            )
                        )
                    }
                    item {
                        Column {
                            characterTypes.forEachIndexed { index, type ->
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                                    modifier = Modifier
                                        .clickable {
                                            selectedIndex.intValue = index
                                            selectedCharacterType = characterTypes[index]
                                        }
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                ) {
                                    RadioButton(
                                        selected = selectedIndex.intValue == index,
                                        onClick = {
                                            selectedIndex.intValue = index
                                            selectedCharacterType = characterTypes[index]
                                        }
                                    )
                                    Text(text = type.name)
                                }

                            }
                        }

                    }


                    if (color != Color.Unspecified &&
                        characterName.isNotBlank() && !isColorPickerDialogVisible
                    ) {
                        item {

                            Text(
                                text = "Preview",
                                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                                modifier = Modifier.padding(
                                    start = 8.dp,
                                    end = 8.dp,
                                    top = 12.dp,
                                    bottom = 4.dp
                                )
                            )
                            val char =
                                GameCharacter.Hero(
                                    characterName = characterName,
                                    colorInt = colorController.selectedColor.value.toArgb()
                                )
                            InitiativeItem(gameCharacter = char, onItemClick = {})
                        }
                    }

                    item {


                        Divider()
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.End
                        ) {

                            TextButton(onClick = { isColorPickerDialogVisible = true }) {
                                Text(
                                    text = "Pick Color"
                                )
                            }
                            Button(
                                enabled = characterName.isNotBlank() && !color.isUnspecified,
                                onClick = {
                                    onSave(
                                        factory.createCharacterEntity(
                                            name = characterName,
                                            nameAlias = aliasTags,
                                            color = color
                                        )
                                    )
                                }) {
                                Text(text = "Save")
                            }
                        }

                    }

                }

                LaunchedEffect(key1 = onSaveResult) {
                    if (onSaveResult is CharacterOperationUIEvent.Loading) {
                        snackbarHostState.showSnackbar(onSaveResult.message)
                    }
                }

            }

            if (onSaveResult is CharacterOperationUIEvent.Loading) {
                Box(
                    Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

        }
    }

    if (isColorPickerDialogVisible) {
        ColorPickerDialog(
            title = "Preview",
            characterName = characterName,
            colorPickerController = colorController,
            onColorChange = { color = it },
            onDismiss = { isColorPickerDialogVisible = it }
        )
    }

}

@Preview(showBackground = true)
@Composable
fun CreateCharacterPrev() {
    HavenCompanionTheme {
        val navController = rememberNavController()
        CreateCharacterScreen(
            validationResult = ValidationResult.Initial,
            onResetValidationResult = { /*TODO*/ },
            onSave = {},
            navController = navController
        )
    }
}

