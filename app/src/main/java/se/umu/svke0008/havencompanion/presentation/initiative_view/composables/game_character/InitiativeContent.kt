package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.game_character



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import se.umu.svke0008.havencompanion.R
import se.umu.svke0008.havencompanion.domain.model.game_character.CharacterState
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.model.game_character.isLongResting
import se.umu.svke0008.havencompanion.presentation.initiative_view.InitiativeScreenConstants.CHARACTER_ITEM_HEIGHT_DEFAULT
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme
import se.umu.svke0008.havencompanion.ui.theme.PirataOne

import se.umu.svke0008.havencompanion.ui.theme.rememberHotel

/**
 * A composable function that displays the initiative content for a game character.
 * The content consists of the character's name, an optional icon, and the character's initiatives.
 * If the character is a hero and is long resting, a resting icon is shown instead of the initiatives.
 *
 * @param modifier A [Modifier] for the main row containing the initiative content.
 * @param gameCharacter The [GameCharacter] object representing the character to be displayed.
 * @param characterIcon An optional [Int] resource ID for the character's icon.
 * @param restIcon The [ImageVector] representing the icon to be shown when the hero is long resting. Defaults to a hotel icon.
 * @param textSize The size of the text displaying the character's name and initiatives.
 * @param textColor The color of the text and icons.
 * @param firstInitiative The first initiative value of the character.
 * @param secondInitiative The second initiative value of the character.
 * @param didOverFlowFirst A lambda that is invoked with a boolean indicating if the first initiative text overflowed its container.
 * @param didOverFlowSecond A lambda that is invoked with a boolean indicating if the second initiative text overflowed its container.
 */
@Composable
fun InitiativeContent(
    modifier: Modifier = Modifier,
    gameCharacter: GameCharacter,
    characterIcon: Int? = null,
    restIcon: ImageVector = rememberHotel(),
    textSize: Float = CHARACTER_ITEM_HEIGHT_DEFAULT,
    textColor: Color = Color.Unspecified,
    firstInitiative: Int? = null,
    secondInitiative: Int? = null,
    didOverFlowFirst: (Boolean) -> Unit = {},
    didOverFlowSecond: (Boolean) -> Unit = {}
) {

    var nameWeight by remember { mutableFloatStateOf(0.5f) }
    var initWeight by remember { mutableFloatStateOf(0.2f) }
    var stopWeight by remember { mutableStateOf(false) }

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(nameWeight)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                Text(
                    text = gameCharacter.characterName,
                    color = textColor,
                    fontSize = textSize.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontFamily = PirataOne
                )
                Spacer(modifier = Modifier.size(16.dp))
                characterIcon?.let {
                    CustomIcon(icon = it, textSize = textSize / 2, textColor = textColor)
                }
            }
        }
        Column(
            modifier = Modifier.weight(initWeight, fill = true),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (gameCharacter is GameCharacter.Hero && gameCharacter.isLongResting()) {
                    Icon(
                        imageVector = restIcon,
                        contentDescription = "Long resting",
                        tint = textColor
                    )
                } else {
                    Text(
                        text = firstInitiative?.toString() ?: "",
                        color = textColor,
                        fontSize = textSize.sp,
                        softWrap = false,
                        fontFamily = PirataOne,
                        onTextLayout = { result ->
                            if (result.didOverflowWidth)
                                didOverFlowFirst(true)
                            else didOverFlowFirst(false)
                        }
                    )
                    secondInitiative?.let {
                        Text(
                            text = it.toString(),
                            color = textColor,
                            fontSize = textSize.sp,
                            softWrap = false,
                            onTextLayout = { result ->
                                if (result.didOverflowWidth && !stopWeight) {
                                    nameWeight -= 0.1f
                                    initWeight += 0.1f
                                } else if (result.didOverflowWidth) {
                                    didOverFlowSecond(true)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun CustomIcon(icon: Int, textSize: Float, textColor: Color) {
    val density = LocalDensity.current
    val sizePx = with(density) { textSize.dp.toPx() }

    Icon(
        painter = painterResource(id = icon),
        modifier = Modifier
            .size(textSize.dp)
            .drawWithContent {
                if (this.size.width >= sizePx && this.size.height >= sizePx)
                    drawContent()
            },
        contentDescription = null,
        tint = textColor
    )
}

@Preview(showBackground = true)
@Composable
fun CharacterContent2Preview() {
    HavenCompanionTheme(havenFont = true) {
        val hero1 = GameCharacter.Hero(
            characterName = "Testing",
            colorInt = MaterialTheme.colorScheme.background.toArgb(),
            characterState = CharacterState.HeroState.LongResting
        )

        InitiativeContent(
            gameCharacter = hero1,
            firstInitiative = hero1.firstInitiative,
            secondInitiative = hero1.secondInitiative,
            characterIcon = R.drawable.skull,
            textSize = 150f,
            didOverFlowFirst = {  },
            didOverFlowSecond = {  }
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CharacterContent2Preview2() {
    HavenCompanionTheme(havenFont = true) {
        val hero1 = GameCharacter.Hero(
            characterName = "Banner spear",
            colorInt = MaterialTheme.colorScheme.background.toArgb(),
            characterState = CharacterState.HeroState.LongResting
        )

        InitiativeContent(
            gameCharacter = hero1,
            firstInitiative = hero1.firstInitiative,
            secondInitiative = hero1.secondInitiative,
            characterIcon = R.drawable.skull,
            textSize = CHARACTER_ITEM_HEIGHT_DEFAULT,
            didOverFlowFirst = {  },
            didOverFlowSecond = {  }
        )
    }
}