package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.game_character

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.presentation.initiative_view.InitiativeScreenConstants
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme
import se.umu.svke0008.havencompanion.ui.theme.PirataOne
import se.umu.svke0008.havencompanion.ui.theme.rememberSkull


/**
 * A composable function that displays the content for an exhausted character.
 * It shows the character's name and an icon (defaulted to a skull) to indicate the character's exhausted state.
 *
 * @param modifier A [Modifier] for the main row containing the character's name and icon.
 * @param characterName The name of the character to be displayed.
 * @param icon The [ImageVector] representing the icon to be shown next to the character's name. Defaults to a skull icon.
 * @param textSize The size of the text displaying the character's name. Defaults to [InitiativeScreenConstants.CHARACTER_ITEM_HEIGHT_DEFAULT].
 * @param charactersVisibleBeforeEllipsis The maximum number of characters to be shown before truncating with an ellipsis. Defaults to [InitiativeScreenConstants.CHARACTERS_BEFORE_ELLIPSIS_DEFAULT].
 * @param textColor The color of the character's name text. Defaults to [Color.Unspecified].
 * @param didOverFlow A lambda that is invoked to indicate if the text overflows its container. It provides a boolean indicating if an overflow occurred.
 */
@Composable
fun ExhaustedContent(
    modifier: Modifier = Modifier,
    characterName: String,
    icon: ImageVector = rememberSkull(),
    textSize: Float = InitiativeScreenConstants.CHARACTER_ITEM_HEIGHT_DEFAULT,
    charactersVisibleBeforeEllipsis: Int = InitiativeScreenConstants.CHARACTERS_BEFORE_ELLIPSIS_DEFAULT,
    textColor: Color = Color.Unspecified,
    didOverFlow: (Boolean) -> Unit
) {
    var nameWeight by remember { mutableFloatStateOf(0.5f) }
    var initWeight by remember { mutableFloatStateOf(0.2f) }
    var stopWeight by remember { mutableStateOf(false) }

    val density = LocalDensity.current
    val sizePx = with(density) { textSize.dp.toPx() }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(nameWeight)) {
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
            modifier = Modifier.weight(initWeight, fill = true),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Exhausted",
                tint = textColor,
                modifier = Modifier
                    .size(textSize.dp)
                    .drawWithContent {
                        if (this.size.width >= sizePx && this.size.height >= sizePx)
                            drawContent()
                    },
            )
        }


    }
}

@Preview
@Composable
fun ExhaustedContentPrew() {
    HavenCompanionTheme() {

        val hero1 = GameCharacter.Hero(
            characterName = "Testing",
            colorInt = MaterialTheme.colorScheme.background.toArgb(),
            firstInitiative = 50,
            secondInitiative = 40,
        )
        ExhaustedContent(characterName = hero1.characterName, didOverFlow = {})
    }
}