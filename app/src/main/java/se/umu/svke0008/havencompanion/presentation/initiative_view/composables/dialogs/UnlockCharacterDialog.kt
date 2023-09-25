package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import se.umu.svke0008.havencompanion.data.local.entities.character_entity.GameCharacterEntity
import se.umu.svke0008.havencompanion.data.mappers.GameCharacterMapperImpl.toEntity
import se.umu.svke0008.havencompanion.data.mappers.JsonParser
import se.umu.svke0008.havencompanion.presentation.util_components.ResourceUtils
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnlockCharacterDialog(
    entity: GameCharacterEntity,
    onConfirm: (GameCharacterEntity) -> Unit,
    onCancel: (Boolean) -> Unit
) {

    AlertDialog(onDismissRequest = { onCancel(false) }) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large,
            tonalElevation = AlertDialogDefaults.TonalElevation
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = if(!entity.revealed) "Unlock this character?" else "Lock this character?",
                    fontSize = MaterialTheme.typography.displayMedium.fontSize,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    lineHeight = 40.sp
                )
                entity.icon?.let {
                    Icon(
                        painter = painterResource(id = ResourceUtils.getDrawableIdFromIconName(it)),
                        contentDescription = "Hero icon",
                        modifier = Modifier.size(48.dp),
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            onCancel(false)
                        }
                    ) {
                        Text("Cancel")
                    }
                    TextButton(
                        onClick = {
                            onConfirm(entity)
                        }
                    ) {
                        Text("Confirm")
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun UnLockPRev() {
    val context = LocalContext.current
    val heroesJson = context.assets.open("heroes.json").bufferedReader().use { it.readText() }
    val heroesDto = JsonParser.parseGameCharactersFromJson(heroesJson)
    val heroes = heroesDto.map { it.toEntity() }
    HavenCompanionTheme() {
        UnlockCharacterDialog(entity = heroes[8], onConfirm = {}, onCancel = { })
    }
}