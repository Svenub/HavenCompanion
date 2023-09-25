package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.game_character.InitiativeItem
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerDialog(
    title: String,
    characterName: String,
    showPreview: Boolean = true,
    colorPickerController: ColorPickerController,
    onColorChange: (Color) -> Unit,
    onDismiss: (Boolean) -> Unit

) {
    AlertDialog(
        onDismissRequest = { onDismiss(false) }
    ) {
        Surface(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier.padding(10.dp)
            ) {
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .padding(10.dp),
                    controller = colorPickerController,
                    onColorChanged = { colorEnvelope -> onColorChange(colorEnvelope.color) },
                )


                if (showPreview && colorPickerController.selectedColor.value != Color.Unspecified) {

                    Text(
                        text = title,
                        fontSize = MaterialTheme.typography.displaySmall.fontSize,
                        modifier = Modifier.padding(5.dp)
                    )
                    val char =
                        GameCharacter.Hero(
                            id = 0,
                            characterName = characterName,
                            colorInt = colorPickerController.selectedColor.value.toArgb()
                        )
                    InitiativeItem(gameCharacter = char, onItemClick = {})
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {

                    TextButton(onClick = { onDismiss(false) }) {
                        Text(text = "Close")
                    }
                }
            }
        }


    }
}


@Preview()
@Composable
fun ColorPickerPRev() {
    HavenCompanionTheme() {
        ColorPickerDialog(
            title = "Preview",
            characterName = "BAjskorb",
            colorPickerController = rememberColorPickerController(),
            onColorChange = {},
            onDismiss = {}
        )
    }
}