package se.umu.svke0008.havencompanion.data.repository


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import se.umu.svke0008.havencompanion.data.local.settings.SettingState
import se.umu.svke0008.havencompanion.data.utils.DataResult
import se.umu.svke0008.havencompanion.data.utils.toDataResult
import se.umu.svke0008.havencompanion.ui.theme.ColorTheme
import se.umu.svke0008.havencompanion.ui.theme.stringToColorTheme
import javax.inject.Inject


class PreferencesRepository @Inject constructor(private val preferenceDataStore: DataStore<Preferences>) {



    private suspend fun saveString(key: String, value: String) {
        try {
            val preferenceKey = stringPreferencesKey(key)
            preferenceDataStore.edit {
                it[preferenceKey] = value
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    private suspend fun saveBoolean(key: String, value: Boolean) {
        try {
            val preferenceKey = booleanPreferencesKey(key)

            preferenceDataStore.edit {
                it[preferenceKey] = value
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    suspend fun toggleDarkMode(value: Boolean) {
        saveBoolean(DARK_MODE_ENABLED, value)
    }

    suspend fun toggleHavenFont(value: Boolean) {
        saveBoolean(HAVEN_FONT_ENABLED, value)
    }

    suspend fun toggleShowPartialResultsDialog(value: Boolean) {
        saveBoolean(SHOW_PARTIAL_RESULTS, value)
    }

    suspend fun toggleDynamicColors(value: Boolean) {
        saveBoolean(DYNAMIC_COLOR_ENABLED, value)
    }

    suspend fun toggleScreenOn(value: Boolean) {
        saveBoolean(KEEP_SCREEN_ON, value)
    }

    suspend fun changeThemeColor(theme: ColorTheme) {
        when(theme) {
            ColorTheme.THEME_1 -> saveString(COLOR_THEME,theme.name)
            ColorTheme.THEME_2 -> saveString(COLOR_THEME,theme.name)
            ColorTheme.THEME_3 -> saveString(COLOR_THEME,theme.name)
        }
    }


    fun getSettings(): Flow<DataResult<SettingState>> = preferenceDataStore.data
        .map { settings ->
            val defaultSettings = SettingState()
            SettingState(
                darkModeEnable = settings[booleanPreferencesKey(DARK_MODE_ENABLED)] ?: defaultSettings.darkModeEnable,
                havenFontEnabled = settings[booleanPreferencesKey(HAVEN_FONT_ENABLED)] ?: defaultSettings.havenFontEnabled,
                keepScreenOn = settings[booleanPreferencesKey(KEEP_SCREEN_ON)] ?: defaultSettings.keepScreenOn,
                showPartialResults = settings[booleanPreferencesKey(SHOW_PARTIAL_RESULTS)] ?: defaultSettings.showPartialResults,
                dynamicColor = settings[booleanPreferencesKey(DYNAMIC_COLOR_ENABLED)] ?: defaultSettings.dynamicColor,
                colorTheme = settings[stringPreferencesKey(COLOR_THEME)]?.stringToColorTheme() ?: defaultSettings.colorTheme
            )
        }.toDataResult()


    companion object {
        const val DARK_MODE_ENABLED = "DARK_MODE_ENABLED"
        const val HAVEN_FONT_ENABLED = "HAVEN FONT_ENABLED"
        const val KEEP_SCREEN_ON = "KEEP_SCREEN_ON"
        const val SHOW_PARTIAL_RESULTS = "SHOW_PARTIAL_RESULTS"
        const val DYNAMIC_COLOR_ENABLED = "DYNAMIC_COLOR_ENABLED"

        const val COLOR_THEME = "COLOR_THEME"

    }



}


