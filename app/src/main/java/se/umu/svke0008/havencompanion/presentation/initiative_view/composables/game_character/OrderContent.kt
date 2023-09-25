package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.game_character

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.presentation.initiative_view.InitiativeScreenConstants
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme
import se.umu.svke0008.havencompanion.ui.theme.PirataOne


/**
 * A composable function that displays a character's name along with icons to move the character up or down in a list.
 * The character's name is displayed on the left side, while the move up and move down icons are displayed on the right side.
 * Clicking on the move up or move down icons will trigger the respective lambdas provided.
 *
 * @param modifier A [Modifier] applied to the main row containing the character's name and move icons.
 * @param characterName The name of the character to be displayed.
 * @param moveUpIcon An [ImageVector] representing the icon to move the character up. Default is a keyboard arrow up icon.
 * @param moveDownIcon An [ImageVector] representing the icon to move the character down. Default is a keyboard arrow down icon.
 * @param onMoveUp A lambda that is invoked when the move up icon is clicked.
 * @param onMoveDown A lambda that is invoked when the move down icon is clicked.
 * @param textSize The size of the text displaying the character's name.
 * @param textColor The color of the text and icons.
 * @param didOverFlow A lambda that is invoked if the character's name overflows its container. Default is an empty lambda.
 */
@Composable
fun OrderContent(
    modifier: Modifier = Modifier,
    characterName: String,
    moveUpIcon: ImageVector = Icons.Default.KeyboardArrowUp,
    moveDownIcon: ImageVector = Icons.Default.KeyboardArrowDown,
    onMoveUp: () -> Unit,
    onMoveDown: () -> Unit,
    textSize: Float = InitiativeScreenConstants.CHARACTER_ITEM_HEIGHT_DEFAULT,
    textColor: Color = Color.Unspecified,
    didOverFlow: (Boolean) -> Unit = {}
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(0.5f)) {
            Row(
                modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Text(
                    text = characterName,
                    color = textColor,
                    fontSize = textSize.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = PirataOne
                )
            }


        }
        Column(
            modifier = Modifier.weight(0.2f, fill = true),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Icon(
                    imageVector = moveUpIcon,
                    contentDescription = "Move up",
                    tint = textColor,
                    modifier = Modifier
                        .size(textSize.dp)
                        .clickable {
                            onMoveUp()
                        }

                )
                Icon(
                    imageVector = moveDownIcon,
                    contentDescription = "Move down",
                    tint = textColor,
                    modifier = Modifier
                        .size(textSize.dp)
                        .clickable {
                            onMoveDown()
                        }


                )
            }
        }


    }
}

@Preview
@Composable
fun OrderContentPrew() {
    HavenCompanionTheme {

        val hero1 = GameCharacter.Hero(
            characterName = "Testing",
            colorInt = MaterialTheme.colorScheme.background.toArgb(),
            firstInitiative = 50,
            secondInitiative = 40,
        )
        OrderContent(
            characterName = hero1.characterName,
            onMoveDown = {},
            onMoveUp = {})
    }
}