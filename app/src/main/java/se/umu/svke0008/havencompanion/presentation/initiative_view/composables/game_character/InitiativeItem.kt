package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.game_character


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.umu.svke0008.havencompanion.R
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.model.game_character.isPending
import se.umu.svke0008.havencompanion.presentation.initiative_view.InitiativeScreenConstants.CHARACTER_ITEM_HEIGHT_DEFAULT
import se.umu.svke0008.havencompanion.presentation.util_components.ResourceUtils
import se.umu.svke0008.havencompanion.presentation.util_components.UiUtils.getTextColorForBackground
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme


/**
 * A composable function that displays an initiative item for a game character.
 * The item consists of the character's name, an optional icon, and the character's initiatives.
 * The background color of the item is based on the character's color, and the text color is determined by the contrast against the background.
 * If the character is pending, the item's opacity is reduced.
 *
 * @param modifier A [Modifier] for the main column containing the initiative item.
 * @param gameCharacter The [GameCharacter] object representing the character to be displayed.
 * @param onItemClick A lambda that is invoked when the initiative item is clicked.
 * @param icon An optional [Int] resource ID for the character's icon.
 * @param itemSize The size of the text displaying the character's name and initiatives.
 */
@Composable
fun InitiativeItem(
    modifier: Modifier = Modifier,
    gameCharacter: GameCharacter,
    onItemClick: (GameCharacter) -> Unit,
    icon: Int? = null,
    itemSize: Float = CHARACTER_ITEM_HEIGHT_DEFAULT,
) {

    val contrast = getTextColorForBackground(gameCharacter.color)

    val colors = ResourceUtils.getColorPalette(gameCharacter)

    val brush: Brush? =
        if (colors.isNotEmpty()) Brush.linearGradient(colors = colors.map { Color(it) })
        else null

    Column(
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth()
            .alpha(if (!gameCharacter.isPending()) 1f else 0.3f)
            .clip(shape = RoundedCornerShape(8.dp))
            .let { if (brush != null) it.background(brush = brush) else it.background(color = gameCharacter.color) }
            .clickable {
                onItemClick(gameCharacter)
            }
    ) {
        InitiativeContent(
            modifier = Modifier.padding(start = 5.dp),
            gameCharacter = gameCharacter,
            firstInitiative = gameCharacter.firstInitiative,
            secondInitiative =
            if (gameCharacter is GameCharacter.Hero)
                gameCharacter.secondInitiative
            else null,
            textSize = itemSize,
            textColor = contrast,
            characterIcon = icon,
            didOverFlowFirst = {},
            didOverFlowSecond = {}
        )
    }
}


@Preview(showBackground = true, showSystemUi = false)
@Composable
fun GameCharacterPreview() {
    HavenCompanionTheme(havenFont = true) {
        val hero1 = GameCharacter.Hero(
            characterName = "Deathwalker",
            colorInt = MaterialTheme.colorScheme.background.toArgb(),
            firstInitiative = 50
        )

        // GameCharacterItemWithSlider(gameCharacter = hero1)
        ExhaustedItem(
            gameCharacter = hero1,
            onItemClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GameCharacterPreview2() {
    HavenCompanionTheme(havenFont = true) {
        val hero1 = GameCharacter.Hero(
            characterName = "Bone Shaper",
            colorInt = MaterialTheme.colorScheme.background.toArgb(),
            firstInitiative = 50
        )

        // GameCharacterItemWithSlider(gameCharacter = hero1)
        InitiativeItem(
            gameCharacter = hero1,
            onItemClick = {},
            icon = R.drawable.skull,
        )
    }
}