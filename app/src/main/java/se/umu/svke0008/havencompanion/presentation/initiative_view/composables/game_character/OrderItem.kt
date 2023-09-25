package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.game_character

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.presentation.initiative_view.InitiativeScreenConstants
import se.umu.svke0008.havencompanion.presentation.util_components.UiUtils
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

/**
 * A composable function that displays an item representing a game character with options to reorder the character.
 * The character's name is displayed prominently, and icons are provided to move the character up or down in a list.
 * The background color of the item is based on the character's color, and the text and icon colors are determined to contrast with the background.
 *
 * @param modifier A [Modifier] applied to the main column containing the character's name and move icons.
 * @param gameCharacter The [GameCharacter] object representing the character to be displayed.
 * @param onItemClick A lambda that is invoked when the item is clicked.
 * @param onMoveUp A lambda that is invoked when the move up icon is clicked. It receives the [GameCharacter] as a parameter.
 * @param onMoveDown A lambda that is invoked when the move down icon is clicked. It receives the [GameCharacter] as a parameter.
 * @param moveUpIcon An [ImageVector] representing the icon to move the character up. Default is a keyboard arrow up icon.
 * @param moveDownIcon An [ImageVector] representing the icon to move the character down. Default is a keyboard arrow down icon.
 * @param itemSize The size of the text displaying the character's name.
 */
@Composable
fun OrderItem(
    modifier: Modifier = Modifier,
    gameCharacter: GameCharacter,
    onItemClick: (GameCharacter) -> Unit,
    onMoveUp: (GameCharacter) -> Unit,
    onMoveDown: (GameCharacter) -> Unit,
    moveUpIcon: ImageVector = Icons.Default.KeyboardArrowUp,
    moveDownIcon: ImageVector = Icons.Default.KeyboardArrowDown,
    itemSize: Float = InitiativeScreenConstants.CHARACTER_ITEM_HEIGHT_DEFAULT,
) {

    val contrast = UiUtils.getTextColorForBackground(gameCharacter.color)

    Column(
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = gameCharacter.color)
            .clickable {
                onItemClick(gameCharacter)
            }
    ) {
        OrderContent(
            modifier = Modifier.padding(start = 5.dp),
            characterName = gameCharacter.characterName,
            moveDownIcon = moveDownIcon,
            moveUpIcon = moveUpIcon,
            textSize = itemSize,
            textColor = contrast,
            onMoveUp = { onMoveUp(gameCharacter) },
            onMoveDown = { onMoveDown(gameCharacter) },
            didOverFlow = {},
        )

    }


}

@Preview
@Composable
fun OrderItemPreq() {
    HavenCompanionTheme(havenFont = true) {
        val hero1 = GameCharacter.Hero(
            characterName = "Deathwalker",
            colorInt = MaterialTheme.colorScheme.background.toArgb(),
            firstInitiative = 50
        )

        // GameCharacterItemWithSlider(gameCharacter = hero1)

        OrderItem(gameCharacter = hero1, onItemClick = {}, onMoveUp = {}, onMoveDown = {})
    }
}