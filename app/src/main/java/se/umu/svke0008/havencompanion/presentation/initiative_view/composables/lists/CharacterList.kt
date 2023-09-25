package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.lists

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterEntity
import se.umu.svke0008.havencompanion.data.mappers.GameCharacterMapperImpl.toDomain
import se.umu.svke0008.havencompanion.data.mappers.GameCharacterMapperImpl.toEntity
import se.umu.svke0008.havencompanion.data.mappers.JsonParser
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.domain.model.game_character.containsInList
import se.umu.svke0008.havencompanion.presentation.util_components.ResourceUtils
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

@Composable
fun CharacterList(
    paddingValues: PaddingValues,
    characterList: List<GameCharacterEntity>,
    currentSelectedCharacters: List<GameCharacter>,
    showIcon: Boolean = true,
    iconSize: Float = 48f,
    showBackGroundCircle: Boolean = true,
    backgroundCircleSize: Float = 50f,
    onAddCharacterToScenario: (GameCharacterEntity) -> Unit,
    onRemoveCharacterFromScenario: (GameCharacterEntity) -> Unit,
    onEditCharacter: (GameCharacterEntity) -> Unit,
    onUnlockCharacter: (GameCharacterEntity) -> Unit
) {
    val listState = rememberLazyListState()



    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            state = listState,
            contentPadding = paddingValues
        ) {
            items(characterList, key = { character -> character.characterName }) { gameCharacter ->

                var expanded by remember { mutableStateOf(false) }

                ListItem(
                    modifier = Modifier.clickable {
                        onUnlockCharacter(gameCharacter)
                    },
                    headlineContent = {
                        if (gameCharacter.revealed) {
                            Text(
                                text = gameCharacter.characterName,
                                style = MaterialTheme.typography.titleMedium
                            )
                        } else {
                            Text(
                                text = "Click to unlock character",
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    },
                    leadingContent = {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {


                            if (showBackGroundCircle) {
                                Canvas(modifier = Modifier.size(backgroundCircleSize.dp)) {
                                    drawCircle(color = Color(gameCharacter.colorInt))
                                }
                            }
                            if (showIcon) {
                                gameCharacter.icon?.let { iconName ->

                                    Icon(
                                        modifier = Modifier.size(iconSize.dp),
                                        painter = painterResource(
                                            id = ResourceUtils.getDrawableIdFromIconName(iconName)
                                        ), contentDescription = "Hero icon",
                                        tint = Color.Black
                                    )
                                }
                            }
                        }
                    },
                    trailingContent = {
                        if (gameCharacter.revealed) {
                            Row {
                                Checkbox(
                                    checked = currentSelectedCharacters.containsInList(gameCharacter.toDomain()),
                                    onCheckedChange = { checked ->
                                        if (checked)
                                            onAddCharacterToScenario(gameCharacter)
                                        else
                                            onRemoveCharacterFromScenario(gameCharacter)
                                    })

                                IconButton(onClick = { expanded = true }) {
                                    Icon(Icons.Default.MoreVert, contentDescription = "Edit")
                                }
                                DropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = { expanded = false }) {
                                    DropdownMenuItem(
                                        text = { Text(text = "Edit") },
                                        leadingIcon = {
                                            Icon(
                                                Icons.Default.Edit,
                                                contentDescription = "Edit character"
                                            )
                                        },

                                        onClick = {
                                            expanded = false
                                            onEditCharacter(gameCharacter)
                                        })
                                }
                            }
                        }


                    }
                )


                Divider()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun Prev() {

    val context = LocalContext.current

    val heroesJson = context.assets.open("heroes.json").bufferedReader().use { it.readText() }
    val heroesDto = JsonParser.parseGameCharactersFromJson(heroesJson)
    val heroes = heroesDto.map { it.toEntity() }

    val monsterJson = context.assets.open("monsters.json").bufferedReader().use { it.readText() }
    val monsterDto = JsonParser.parseGameCharactersFromJson(monsterJson)
    val monsters = monsterDto.map { it.toEntity() }


    HavenCompanionTheme() {
        CharacterList(
            paddingValues = PaddingValues(bottom = 0.dp),
            characterList = monsters,
            currentSelectedCharacters = emptyList(),
            onEditCharacter = {},
            onAddCharacterToScenario = {},
            onRemoveCharacterFromScenario = {},
            onUnlockCharacter = {}
        )
    }
}