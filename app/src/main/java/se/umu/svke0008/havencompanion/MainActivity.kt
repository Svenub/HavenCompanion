package se.umu.svke0008.havencompanion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import se.umu.svke0008.havencompanion.data.local.settings.SettingState
import se.umu.svke0008.havencompanion.data.utils.DataResult
import se.umu.svke0008.havencompanion.presentation.MainScreen
import se.umu.svke0008.havencompanion.presentation.util_components.LoadingScreen
import se.umu.svke0008.havencompanion.presentation.viewmodel.SettingsViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val settingsViewModel: SettingsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                settingsViewModel.settingsState.value is DataResult.Loading
            }
        }
        setContent {
            val dataResult by settingsViewModel.settingsState.collectAsStateWithLifecycle()
            when(dataResult) {
                is DataResult.Success -> MainScreen(settingsViewModel, (dataResult as DataResult.Success<SettingState>).data)
                else -> LoadingScreen()
            }

          //  val windowSizeClass = calculateWindowSizeClass(this) // used when going tablets and larger screens
           // Log.d("onCreate", windowSizeClass.heightSizeClass.toString())
        }

    }
}


