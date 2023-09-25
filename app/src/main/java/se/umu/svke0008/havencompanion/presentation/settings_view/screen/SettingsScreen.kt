package se.umu.svke0008.havencompanion.presentation.settings_view.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import se.umu.svke0008.havencompanion.data.local.settings.SettingState
import se.umu.svke0008.havencompanion.data.utils.DataResult
import se.umu.svke0008.havencompanion.domain.actions.SettingsAction
import se.umu.svke0008.havencompanion.presentation.settings_view.composables.SwitchSetting
import se.umu.svke0008.havencompanion.presentation.settings_view.composables.ThemeColorSetting
import se.umu.svke0008.havencompanion.presentation.util_components.ErrorScreen
import se.umu.svke0008.havencompanion.presentation.util_components.LoadingScreen
import se.umu.svke0008.havencompanion.presentation.viewmodel.SettingsViewModel
import se.umu.svke0008.havencompanion.ui.padding
import se.umu.svke0008.havencompanion.ui.theme.ColorTheme
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

/**
 * A composable screen that displays settings options for the application.
 * The settings include options to enable a custom font (havenfont) and toggle dark mode.
 *
 * @param havenFontEnabled A boolean indicating if the havenfont is currently enabled.
 * @param darkModeEnabled A boolean indicating if dark mode is currently enabled.
 * @param enableHavenFont A lambda that toggles the havenfont setting on or off.
 * @param enableDarkMode A lambda that toggles the dark mode setting on or off.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SettingsScreen(
    havenFontEnabled: Boolean,
    darkModeEnabled: Boolean,
    dynamicColors: Boolean,
    keepScreenOn: Boolean,
    partialResultEnabled: Boolean,
    selectedTheme: ColorTheme,
    onBack: () -> Unit,
    onColorThemeSelect: (ColorTheme) -> Unit,
    enableHavenFont: (Boolean) -> Unit,
    enableDarkMode: (Boolean) -> Unit,
    enableDynamicColor: (Boolean) -> Unit,
    onKeepScreenOn: (Boolean) -> Unit,
    enablePartialResultsDialog: (Boolean) -> Unit,
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = { onBack.invoke() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
        ) {

            Box {
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(
                            start = MaterialTheme.padding.medium,
                            end = MaterialTheme.padding.medium
                        )
                ) {
                    Text(text = "Settings", style = MaterialTheme.typography.displayLarge)
                    Divider(
                        modifier = Modifier.padding(vertical = MaterialTheme.padding.small),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                    SwitchSetting(
                        title = "Enable havenfont",
                        isChecked = havenFontEnabled,
                        onCheckedChange = { enableHavenFont(it) },
                        checkedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        checkedTrackColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    SwitchSetting(
                        title = "Enable dark mode",
                        isChecked = darkModeEnabled,
                        onCheckedChange = { enableDarkMode(it) },
                        checkedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        checkedTrackColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    SwitchSetting(
                        title = "Enable dynamic colors",
                        isChecked = dynamicColors,
                        onCheckedChange = { enableDynamicColor(it) },
                        checkedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        checkedTrackColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    SwitchSetting(
                        title = "Keep screen on (initiative)",
                        isChecked = keepScreenOn,
                        onCheckedChange = { onKeepScreenOn(it) },
                        checkedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        checkedTrackColor = MaterialTheme.colorScheme.onPrimaryContainer)

                    SwitchSetting(
                        title = "Show partial result dialog",
                        isChecked = partialResultEnabled,
                        onCheckedChange = { enablePartialResultsDialog(it) },
                        checkedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        checkedTrackColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )


                    Text(
                        text = "Themes",
                        style = MaterialTheme.typography.headlineLarge,
                        modifier = Modifier.padding(vertical = MaterialTheme.padding.medium)
                    )

                    Divider(
                        modifier = Modifier.padding(vertical = MaterialTheme.padding.small),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )

                    ThemeColorSetting(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        selectedTheme = selectedTheme,
                        onSelectTheme = { onColorThemeSelect(it) })
                }
            }
        }
    }


}

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navController: NavController
) {
    val settingsResult by viewModel.settingsState.collectAsStateWithLifecycle()

    when(settingsResult) {
        is DataResult.Failure -> ErrorScreen()
        DataResult.Loading -> LoadingScreen()
        is DataResult.Success -> {
            val settingsState = (settingsResult as DataResult.Success<SettingState>).data

            SettingsScreen(
                havenFontEnabled = settingsState.havenFontEnabled,
                darkModeEnabled = settingsState.darkModeEnable,
                dynamicColors = settingsState.dynamicColor,
                partialResultEnabled = settingsState.showPartialResults,
                selectedTheme = settingsState.colorTheme,
                enableHavenFont = {
                    viewModel.onAction(
                        SettingsAction.ToggleHavenFont(it)
                    )
                },
                enableDarkMode = {
                    viewModel.onAction(
                        SettingsAction.ToggleDarkMode(it)
                    )
                },
                keepScreenOn = settingsState.keepScreenOn,
                enablePartialResultsDialog = {
                    viewModel.onAction(
                        SettingsAction.TogglePartialResultsDialog(it)
                    )
                },
                enableDynamicColor = {
                    viewModel.onAction(
                        SettingsAction.ToggleDynamicColor(it)
                    )
                },
                onColorThemeSelect = { viewModel.onAction(SettingsAction.ChangeThemeColor(it)) },
                onKeepScreenOn = { viewModel.onAction(SettingsAction.KeepScreenOn(it)) },
                onBack = { navController.popBackStack() }
            )

        }
    }




}


@Preview(showBackground = true)
@Composable
fun SettingsPrev() {
    HavenCompanionTheme(darkTheme = true) {
        SettingsScreen(
            havenFontEnabled = true,
            darkModeEnabled = true,
            partialResultEnabled = false,
            enableHavenFont = {},
            enableDarkMode = {},
            enablePartialResultsDialog = {},
            dynamicColors = true,
            enableDynamicColor = { },
            selectedTheme = ColorTheme.THEME_1,
            onColorThemeSelect = {},
            onBack = {},
            onKeepScreenOn = {},
            keepScreenOn = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPrev2() {
    HavenCompanionTheme(darkTheme = false) {
        SettingsScreen(
            havenFontEnabled = true,
            darkModeEnabled = true,
            partialResultEnabled = false,
            enableHavenFont = {},
            enableDarkMode = {},
            enablePartialResultsDialog = {},
            dynamicColors = true,
            enableDynamicColor = { },
            selectedTheme = ColorTheme.THEME_1,
            onColorThemeSelect = {},
            onBack = {},
            onKeepScreenOn = {},
            keepScreenOn = false
        )
    }
}