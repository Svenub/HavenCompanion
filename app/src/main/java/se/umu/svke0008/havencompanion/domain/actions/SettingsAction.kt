package se.umu.svke0008.havencompanion.domain.actions

import se.umu.svke0008.havencompanion.ui.theme.ColorTheme

sealed class SettingsAction {
    data class ToggleHavenFont(val value: Boolean) : SettingsAction()
    data class ToggleDarkMode(val value: Boolean) : SettingsAction()
    data class TogglePartialResultsDialog(val value: Boolean) : SettingsAction()
    data class ToggleDynamicColor(val value: Boolean) : SettingsAction()
    data class ChangeThemeColor(val theme: ColorTheme) : SettingsAction()
    data class KeepScreenOn(val value: Boolean) : SettingsAction()

}
