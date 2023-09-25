package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.game_character

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import org.burnoutcrew.reorderable.ItemPosition
import org.burnoutcrew.reorderable.detectReorder
import org.burnoutcrew.reorderable.draggedItem
import org.burnoutcrew.reorderable.rememberReorderState
import org.burnoutcrew.reorderable.reorderable
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.presentation.initiative_view.InitiativeScreenConstants

/**
 * A composable function that displays a list of game characters with the ability to reorder them.
 * The list supports drag-and-drop reordering, and each item in the list also has icons to move the character up or down.
 * Multi-touch detection is implemented to prevent reordering when multiple fingers are detected on the screen.
 *
 * @param modifier A [Modifier] applied to the main [LazyColumn] containing the list of characters.
 * @param characterOrder A list of [GameCharacter] objects representing the characters to be displayed in the order they should appear.
 * @param itemSize The size of the text displaying the character's name.
 * @param onMoveCharacterUp A lambda that is invoked when the move up icon of an item is clicked. It receives the [GameCharacter] as a parameter.
 * @param onMoveCharacterDown A lambda that is invoked when the move down icon of an item is clicked. It receives the [GameCharacter] as a parameter.
 * @param onDragCharacter A lambda that is invoked when an item is dragged and dropped to a new position. It receives two parameters: the original position (`fromPos`) and the new position (`toPos`) of the item.
 * @param contentPadding Optional padding to be applied to the content inside the [LazyColumn]. Default is no padding.
 */
@Composable
fun ReorderList(
    characterOrder: List<GameCharacter>,
    itemSize: Float = InitiativeScreenConstants.CHARACTER_ITEM_HEIGHT_DEFAULT,
    onMoveCharacterUp: (GameCharacter) -> Unit = {},
    onMoveCharacterDown: (GameCharacter) -> Unit = {},
    onDragCharacter: (fromPos: ItemPosition, toPos: ItemPosition) -> Unit,
    contentPadding: PaddingValues? = null,
) {
    val state = rememberReorderState()

    var multiTouchDetected by remember { mutableStateOf(false) }

    LazyColumn(
        state = state.listState,
        contentPadding = contentPadding ?: PaddingValues(),
        modifier = Modifier
            .reorderable(
                state = state,
                onMove = { fromPos, toPos -> onDragCharacter(fromPos, toPos) }
            )
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        val event = awaitPointerEvent()
                        multiTouchDetected = event.changes.size >= 2
                    }
                }
            }


    ) {
        items(characterOrder, key = { item -> item.characterName.hashCode() }) { gameCharacter ->

            val dragging = state.draggedKey == gameCharacter.characterName.hashCode()

            val scale = animateFloatAsState(if (dragging) 1.1f else 1.0f)
            val elevation = if (dragging) 8.dp else 0.dp

            var itemModifier = Modifier
                .draggedItem(state.offsetByKey(gameCharacter.characterName.hashCode()))
                .scale(scale.value)
                .shadow(elevation)

            if (!multiTouchDetected) {
                itemModifier = itemModifier.detectReorder(state)
            }

            OrderItem(
                modifier = itemModifier
                    .scale(scale.value)
                    .shadow(elevation),
                gameCharacter = gameCharacter,
                itemSize = itemSize,
                onItemClick = {},
                onMoveUp = { onMoveCharacterUp(it) },
                onMoveDown = { onMoveCharacterDown(it) }
            )
        }
    }

}
