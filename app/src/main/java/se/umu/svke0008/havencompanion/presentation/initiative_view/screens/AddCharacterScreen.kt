package se.umu.svke0008.havencompanion.presentation.initiative_view.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterEntity
import se.umu.svke0008.havencompanion.data.mappers.GameCharacterMapperImpl.toDomain
import se.umu.svke0008.havencompanion.domain.actions.CharacterAction
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.utils.ValidationResult
import se.umu.svke0008.havencompanion.presentation.Screen
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs.EditCharacterDialog
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs.UnlockCharacterDialog
import se.umu.svke0008.havencompanion.presentation.initiative_view.composables.lists.CharacterList
import se.umu.svke0008.havencompanion.presentation.initiative_view.ui_events.CharacterOperationUIEvent
import se.umu.svke0008.havencompanion.presentation.initiative_view.ui_state.CharacterActionUIState
import se.umu.svke0008.havencompanion.presentation.util_components.ErrorScreen
import se.umu.svke0008.havencompanion.presentation.util_components.LoadingScreen
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme
import java.util.Locale


/**
 * A composable screen that allows users to add characters to a list. The screen displays three tabs: "Heroes", "Monsters", and "Custom".
 * Users can search for characters in the "Heroes" and "Monsters" tabs and select them to add to the current list.
 * The "Custom" tab does not support searching.
 * A floating action button (FAB) is displayed when there are selected characters, allowing users to clear the selection.
 *
 * @param modifier A [Modifier] applied to the main [Column] containing the screen content.
 * @param heroResult A list of [GameCharacter.Hero] objects representing the available heroes.
 * @param monsterResult A list of [GameCharacter.Monster] objects representing the available monsters.
 * @param currentSelectedCharacters A list of [GameCharacter] objects representing the characters currently selected by the user.
 * @param onAddCharacterToScenario A lambda that is invoked when a character is added. It receives the [GameCharacter] as a parameter.
 * @param onRemoveCharacterFromScenario A lambda that is invoked when a character is removed. It receives the [GameCharacter] as a parameter.
 * @param clearCharacters A lambda that is invoked when the user clicks the "Clear character(s)" FAB.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun AddCharacterScreen(
    modifier: Modifier = Modifier,
    heroResult: CharacterActionUIState<List<GameCharacterEntity>> = CharacterActionUIState.Idle,
    monsterResult: CharacterActionUIState<List<GameCharacterEntity>> = CharacterActionUIState.Idle,
    searchText: String,
    onTextSearch:(String) -> Unit,
    currentSelectedCharacters: List<GameCharacter> = emptyList(),
    navController: NavController,
    onAddCharacterToScenario: (GameCharacter) -> Unit,
    saveClickedCharacter: (GameCharacterEntity) -> Unit,
    onUnlockCharacter: (GameCharacterEntity) -> Unit,
    onLockCharacter: (GameCharacterEntity) -> Unit,
    onRemoveCharacterFromScenario: (GameCharacter) -> Unit,
    validationResult: ValidationResult,
    clearCharacters: () -> Unit,
    onResetValidationResult: () -> Unit,
    onCharacterAction: (CharacterAction) -> Unit,
    characterOperationUIEvent: CharacterOperationUIEvent
) {


    val titles = listOf("Heroes", "Monsters")
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { titles.size }
    )

    val tabState = pagerState.currentPage

  //  var filterText by remember { mutableStateOf("") }

    var selectedContent by remember { mutableIntStateOf(0) }
    var active by rememberSaveable { mutableStateOf(false) }


    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val showSearchBar by remember {
        derivedStateOf(
            calculation = { titles[pagerState.currentPage] != "Custom" }
        )
    }

    var showEditCharacterDialog by remember { mutableStateOf(false) }
    var editCharacter: GameCharacterEntity? by remember { mutableStateOf(null) }



    LaunchedEffect(key1 = characterOperationUIEvent.timeStamp) {
        when (characterOperationUIEvent) {
            is CharacterOperationUIEvent.DeleteCharacterUIEvent.Error ->
                snackbarHostState.showSnackbar(characterOperationUIEvent.message)

            is CharacterOperationUIEvent.UpdateCharacterUIEvent.Error ->
                snackbarHostState.showSnackbar(characterOperationUIEvent.message)

            is CharacterOperationUIEvent.CreateCharacterUIEvent.Success ->
                snackbarHostState.showSnackbar(characterOperationUIEvent.message)

            is CharacterOperationUIEvent.DeleteCharacterUIEvent.Success -> {
                val action = snackbarHostState.showSnackbar(
                    message = characterOperationUIEvent.message,
                    actionLabel = "Undo",
                    duration = SnackbarDuration.Indefinite,
                    withDismissAction = true
                )
                if (action == SnackbarResult.ActionPerformed) {
                    onCharacterAction(CharacterAction.UndoDelete)
                }
            }

            is CharacterOperationUIEvent.UpdateCharacterUIEvent.Success -> {
                val action = snackbarHostState.showSnackbar(
                    message = characterOperationUIEvent.message,
                    actionLabel = "Undo",
                    duration = SnackbarDuration.Indefinite,
                    withDismissAction = true
                )
                if (action == SnackbarResult.ActionPerformed) {
                    onCharacterAction(CharacterAction.UndoEdit)
                }
            }

            else -> {}
        }
    }


    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Add character to scenario",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }, navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
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
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(Screen.CharacterScreen.CreateCharacterScreen.route) }) {
                Icon(Icons.Default.Add, "Create character")
            }

        },

        floatingActionButtonPosition = FabPosition.End
    ) { paddingValues ->
        Column(
            modifier = modifier.fillMaxSize().padding(paddingValues),
            horizontalAlignment = CenterHorizontally,
        ) {

            TabRow(selectedTabIndex = tabState) {
                titles.forEachIndexed { index, title ->
                    Tab(
                        selected = tabState == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                                selectedContent = index
                            }

                        },
                        text = { Text(text = title, style = MaterialTheme.typography.titleMedium) })
                }
            }

            AnimatedVisibility(visible = showSearchBar) {
                SearchBar(
                    modifier = Modifier.padding(bottom = 8.dp),
                    placeholder = { Text(text = "Search character") },
                    query = searchText,
                    onQueryChange = { onTextSearch(it)},
                    onSearch = { active = false },
                    active = active,
                    onActiveChange = { },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) }
                ) {
                }
            }

            HorizontalPager(
                state = pagerState,
            ) { page ->

                var showUnlockDialog by remember { mutableStateOf(false) }
                var unlockCharacter: GameCharacterEntity? by remember { mutableStateOf(null) }

                when (page) {
                    0 -> {
                        when (heroResult) {
                            is CharacterActionUIState.Error -> ErrorScreen(text = heroResult.message)
                            CharacterActionUIState.Loading -> LoadingScreen()
                            is CharacterActionUIState.Success -> {


                                CharacterList(
                                    paddingValues = PaddingValues(bottom = 84.dp),
                                    characterList = heroResult.data,
                                    currentSelectedCharacters = currentSelectedCharacters,
                                    onAddCharacterToScenario = {
                                        onAddCharacterToScenario(it.toDomain())
                                    },
                                    onRemoveCharacterFromScenario = {
                                        onRemoveCharacterFromScenario(it.toDomain())
                                    },
                                    onEditCharacter = {
                                        saveClickedCharacter(it)
                                        showEditCharacterDialog = true
                                        editCharacter = it
                                    },
                                    onUnlockCharacter = {
                                        showUnlockDialog = true
                                        unlockCharacter = it
                                    }
                                )

                                if (showUnlockDialog) {
                                    unlockCharacter?.let { character ->
                                        UnlockCharacterDialog(
                                            entity = character,
                                            onConfirm = {
                                                if (!it.revealed) {
                                                    onUnlockCharacter(character)
                                                } else {
                                                    onLockCharacter(character)
                                                }
                                                showUnlockDialog = false
                                            },
                                            onCancel = { showUnlockDialog = false })
                                    }
                                }
                            }

                            else -> {}
                        }
                    }


                    1 -> {
                        when (monsterResult) {
                            is CharacterActionUIState.Error -> ErrorScreen(text = monsterResult.message)
                            CharacterActionUIState.Loading -> LoadingScreen()
                            is CharacterActionUIState.Success -> {
                                CharacterList(
                                    paddingValues = PaddingValues(bottom = 84.dp),
                                    characterList = monsterResult.data,
                                    currentSelectedCharacters = currentSelectedCharacters,
                                    onAddCharacterToScenario = { onAddCharacterToScenario(it.toDomain()) },
                                    onRemoveCharacterFromScenario = {
                                        onRemoveCharacterFromScenario(it.toDomain())
                                    },
                                    onEditCharacter = {
                                        saveClickedCharacter(it)
                                        showEditCharacterDialog = true
                                        editCharacter = it
                                    },
                                    onUnlockCharacter = {}
                                )
                            }

                            else -> {}
                        }

                    }
                }
            }


        }

        if (showEditCharacterDialog) {
            editCharacter?.let { thisCharacter ->

                EditCharacterDialog(
                    character = thisCharacter,
                    onUpdate = { onCharacterAction(CharacterAction.UpdateCharacter(it)) },
                    onDelete = { onCharacterAction(CharacterAction.DeleteCharacter(it)) },
                    validationResult = validationResult,
                    onResetValidationResult = { onResetValidationResult() },
                    onDismiss = {
                        showEditCharacterDialog = it
                        editCharacter = null
                    }
                )

            }
        }

    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun AddCharacterPreview() {
    HavenCompanionTheme(havenFont = true, darkTheme = false) {
        AddCharacterScreen(
            navController = rememberNavController(),
            onAddCharacterToScenario = {},
            onUnlockCharacter = {},
            onLockCharacter = {},
            onRemoveCharacterFromScenario = {},
            onCharacterAction = {},
            clearCharacters = {},
            onResetValidationResult = {},
            validationResult = ValidationResult.Valid,
            saveClickedCharacter = {},
            characterOperationUIEvent = CharacterOperationUIEvent.NoEvent,
            onTextSearch = {},
            searchText = ""
        )
    }
}