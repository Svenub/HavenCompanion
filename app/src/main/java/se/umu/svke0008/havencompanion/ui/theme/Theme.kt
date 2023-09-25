package se.umu.svke0008.havencompanion.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import se.umu.svke0008.havencompanion.ui.LocalPadding
import se.umu.svke0008.havencompanion.ui.Padding
import se.umu.svke0008.havencompanion.ui.theme.ColorTheme.THEME_1
import se.umu.svke0008.havencompanion.ui.theme.ColorTheme.THEME_2
import se.umu.svke0008.havencompanion.ui.theme.ColorTheme.THEME_3

enum class ColorTheme(val themeName: String, val color: Color) {
    THEME_1(themeName = "Original", color = Color(0xFFF3B56A)),
    THEME_2(themeName = "Blue", color = Color(0xFF356FFF)),
    THEME_3(themeName = "Purple", color = Color(0xFF5100C6))
}

fun String.stringToColorTheme(): ColorTheme {
    return when {
        this == THEME_1.name -> THEME_1
        this == THEME_2.name -> THEME_2
        this == THEME_3.name -> THEME_3
        else -> throw IllegalArgumentException("Unknown color theme: $this")
    }
}


@Composable
fun HavenCompanionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorTheme: ColorTheme = THEME_1,
    dynamicColor: Boolean = true,  // Dynamic color is available on Android 12+
    havenFont: Boolean = true,
    enableDarkMode: Boolean = false,
    content: @Composable () -> Unit
) {
    val lightScheme = when(colorTheme) {
        THEME_1 -> LightColorScheme1
        THEME_2 -> LightColorScheme2
        THEME_3 -> LightColorScheme3
    }

    val darkScheme = when(colorTheme) {
        THEME_1 -> DarkColorScheme1
        THEME_2 -> DarkColorScheme2
        THEME_3 -> DarkColorScheme3
    }

    val colorScheme =
        when {
            (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) -> {
                val context = LocalContext.current
                if (enableDarkMode) dynamicDarkColorScheme(context)
                else dynamicLightColorScheme(context)
            }
            enableDarkMode -> darkScheme
            else -> lightScheme
        }

    val view = LocalView.current
    if (!view.isInEditMode) {

        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()

            window.navigationBarColor = colorScheme.surfaceColorAtElevation(5.dp).toArgb()
            WindowCompat
                .getInsetsController(window, view)
                .isAppearanceLightStatusBars = darkTheme
           // WindowCompat.setDecorFitsSystemWindows(window, false)
        }

    }

    CompositionLocalProvider(
        LocalPadding provides Padding()
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = if(havenFont) HavenTypography else Typography,
            content = content
        )
    }
}