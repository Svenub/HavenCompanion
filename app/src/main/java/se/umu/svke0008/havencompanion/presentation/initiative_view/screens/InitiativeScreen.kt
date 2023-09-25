package se.umu.svke0008.havencompanion.presentation.initiative_view.screens

import android.Manifest
import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch
import se.umu.svke0008.havencompanion.R
import se.umu.svke0008.havencompanion.data.local.settings.SettingState
import se.umu.svke0008.havencompanion.data.utils.DataResult
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.model.scenario.InitiativeState
import se.umu.svke0008.havencompanion.domain.actions.ScenarioAction
import se.umu.svke0008.havencompanion.domain.actions.SettingsAction
import se.umu.svke0008.havencompanion.presentation.Screen
import se.umu.svke0008.havencompanion.presentation.initiative_view.InitiativeScreenConstants.CHARACTER_ITEM_HEIGHT_DEFAULT
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.controllers.AnimationFab
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.controllers.ExtendedFabActions
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs.HeroConflictDialog
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs.ManualInitiativeDialog
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs.MicrophonePermissionDialog
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs.MonsterConflictDialog
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs.NewRoundInitiativeDialog
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs.PartialResultsDialog
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs.SecondInitiativeDialog
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.game_character.ExhaustedItem
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.game_character.InitiativeItem
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.game_character.ItemList
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.game_character.ReorderList
import se.umu.svke0008.havencompanion.presentation.initiative_view.ui_events.ScenarioUIEvent
import se.umu.svke0008.havencompanion.presentation.initiative_view.ui_state.InitiativeUiState
import se.umu.svke0008.havencompanion.presentation.util_components.UiUtils
import se.umu.svke0008.havencompanion.presentation.viewmodel.ScenarioViewModel
import se.umu.svke0008.havencompanion.presentation.viewmodel.SettingsViewModel
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme


/**
 * A composable screen that displays the initiative order of game characters. The screen provides tabs for "In Play", "Exhausted", and "Order".
 * Users can manually set the initiative for characters, reorder them, and handle conflicts when multiple characters have the same initiative.
 * The screen also supports voice activation for setting initiative and provides feedback through snackbars.
 *
 * @param modifier A [Modifier] applied to the main [Column] containing the screen content.
 * @param characterOrder A list of [GameCharacter] objects representing the order of characters.
 * @param charactersInPlay A list of [GameCharacter] objects representing the characters currently in play.
 * @param exhaustedCharacters A list of [GameCharacter] objects representing the characters that are exhausted.
 * @param initiativeState The current state of the initiative, which determines the UI feedback and dialogs shown.
 * @param toggleVoiceActivation A lambda that toggles voice activation on or off.
 * @param voiceActive A boolean indicating if voice activation is currently active.
 * @param navController An optional [NavController] for navigating between composables.
 * @param onScenarioAction A lambda that handles UI events such as initiative changes, character movements, and voice commands.
 * @param isListening A boolean indicating if the app is currently listening for voice commands.
 * @param partialResult A string representing the partial result of voice recognition.
 */
