package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.game_character

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.tooling.preview.Preview
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.presentation.initiative_view.InitiativeScreenConstants.CHARACTER_ITEM_HEIGHT_DEFAULT
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

/**
 * A composable function that displays a list of game characters. Each item in the list can be clicked, moved up, or moved down.
 * The list is displayed within a box with a background color based on the current material theme's background color.
 * The content of each item is determined by the provided `itemContent` lambda.
 *
 * @param modifier A [Modifier] for the main box containing the list of items.
 * @param list A [List] of [GameCharacter] objects representing the characters to be displayed.
 * @param itemSize The size of the text displaying the character's name and initiatives.
 * @param onCharacterClick A lambda that is invoked when an item in the list is clicked. Default is an empty lambda.
 * @param onMoveCharacterUp A lambda that is invoked when the action to move a character up is triggered. Default is an empty lambda.
 * @param onMoveCharacterDown A lambda that is invoked when the action to move a character down is triggered. Default is an empty lambda.
 * @param contentPadding Optional padding values for the content inside the LazyColumn. Default is no padding.
 * @param itemContent A lambda that defines the content of each item in the list. It takes in the game character, item size, and three lambdas for click, move up, and move down actions.
 */
@Composable
fun ItemList(
    list: List<GameCharacter>,
    itemSize: Float = CHARACTER_ITEM_HEIGHT_DEFAULT,
    contentPadding: PaddingValues? = null,
    itemContent: @Composable (gameCharacter: GameCharacter, itemSize: Float) -> Unit
) {

    LazyColumn(contentPadding = contentPadding ?: PaddingValues()) {

        items(list, key = { list -> list.id }) { gameCharacter ->
            itemContent(gameCharacter, itemSize)
        }

    }

}


@Preview()
@Composable
fun ItemListPrev() {
    HavenCompanionTheme() {

        val hero1 = GameCharacter.Hero(
            characterName = "Testing shit",
            colorInt = MaterialTheme.colorScheme.background.toArgb(),
            firstInitiative = 50,
        )
        val list = listOf(hero1)

        ItemList(
            list = list,
            itemSize = CHARACTER_ITEM_HEIGHT_DEFAULT,
            contentPadding = null
        ) { gameCharacter, _ ->
            InitiativeItem(
                gameCharacter = gameCharacter,
                itemSize = CHARACTER_ITEM_HEIGHT_DEFAULT,
                onItemClick = {})
        }

    }
}
