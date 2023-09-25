package se.umu.svke0008.havencompanion.presentation.util_components

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.WindowManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import kotlin.math.pow

object UiUtils {

    private fun linearize(colorComponent: Float): Double {
        return if (colorComponent <= 0.03928) {
            colorComponent / 12.92
        } else {
            ((colorComponent + 0.055) / 1.055).pow(2.4)
        }
    }

    private fun calculateRelativeLuminance(color: Color): Double {
        val red = linearize(color.red)
        val green = linearize(color.green)
        val blue = linearize(color.blue)

        return 0.2126 * red + 0.7152 * green + 0.0722 * blue
    }


    private fun contrastRatio(foregroundColor: Color, backgroundColor: Color): Double {
        val foregroundLuminance = calculateRelativeLuminance(foregroundColor)
        val backgroundLuminance = calculateRelativeLuminance(backgroundColor)

        return if (foregroundLuminance > backgroundLuminance) {
            (foregroundLuminance + 0.05) / (backgroundLuminance + 0.05)
        } else {
            (backgroundLuminance + 0.05) / (foregroundLuminance + 0.05)
        }
    }

    fun getTextColorForBackground(backgroundColor: Color): Color {

        val blackContrastRatio = contrastRatio(Color.Black, backgroundColor)
        val whiteContrastRatio = contrastRatio(Color.White, backgroundColor)

        // If both colors have the same contrast ratio, prefer black
        return if (blackContrastRatio >= whiteContrastRatio) Color.Black else Color.White
    }

    fun filterAlphabeticWithSingleWhitespace(input: String): String {
        // Remove non-alphabetic characters except whitespace
        val filtered = input.filter { it.isLetter() || it == ' ' }

        // Replace multiple whitespaces with a single whitespace

        // Trim to remove leading and trailing whitespaces
        return Regex("\\s+").replace(filtered, " ")
    }

    fun filterAlphabeticWithSingleWhitespaceAndComma(input: String): String {
        // Allow alphabetic characters, whitespace, and commas
        val filtered = input.filter { it.isLetter() || it == ' ' || it == ',' }

        // Replace multiple whitespaces or commas with a single whitespace or comma respectively
        val replaced = Regex("\\s+").replace(filtered, " ")
        val singleComma = Regex(",+").replace(replaced, ",")

        return singleComma
    }

    fun parseHexStringToColor(colorString: String): Color {
        if (colorString.length != 9 || colorString[0] != '#') {
            throw IllegalArgumentException("Invalid color format")
        }

        val alpha = colorString.substring(1, 3).toInt(16)
        val red = colorString.substring(3, 5).toInt(16)
        val green = colorString.substring(5, 7).toInt(16)
        val blue = colorString.substring(7, 9).toInt(16)

        return Color(red, green, blue, alpha)
    }

    fun colorToARGBString(color: Color): String {
        val alpha = (color.alpha * 255).toInt().toString(16).padStart(2, '0')
        val red = (color.red * 255).toInt().toString(16).padStart(2, '0')
        val green = (color.green * 255).toInt().toString(16).padStart(2, '0')
        val blue = (color.blue * 255).toInt().toString(16).padStart(2, '0')

        return "#$alpha$red$green$blue"
    }

    @Composable
    fun KeepScreenOn() {
        val context = LocalContext.current
        DisposableEffect(Unit) {
            val window = context.findActivity()?.window
            window?.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            onDispose {
                window?.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            }
        }
    }

    private fun Context.findActivity(): Activity? {
        var context = this
        while (context is ContextWrapper) {
            if (context is Activity) return context
            context = context.baseContext
        }
        return null
    }

}