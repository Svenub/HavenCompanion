package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import kotlinx.coroutines.launch
import se.umu.svke0008.havencompanion.data.local.db.DatabaseResult
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterEntity
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterType
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.toColorString
import se.umu.svke0008.havencompanion.data.utils.Event
import se.umu.svke0008.havencompanion.domain.utils.ValidationResult
import se.umu.svke0008.havencompanion.presentation.util_components.UiUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCharacterDialog(
    character: GameCharacterEntity,
    onUpdate: (GameCharacterEntity) -> Unit,
    onDelete: (GameCharacterEntity) -> Unit,
    validationResult: ValidationResult,
    onResetValidationResult: () -> Unit,
    onSaveResult: Event<DatabaseResult<String>?> = Event(null),
    onDismiss: (Boolean) -> Unit
) {

    val scope = rememberCoroutineScope()
    var isColorPickerDialogVisible by remember { mutableStateOf(false) }
    var characterName by remember { mutableStateOf(character.characterName) }
    var selectedCharacterType by remember { mutableStateOf(character.characterType) }
    var aliasTags by remember { mutableStateOf(character.nameAlias) }
    val characterTypes = GameCharacterType.values()
    val selectedIndex =
        remember { mutableIntStateOf(GameCharacterType.values().indexOf(selectedCharacterType)) }

    val colorController = rememberColorPickerController()
    var color by remember { mutableIntStateOf(character.colorInt) }


    LaunchedEffect(key1 = validationResult) {
        if (validationResult == ValidationResult.Valid) {
            onDismiss(false)
            onResetValidationResult()
        }
    }


    AlertDialog(onDismissRequest = { onDismiss(false) }) {
        val focusManager = LocalFocusManager.current
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {

            LazyColumn(
                modifier = Modifier.widthIn(max = 480.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 24.dp),
            ) {
                item {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Edit ${character.characterName}",
                            fontSize = MaterialTheme.typography.displayMedium.fontSize,
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            lineHeight = 40.sp
                        )

                    }
                    Divider()

                }
                item {
                    TextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        placeholder = { Text(text = character.characterName) },
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
                                Text(ValidationResult.SIMILAR_CHARACTER_NAME + " (" + validationResult.similarName + ")")
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
                        placeholder = { Text(text = character.nameAlias) },
                        label = { Text("Alias tags, separate with commas") },
                        value = aliasTags,
                        onValueChange = {
                            if (validationResult is ValidationResult.Invalid) {
                                onResetValidationResult()
                            }
                            aliasTags =
                                UiUtils.filterAlphabeticWithSingleWhitespaceAndComma(it)
                        },
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
                                Text(ValidationResult.SIMILAR_ALIAS_NAME + " (" + validationResult.similarAlias + ")")
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

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            TextButton(onClick = { isColorPickerDialogVisible = true }) {
                                Text(
                                    text = "Pick Color"
                                )
                            }

                        }
                    }


                }

                item {
                    Divider()
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextButton(onClick = {
                            onDelete(character)
                            onDismiss(false)
                        }) {
                            Text(text = "Delete", color = Color.Red)
                        }

                        Row(
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = {
                                onResetValidationResult()
                                onDismiss(false)
                            }) {
                                Text(text = "Close")
                            }

                            Button(
                                enabled = characterName.isNotBlank(),
                                onClick = {
                                    scope.launch {
                                        onUpdate(
                                            character.copy(
                                                characterName = characterName,
                                                nameAlias = aliasTags,
                                                color = color.toColorString(),
                                                characterType = selectedCharacterType
                                            )
                                        )
                                    }
                                }
                            ) {
                                Text(text = "Save")
                            }
                        }
                    }


                }

            }

            onSaveResult.peekContent()?.let { event ->
                if (event is DatabaseResult.Loading) {
                    Box(
                        Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }


            if (isColorPickerDialogVisible) {
                ColorPickerDialog(
                    title = "Preview",
                    characterName = characterName,
                    colorPickerController = colorController,
                    onColorChange = { color = it.toArgb() },
                    onDismiss = { isColorPickerDialogVisible = it }
                )
            }
        }


    }
}