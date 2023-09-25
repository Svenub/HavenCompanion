package se.umu.svke0008.havencompanion.presentation.util_components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp


@Composable
fun EllipsisText(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: Float,
    textColor: Color,
    charactersLeftBeforeEllipse: (Int) -> Unit
) {
    var truncatedText by remember { mutableStateOf(text) }
    var isTruncated by remember { mutableStateOf(false) }

    Text(
        modifier = modifier,
        text = truncatedText,
        fontSize = fontSize.sp,
        maxLines = 1,
        softWrap = false,
        onTextLayout = { result ->
            if (result.didOverflowWidth && !isTruncated) {
                val visibleWidth = result.size.width
                val visibleEndOffset = result.getOffsetForPosition(
                    Offset((visibleWidth - 1).toFloat(), 0F))
                truncatedText = text.take(visibleEndOffset - 3) + "..."
                charactersLeftBeforeEllipse(visibleEndOffset - 3)
                isTruncated = true
            }
        }
    )
}
