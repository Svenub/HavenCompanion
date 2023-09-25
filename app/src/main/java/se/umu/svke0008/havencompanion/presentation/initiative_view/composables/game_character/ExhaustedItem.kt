package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.game_character

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.presentation.initiative_view.InitiativeScreenConstants
import se.umu.svke0008.havencompanion.presentation.util_components.UiUtils
import se.umu.svke0008.havencompanion.ui.theme.rememberSkull


/**
 * A composable function that displays an item representing an exhausted character.
 * The item consists of the character's name and an icon (defaulted to a skull) to indicate the character's exhausted state.
 * The item is clickable and invokes the provided `onItemClick` lambda when clicked.
 *
 * @param modifier A [Modifier] for the main column containing the exhausted content.
 * @param gameCharacter The [GameCharacter] object representing the character to be displayed.
 * @param onItemClick A lambda that is invoked when the item is clicked. It provides the clicked [GameCharacter].
 * @param icon The [ImageVector] representing the icon to be shown next to the character's name. Defaults to a skull icon.
 * @param itemSize The size of the item displaying the character's name. Defaults to [InitiativeScreenConstants.CHARACTER_ITEM_HEIGHT_DEFAULT].
 */
@Composable
fun ExhaustedItem(
    modifier: Modifier = Modifier,
    gameCharacter: GameCharacter,
    onItemClick: (GameCharacter) -> Unit,
    icon: ImageVector = rememberSkull(),
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
        ExhaustedContent(
            modifier = Modifier.padding(start = 5.dp),
            characterName = gameCharacter.characterName,
            textColor = contrast,
            textSize = itemSize,
            icon = icon,
            didOverFlow = {})
    }
}