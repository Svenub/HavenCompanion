package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import se.umu.svke0008.havencompanion.domain.model.game_character.GameCharacter
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme


/**
 * This dialog presents a list of heroes and allows the user to select the order in which they act.
 * The order is determined by the user's clicks on each hero's name. The dialog provides options to
 * save the selected order or cancel the operation.
 *
 * @param heroes A list of [GameCharacter.Hero] objects representing the heroes in conflict.
 * @param onSave A lambda that is invoked when the user decides to save the selected order. It provides the ordered list of heroes as a parameter.
 * @param onCancel A lambda that is invoked when the user decides to cancel the operation. It provides a boolean indicating if the operation was canceled.
 *
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeroConflictDialog(
    heroes: List<GameCharacter.Hero>,
    onSave: (List<GameCharacter.Hero>) -> Unit,
    onCancel: (Boolean) -> Unit
) {



    var initiativeOrder by remember { mutableIntStateOf(0) }
    var heroOrder: List<GameCharacter.Hero> by remember { mutableStateOf(heroes) }


    LaunchedEffect(heroes) {
        initiativeOrder = 0
        heroOrder = heroes
    }

    AlertDialog(
        onDismissRequest = {
            onCancel(false)
        }
    ) {


        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Choose the order of the heroes",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    textAlign = TextAlign.Center
                )
                Divider()

                heroOrder.forEachIndexed { index, monster ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier
                            .clickable {
                                if (heroOrder.all { it.onConflictOrder != null }) {
                                    heroOrder =
                                        heroOrder.map { it.copy(onConflictOrder = null) }
                                    initiativeOrder = 0
                                }
                                heroOrder = heroOrder.map {
                                    if (it.characterName == heroOrder[index].characterName
                                        && heroOrder[index].onConflictOrder == null
                                    ) {
                                        initiativeOrder++
                                        it.copy(onConflictOrder = initiativeOrder)
                                    } else {
                                        it
                                    }
                                }


                            }
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {

                        Text(text = monster.characterName)

                        monster.onConflictOrder?.let {
                            Text(text = it.toString())
                        }
                    }
                }

                Divider()
                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TextButton(
                        onClick = {
                            onCancel(false)
                        },
                    ) {
                        Text("Cancel")
                    }

                    TextButton(
                        enabled = heroOrder.all { it.onConflictOrder != null },
                        onClick = {
                            onSave(heroOrder)
                        },
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun HeroConflictPrev() {
    HavenCompanionTheme {
        HeroConflictDialog(
            heroes = emptyList(),
            onSave = {},
            onCancel = {}
        )
    }
}