package se.umu.svke0008.havencompanion.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import se.umu.svke0008.havencompanion.data.local.settings.SettingState
import se.umu.svke0008.havencompanion.data.mappers.GameCharacterMapperImpl.toDomain
import se.umu.svke0008.havencompanion.domain.actions.CharacterAction
import se.umu.svke0008.havencompanion.domain.actions.EnhancementAction
import se.umu.svke0008.havencompanion.domain.actions.ScenarioAction
import se.umu.svke0008.havencompanion.domain.utils.ValidationResult
import se.umu.svke0008.havencompanion.presentation.enhancement_view.screens.EnhancementScreen
import se.umu.svke0008.havencompanion.presentation.initiative_view.screens.AddCharacterScreen
import se.umu.svke0008.havencompanion.presentation.initiative_view.screens.CreateCharacterScreen
import se.umu.svke0008.havencompanion.presentation.initiative_view.screens.InitiativeScreen
import se.umu.svke0008.havencompanion.presentation.initiative_view.ui_events.CharacterOperationUIEvent
import se.umu.svke0008.havencompanion.presentation.settings_view.screen.SettingsScreen
import se.umu.svke0008.havencompanion.presentation.viewmodel.CharacterViewModel
import se.umu.svke0008.havencompanion.presentation.viewmodel.EnhancementViewModel
import se.umu.svke0008.havencompanion.presentation.viewmodel.ScenarioViewModel
import se.umu.svke0008.havencompanion.presentation.viewmodel.SettingsViewModel
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme


/**
 * Represents the main framework that holds every screen. The main content
 * area is managed by a NavHost that allows navigating between different screens such as InitiativeScreen,
 * AddCharacterScreen, and SettingsScreen.
 *
 * @param havenFontEnabled A Boolean indicating if the Haven font is enabled.
 * @param darkModeEnabled A Boolean indicating if the dark mode is enabled.
 * @param enableHavenFont A lambda function to enable or disable the Haven font.
 * @param enableDarkMode A lambda function to enable or disable the dark mode.
 * @param scenarioViewModel An instance of ScenarioViewModel to manage scenario-related data and actions. Defaults to hiltViewModel().

 *
 * The InitiativeScreen displays the characters in play, their order, and other related information.
 * The AddCharacterScreen allows users to add or remove characters. The SettingsScreen provides options
 * to enable or disable the Haven font and dark mode.
 */
