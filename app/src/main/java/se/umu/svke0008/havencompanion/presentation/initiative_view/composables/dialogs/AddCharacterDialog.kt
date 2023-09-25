package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.skydoves.colorpicker.compose.AlphaTile
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterType
import se.umu.svke0008.havencompanion.domain.utils.CharacterResult

@Composable
fun AddCharacterDialog(
    showDialog: (Boolean) -> Unit,
    dismissError: (Boolean) -> Unit,
    characterTypes: Array<GameCharacterType> = GameCharacterType.values(),
    onCharacterAdd: (GameCharacter) -> Unit,
    addCharacterResult: CharacterResult
) {
    var characterName by remember { mutableStateOf("") }
    var selectedType by remember { mutableStateOf(characterTypes[0]) }
    var aliasTags by remember { mutableStateOf("") }
    var color by remember { mutableStateOf(Color.Black) }
    val showCharacterDialog = remember { mutableStateOf(true) }
    val showColorPicker = remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }
    val controller = rememberColorPickerController()
    var errorDismissed by remember { mutableStateOf(false) }



    AlertDialog(
        onDismissRequest = { showDialog(false) },
        title = {
            Column {
                Text(
                    text = "Add new character",
                    modifier = Modifier.padding(bottom = 16.dp),
                    style = TextStyle(fontSize = 20.sp)
                )
            }
        },
        text = {
            Column {
                OutlinedTextField(
                    value = characterName,
                    onValueChange = { characterName = it },
                    label = { Text("Character name") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp)
                )
                Text(
                    "Select character type",
                    modifier = Modifier.padding(bottom = 4.dp),
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 16.sp)
                )
                Box {
                    Text(
                        text = selectedType.toString(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { expanded = true }
                    )
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        characterTypes.forEach {  type ->
                            DropdownMenuItem(text = {
                                Text(
                                    text = type.toString()
                                )
                            }, onClick = {
                                selectedType = type
                                expanded = false
                            })
                        }
                    }
                }
                OutlinedTextField(
                    value = aliasTags,
                    onValueChange = { aliasTags = it },
                    label = { Text("Alias tags, separate with commas") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp)
                )
                Text("Pick Color", modifier = Modifier.align(Alignment.CenterHorizontally))
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .padding(10.dp),
                    controller = controller,
                    onColorChanged = { colorEnvelope: ColorEnvelope ->
                        color = colorEnvelope.color
                    }
                )
                AlphaTile(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .align(Alignment.CenterHorizontally),
                    controller = controller,
                    tileOddColor = color
                )
            }

            if (addCharacterResult is CharacterResult.Error) {
                Snackbar(
                    action = {
                        Button(onClick = { dismissError(false) }) {
                            Text("Dismiss")
                        }
                    },
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(addCharacterResult.message)
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val newCharacter: GameCharacter = when (selectedType) {
                    GameCharacterType.Hero ->  GameCharacter.Hero(
                        id = 0,
                        characterName = characterName,
                        nameAlias = aliasTags.split(","),
                        colorInt = color.toArgb()
                    )
                    GameCharacterType.Monster -> GameCharacter.Monster(
                        id = 0,
                        characterName = characterName,
                        nameAlias = aliasTags.split(","),
                        colorInt = color.toArgb()
                    )
                }
                onCharacterAdd(newCharacter)
                errorDismissed = false

            }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = { showDialog(false) }) {
                Text("Cancel")
            }
        }
    )


}