@OptIn(
    ExperimentalFoundationApi::class, ExperimentalPermissionsApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun InitiativeScreen(
    modifier: Modifier = Modifier,
    title: String,
    onNavigationClick: () -> Unit,
    characterOrder: List<GameCharacter>,
    charactersInPlay: List<GameCharacter>,
    exhaustedCharacters: List<GameCharacter>,
    initiativeState: InitiativeState,
    scenarioUIEvent: ScenarioUIEvent,
    currentPendingIndex: Int,
    toggleVoiceActivation: (Boolean) -> Unit,
    voiceActive: Boolean,
    showPartialResultDialogSetting: Boolean,
    togglePartialResultSetting: (Boolean) -> Unit,
    navController: NavController? = null,
    onScenarioAction: (ScenarioAction) -> Unit = {},
    isListening: Boolean,
    partialResult: String
) {


    val recordPermissionState = rememberPermissionState(Manifest.permission.RECORD_AUDIO)
    val snackbarHostState = remember { SnackbarHostState() }


    val titles = InitiativeUiState.values()
    var showInitiativeDialog by remember { mutableStateOf(false) }
    var dismissPartialDialog by remember { mutableStateOf(false) }

    var showMicrophonePermissionDialog by remember { mutableStateOf(false) }
    var currentSelectedCharacter: GameCharacter? by remember { mutableStateOf(null) }


    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { titles.size }
    )

    val tabState = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()


    if (showMicrophonePermissionDialog) {
        MicrophonePermissionDialog(
            showDialog = { showMicrophonePermissionDialog = false },
            permissionState = recordPermissionState,
            toggleVoiceActivation = {
                toggleVoiceActivation(it)
                showMicrophonePermissionDialog = false
            }
        )
    }


    var showPartialResultDialog by remember { mutableStateOf(false) }

    LaunchedEffect(key1 = isListening, key2 = dismissPartialDialog) {
        showPartialResultDialog =
            showPartialResultDialogSetting && isListening && !dismissPartialDialog
    }

    LaunchedEffect(key1 = scenarioUIEvent) {
        when (scenarioUIEvent) {
            is ScenarioUIEvent.Initial -> {}
            is ScenarioUIEvent.Canceled -> {
                val action = snackbarHostState.showSnackbar(
                    message = scenarioUIEvent.message,
                    actionLabel = "Reset",
                    duration = SnackbarDuration.Indefinite,
                    withDismissAction = true
                )
                if (action == SnackbarResult.ActionPerformed) {
                    onScenarioAction(ScenarioAction.RestoreInitiative)
                }
            }

            is ScenarioUIEvent.Done -> snackbarHostState.showSnackbar(
                message = scenarioUIEvent.message,
                duration = SnackbarDuration.Long
            )
        }
    }


    if (showPartialResultDialog) {
        PartialResultsDialog(
            partialResult = partialResult,
            showDialog = {
                togglePartialResultSetting(it)
                dismissPartialDialog = true
            })
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = title,
                        maxLines = 1,
                        style = MaterialTheme.typography.headlineLarge,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigationClick) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { navController?.navigate(Screen.CharacterScreen.route) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_person_add_alt_1_24),
                            contentDescription = "Add character to scenario",
                        )
                    }
                    IconToggleButton(
                        checked = voiceActive,
                        colors = IconButtonDefaults.iconToggleButtonColors(
                            checkedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                            checkedContentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        onCheckedChange = {

                            if (recordPermissionState.hasPermission)
                                toggleVoiceActivation(it)
                            else
                                showMicrophonePermissionDialog = true
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_mic_24),
                            contentDescription = "Toggle microphone",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        floatingActionButton = {
            AnimationFab(
                primaryText = "New round",
                showMoreControls = voiceActive,
                isListening = isListening,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
                onExtendedFabActions = {
                    when (it) {
                        ExtendedFabActions.ByName -> onScenarioAction(ScenarioAction.StartVoiceByName)
                        ExtendedFabActions.ByOrder -> onScenarioAction(ScenarioAction.StartVoiceByOrder)
                    }
                    dismissPartialDialog = false
                },
                stopListening = { onScenarioAction(ScenarioAction.StopListening) },
                onNewRound = { onScenarioAction(ScenarioAction.NewRound) }
            )
        }

    ) { paddingValues ->

        Column(modifier = modifier.padding(paddingValues)) {
            TabRow(selectedTabIndex = tabState) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = tabState == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }

                        },
                        text = {
                            Text(
                                text = title.getName(),
                                style = MaterialTheme.typography.titleMedium,
                            )
                        })
                }
            }
            HorizontalPager(
                state = pagerState,
            ) { currentPage ->
                Box(
                    modifier = Modifier
                        .background(
                            MaterialTheme.colorScheme.background,
                            MaterialTheme.shapes.extraSmall
                        )
                        .fillMaxHeight()

                ) {
                    when (titles[currentPage]) {

                        InitiativeUiState.In_play ->
                            ItemList(
                                list = charactersInPlay,
                                itemSize = CHARACTER_ITEM_HEIGHT_DEFAULT,
                                contentPadding = null
                            ) { gameCharacter, _ ->
                                InitiativeItem(
                                    gameCharacter = gameCharacter,
                                    itemSize = CHARACTER_ITEM_HEIGHT_DEFAULT,
                                    onItemClick = {
                                        currentSelectedCharacter = it
                                        showInitiativeDialog = true
                                    })
                            }

                        InitiativeUiState.Exhausted ->
                            ItemList(
                                list = exhaustedCharacters,
                                itemSize = CHARACTER_ITEM_HEIGHT_DEFAULT,
                                contentPadding = null
                            ) { gameCharacter, _ ->
                                ExhaustedItem(
                                    gameCharacter = gameCharacter,
                                    itemSize = CHARACTER_ITEM_HEIGHT_DEFAULT,
                                    onItemClick = { onScenarioAction(ScenarioAction.ToggleExhaust(it)) }
                                )
                            }

                        InitiativeUiState.Order ->
                            ReorderList(
                                characterOrder = characterOrder,
                                contentPadding = null,
                                onMoveCharacterDown = {
                                    onScenarioAction(
                                        ScenarioAction.MoveDownCharacter(
                                            it
                                        )
                                    )
                                },
                                onMoveCharacterUp = {
                                    onScenarioAction(
                                        ScenarioAction.MoveUpCharacter(
                                            it
                                        )
                                    )
                                },
                                onDragCharacter = { fromPos, toPos ->
                                    onScenarioAction(ScenarioAction.DragCharacter(fromPos, toPos))
                                }
                            )
                    }
                }

            }

        }

    }

    when (isListening) {
        true -> {}
        false -> when (initiativeState) {

            is InitiativeState.HeroConflict -> {
                HeroConflictDialog(
                    heroes = initiativeState.conflictingHeroes,
                    onSave = { onScenarioAction(ScenarioAction.MultipleInitiativeChange(it)) },
                    onCancel = {
                        onScenarioAction(ScenarioAction.CancelInitiative)
                    }
                )
            }

            is InitiativeState.MonsterConflict -> MonsterConflictDialog(
                monsters = initiativeState.conflictingMonsters,
                onSave = { onScenarioAction(ScenarioAction.MultipleInitiativeChange(it)) },
                onCancel = {
                    onScenarioAction(ScenarioAction.CancelInitiative)
                }
            )

            is InitiativeState.LongRestConflict ->
                HeroConflictDialog(
                    heroes = initiativeState.conflictingHeroes,
                    onSave = { onScenarioAction(ScenarioAction.MultipleInitiativeChange(it)) },
                    onCancel = {
                        onScenarioAction(ScenarioAction.CancelInitiative)
                    }
                )

            is InitiativeState.Pending ->
                NewRoundInitiativeDialog(
                    gameCharacter = initiativeState.pendingCharacters[currentPendingIndex],
                    clearFocus = false,
                    onCharacterInitiativeUpdated = {
                        onScenarioAction(ScenarioAction.InitiativeChange(it))
                    },
                    previousCharacter = { onScenarioAction(ScenarioAction.PreviousCharacter) },
                    nextCharacter = { onScenarioAction(ScenarioAction.NextCharacter) },
                    onCancel = {
                        onScenarioAction(ScenarioAction.CancelInitiative)
                    }
                )

            is InitiativeState.SetSecondInitiative ->
                SecondInitiativeDialog(
                    gameCharacter = initiativeState.gameCharacter,
                    onCharacterInitiativeUpdated = {
                        onScenarioAction(ScenarioAction.InitiativeChange(it))
                    },
                    onCancel = {
                        onScenarioAction(ScenarioAction.CancelInitiative)
                    }
                )

            else -> {}
        }
    }

    if (showInitiativeDialog) {
        currentSelectedCharacter?.let { gameCharacter ->
            ManualInitiativeDialog(
                gameCharacter = gameCharacter,
                showDialog = { showDialog ->
                    showInitiativeDialog = showDialog
                },
                onSave = { onScenarioAction(ScenarioAction.InitiativeChange(it)) })
        }
    }
}