@Composable
fun MainScreen(settingsViewModel: SettingsViewModel, settingState: SettingState) {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route ?: Screen.InitiativeScreen.route

    val scope = rememberCoroutineScope()
    //val appBarState = rememberAppBarState(navController = navController)

   // val settingsViewModel: SettingsViewModel = hiltViewModel()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val darkModeEnabled = settingState.darkModeEnable

    val havenFontEnabled = settingState.havenFontEnabled
    val dynamicColorEnabled = settingState.dynamicColor
    val colorTheme = settingState.colorTheme

    HavenCompanionTheme(
        havenFont = havenFontEnabled,
        enableDarkMode = darkModeEnabled,
        dynamicColor = dynamicColorEnabled,
        colorTheme = colorTheme
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            tonalElevation = 5.dp
        ) {


            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    DrawerSheet(
                        currentRoute = currentDestination,
                        onItemClick = {
                            scope.launch {
                                drawerState.close()
                                navController.navigate(it.route)
                            }

                        }
                    )
                },
                content = {
                    NavHost(
                        navController = navController,
                        startDestination = Screen.InitiativeScreen.route
                    ) {


                        composable(
                            route = Screen.InitiativeScreen.route,
                        ) { entry ->

                            InitiativeScreen(
                                scenarioViewModel = hiltViewModel(entry),
                                settingsViewModel = hiltViewModel(entry),
                                navController = navController,
                                onNavigationClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            )
                        }
                        composable(route = Screen.EnhancementScreen.route) {
                            val enhancementViewModel = it.sharedViewModel<EnhancementViewModel>(navController)
                            val ruleState by enhancementViewModel.enhancementRuleState.collectAsStateWithLifecycle()
                            val enhancementList by enhancementViewModel.getCalculatedEnhancements.collectAsStateWithLifecycle()
                            val filterSortState by enhancementViewModel.enhancementSortFilterState.collectAsStateWithLifecycle()

                            EnhancementScreen(
                                enhancementRuleState = ruleState,
                                enhancementList = enhancementList,
                                filterSortState = filterSortState,
                                onCurrentGold = {
                                    enhancementViewModel.onEvent(
                                        EnhancementAction.ApplyCurrentGold(
                                            it
                                        )
                                    )
                                },
                                onEnhancementStateAction = { newState ->
                                    enhancementViewModel.onEvent(newState)
                                },
                                onNavigationClick = {
                                    scope.launch {
                                        drawerState.open()
                                    }
                                }
                            )
                        }

                        navigation(
                            route = Screen.CharacterScreen.route,
                            startDestination = Screen.CharacterScreen.AddCharacterScreen.route

                        ) {


                            composable(
                                route = Screen.CharacterScreen.AddCharacterScreen.route,
                            ) { entry ->
                          /*      val characterViewModel: CharacterViewModel =
                                    entry.sharedViewModel(navController)
                                val scenarioViewModel: ScenarioViewModel =
                                    entry.sharedViewModel(navController)*/

                                val characterViewModel: CharacterViewModel = hiltViewModel(entry)
                                val scenarioViewModel: ScenarioViewModel = hiltViewModel(entry)


                                val heroResult by
                                characterViewModel.heroEntities.collectAsStateWithLifecycle()
                                val monsterResult by
                                characterViewModel.monsterEntities.collectAsStateWithLifecycle()
                                val currentSelectedCharacters by
                                scenarioViewModel.allGameCharacter.collectAsStateWithLifecycle()

                                val validationResult by
                                characterViewModel.validationResult.collectAsStateWithLifecycle(
                                    initialValue = ValidationResult.Initial
                                )

                                val characterOperationUIEvent by
                                characterViewModel.characterOperationUIEvent.collectAsStateWithLifecycle(
                                    initialValue = CharacterOperationUIEvent.NoEvent
                                )

                                val searchText by characterViewModel.searchText.collectAsStateWithLifecycle()

                                AddCharacterScreen(
                                    heroResult = heroResult,
                                    monsterResult = monsterResult,
                                    currentSelectedCharacters = currentSelectedCharacters,
                                    onAddCharacterToScenario = {
                                        scenarioViewModel.onAction(ScenarioAction.AddCharacter(it))
                                    },
                                    onRemoveCharacterFromScenario = {
                                        scenarioViewModel.onAction(ScenarioAction.DeleteCharacter(it))
                                    },
                                    clearCharacters = {
                                        scenarioViewModel.onAction(ScenarioAction.ClearCharacters)
                                    },
                                    navController = navController,
                                    onUnlockCharacter = {
                                        characterViewModel.onAction(
                                            CharacterAction.UnLockCharacter(
                                                it
                                            )
                                        )
                                    },
                                    onLockCharacter = {
                                        characterViewModel.onAction(CharacterAction.LockCharacter(it))
                                    },
                                    validationResult = validationResult,
                                    onCharacterAction = {
                                        characterViewModel.onAction(it)
                                        if (it is CharacterAction.DeleteCharacter) {
                                            scenarioViewModel.onAction(
                                                ScenarioAction.DeleteCharacter(
                                                    it.character.toDomain()
                                                )
                                            )
                                        }
                                    },
                                    onResetValidationResult = { characterViewModel.resetValidationResult() },
                                    saveClickedCharacter = { characterViewModel.storeCharacter(it) },
                                    characterOperationUIEvent = characterOperationUIEvent,
                                    searchText = searchText,
                                    onTextSearch = { characterViewModel.updateSearchText(it) }

                                )
                            }

                            composable(route = Screen.CharacterScreen.CreateCharacterScreen.route) { entry ->
                                val characterViewModel =
                                    entry.sharedViewModel<CharacterViewModel>(navController)


                                val validationResult by
                                characterViewModel.validationResult.collectAsStateWithLifecycle(
                                    initialValue = ValidationResult.Initial
                                )
                                val characterOperationUIEvent by characterViewModel.characterOperationUIEvent
                                    .collectAsStateWithLifecycle(initialValue = CharacterOperationUIEvent.NoEvent)


                                LaunchedEffect(key1 = characterOperationUIEvent) {
                                    if (characterOperationUIEvent
                                                is CharacterOperationUIEvent.CreateCharacterUIEvent.Success
                                    ) {
                                        navController.popBackStack()
                                    }
                                }

                                CreateCharacterScreen(
                                    validationResult = validationResult,
                                    onResetValidationResult = { characterViewModel.resetValidationResult() },
                                    onSave = {
                                        characterViewModel.onAction(
                                            CharacterAction.CreateCharacter(
                                                it
                                            )
                                        )
                                    },
                                    onSaveResult = characterOperationUIEvent,
                                    navController = navController
                                )
                            }
                        }


                        composable(
                            route = Screen.SettingsSreen.route,
                        ) {
                            SettingsScreen(
                                viewModel = settingsViewModel,
                                navController = navController
                            )
                        }

                    }

                }
            )

        }
    }

}


@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(
    navController: NavHostController,
): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    HavenCompanionTheme {
        // MainScreen()
    }
}