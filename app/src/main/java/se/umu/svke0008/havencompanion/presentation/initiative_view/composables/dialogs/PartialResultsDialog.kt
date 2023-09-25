package se.umu.svke0008.havencompanion.presentation.initiative_view.composables.dialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartialResultsDialog(
    partialResult: String,
    showDialog: (Boolean) -> Unit
) {

    var showAgain by remember { mutableStateOf(true) }



    AlertDialog(onDismissRequest = { showDialog(showAgain) }) {
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
                Row() {
                    Text(
                        text = "Speech:",
                        fontSize = MaterialTheme.typography.displayMedium.fontSize,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        lineHeight = 40.sp
                    )
                }
                Divider()

                Row(Modifier.fillMaxWidth().padding(8.dp)) {
                    Text(text = partialResult)
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Dont show again")
                    Checkbox(checked = !showAgain, onCheckedChange = { showAgain = !it })

                    TextButton(
                        onClick = {
                            showDialog(showAgain)
                        }
                    ) {
                        Text("Close")
                    }
                }


            }
        }


    }
}

@Preview
@Composable
fun Prev() {
    HavenCompanionTheme() {
        PartialResultsDialog(partialResult = "Testing", showDialog = {})
    }
}