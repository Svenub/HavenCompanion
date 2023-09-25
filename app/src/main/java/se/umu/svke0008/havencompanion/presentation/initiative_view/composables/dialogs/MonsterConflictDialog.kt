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
 * A composable function that displays a dialog allowing the user to choose the order of monsters for a conflict.
 * The user can select the order in which the monsters will act during the conflict. Once the order is set, the user can save or cancel the changes.
 *
 * @param monsters A list of [GameCharacter.Monster] representing the monsters involved in the conflict.
 * @param onSave A lambda that is invoked when the user decides to save the chosen order. It provides a list of [GameCharacter.Monster] with the updated order.
 * @param onCancel A lambda that is invoked when the user decides to cancel the changes. It provides a boolean indicating if the dialog should be dismissed.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonsterConflictDialog(
    monsters: List<GameCharacter.Monster>,
    onSave: (List<GameCharacter.Monster>) -> Unit,
    onCancel: (Boolean) -> Unit
) {


    var initiativeOrder by remember { mutableIntStateOf(0) }
    var monsterOrder: List<GameCharacter.Monster> by remember { mutableStateOf(monsters) }

    LaunchedEffect(monsters) {
        initiativeOrder = 0
        monsterOrder = monsters
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
                    text = "Choose the order of the monsters",
                    fontSize = MaterialTheme.typography.headlineMedium.fontSize,
                    textAlign = TextAlign.Center
                )
                Divider()

                monsterOrder.forEachIndexed { index, monster ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier
                            .clickable {
                                if (monsterOrder.all { it.onConflictOrder != null }) {
                                    monsterOrder =
                                        monsterOrder.map { it.copy(onConflictOrder = null) }
                                    initiativeOrder = 0
                                }
                                monsterOrder = monsterOrder.map {
                                    if (it.characterName == monsterOrder[index].characterName
                                        && monsterOrder[index].onConflictOrder == null
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
                        onClick = {
                            onSave(monsterOrder)
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
fun MonsterConflictPrev() {
    HavenCompanionTheme {
        MonsterConflictDialog(
            monsters = emptyList(),
            onSave = {},
            onCancel = {}
        )
    }
}