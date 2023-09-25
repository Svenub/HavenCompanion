package se.umu.svke0008.havencompanion.data.local.settings

import se.umu.svke0008.havencompanion.ui.theme.ColorTheme

data class SettingState(
    val darkModeEnable: Boolean = false,
    val havenFontEnabled: Boolean = true,
    val keepScreenOn: Boolean = false,
    val showPartialResults: Boolean = true,
    val dynamicColor: Boolean = false,
    val colorTheme: ColorTheme = ColorTheme.THEME_1
)

