package se.umu.svke0008.havencompanion.presentation.settings_view.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import se.umu.svke0008.havencompanion.ui.padding
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

@Composable
fun SwitchSetting(
    title: String,
    isChecked: Boolean,
    enabled: Boolean = true,
    checkedBorderColor: Color = MaterialTheme.colorScheme.primary,
    checkedTrackColor: Color = MaterialTheme.colorScheme.primary,
    onCheckedChange: (Boolean) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.padding.small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.titleLarge)
        Switch(
            checked = isChecked,
            enabled = enabled,
            onCheckedChange = { onCheckedChange(it) },
            colors = SwitchDefaults.colors(
                checkedBorderColor = checkedBorderColor,
                checkedTrackColor = checkedTrackColor
            )
        )
    }

}

@Preview
@Composable
fun SwitchSettingPrevFalse() {
    HavenCompanionTheme() {
        SwitchSetting(title = "Testing", isChecked = false, onCheckedChange = {})
    }
}

@Preview
@Composable
fun SwitchSettingPrevTrue() {
    HavenCompanionTheme() {
        SwitchSetting(title = "Testing", isChecked = true, onCheckedChange = {})
    }
}