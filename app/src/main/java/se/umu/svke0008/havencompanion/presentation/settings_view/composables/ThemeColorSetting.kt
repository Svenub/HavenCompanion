package se.umu.svke0008.havencompanion.presentation.settings_view.composables

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.umu.svke0008.havencompanion.ui.theme.ColorTheme
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

@Composable
fun ThemeColorSetting(
    modifier: Modifier = Modifier,
    selectedTheme: ColorTheme = ColorTheme.THEME_1,
    onSelectTheme: (ColorTheme) -> Unit,
    circleSize: Float = 70f
) {
    val themes = ColorTheme.values()
    val selectedColor = MaterialTheme.colorScheme.onPrimaryContainer

    Row(modifier = modifier) {
        themes.forEach { theme ->

            Column(horizontalAlignment = CenterHorizontally) {
                Canvas(
                    modifier = Modifier
                        .padding((circleSize / 2).dp)
                        .clickable {
                            onSelectTheme(theme)
                        }
                ) {
                    if (theme == selectedTheme) {
                        val ringWidth = circleSize / 4
                        drawCircle(
                            color = selectedColor,
                            radius = circleSize,
                            style = Stroke(width = ringWidth)
                        )
                    }

                    drawCircle(
                        color = theme.color,
                        radius = (circleSize)
                    )
                }
                Text(text = theme.themeName, style = MaterialTheme.typography.labelLarge)
            }

        }
    }
}


@Preview
@Composable
fun ThemeColorSettingsPRev() {
    HavenCompanionTheme {
        ThemeColorSetting(
            onSelectTheme = {},
            modifier = Modifier.fillMaxWidth(),
            circleSize = 70f
        )
    }
}