@Composable
fun InitiativeScreen(
    scenarioViewModel: ScenarioViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    navController: NavController,
    onNavigationClick: () -> Unit
) {

    val settingState by settingsViewModel.settingsState.collectAsStateWithLifecycle()
    val characterOrder by scenarioViewModel.allGameCharacter.collectAsStateWithLifecycle()
    val charactersInPlay by scenarioViewModel.charactersInPlay.collectAsStateWithLifecycle()
    val exhaustedCharacters by scenarioViewModel.exhaustedCharacters.collectAsStateWithLifecycle()
    val initiativeState by scenarioViewModel.initiativeState.collectAsStateWithLifecycle(
        InitiativeState.Initial
    )
    val scenarioUIEvent by scenarioViewModel.scenarioUIEvent.collectAsStateWithLifecycle(
        initialValue = ScenarioUIEvent.Initial
    )
    val currentPendingIndex by scenarioViewModel.currentCharacterIndex.collectAsStateWithLifecycle()
    val voiceActive by scenarioViewModel.voiceActive.collectAsStateWithLifecycle()
    val isListening by scenarioViewModel.isListening.collectAsStateWithLifecycle()
    val partialResult by scenarioViewModel.partialSpeechResult.collectAsStateWithLifecycle()


    when (settingState) {
        is DataResult.Success -> if ((settingState as DataResult.Success<SettingState>).data.keepScreenOn) {
            UiUtils.KeepScreenOn()
        }

        else -> {}
    }

    InitiativeScreen(
        title = Screen.InitiativeScreen.title,
        onNavigationClick = {
            onNavigationClick()
        },
        characterOrder = characterOrder,
        charactersInPlay = charactersInPlay,
        exhaustedCharacters = exhaustedCharacters,
        initiativeState = initiativeState,
        scenarioUIEvent = scenarioUIEvent,
        currentPendingIndex = currentPendingIndex,
        navController = navController,
        onScenarioAction = { scenarioAction -> scenarioViewModel.onAction(scenarioAction) },
        toggleVoiceActivation = { scenarioViewModel.toggleVoice(it) },
        voiceActive = voiceActive,
        isListening = isListening,
        partialResult = partialResult,
        showPartialResultDialogSetting =
        if (settingState is DataResult.Success)
                (settingState as DataResult.Success<SettingState>).data.showPartialResults
        else true,
        togglePartialResultSetting = {
            settingsViewModel.onAction(SettingsAction.TogglePartialResultsDialog(it))
        }
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)

@Composable
fun Preview() {
    HavenCompanionTheme {

        val hero1 = GameCharacter.Hero(
            characterName = "Testing shit",
            colorInt = MaterialTheme.colorScheme.background.toArgb(),
            firstInitiative = 50,
        )

        InitiativeScreen(navController = rememberNavController()) {

        }

        /*InitiativeScreen(
            title = "Initiative screen",
            characterOrder = emptyList(),
            charactersInPlay = listOf(hero1),
            exhaustedCharacters = emptyList(),
            toggleVoiceActivation = { },
            initiativeState = InitiativeState.Done,
            voiceActive = false,
            isListening = false,
            partialResult = "Test",
            currentPendingIndex = 0,
            onNavigationClick = {},
            togglePartialResultSetting = {},
            showPartialResultDialogSetting = true
        )*/
    }
}