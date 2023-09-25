package se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import se.umu.svke0008.havencompanion.domain.model.enhancement.EnhancementType
import se.umu.svke0008.havencompanion.presentation.util_components.ResourceUtils
import se.umu.svke0008.havencompanion.ui.padding
import se.umu.svke0008.havencompanion.ui.theme.HavenCompanionTheme

@Composable
fun EnhancementTypeGroup(
    title: String = "Choose enhancement sticker",
    selectedType: EnhancementType,
    containerColor: Color = MaterialTheme.colorScheme.tertiaryContainer,
    contentColor: Color = MaterialTheme.colorScheme.onTertiaryContainer,
    onEnhancementTypeClicked: (EnhancementType) -> Unit
) {

    val enhancementTypes = listOf(
        Pair(EnhancementType.NeutralEnhancementType, "Square"),
        Pair(EnhancementType.ElementEnhancementType, "Circle"),
        Pair(EnhancementType.NegativeEnhancementType, "Diamond"),
        Pair(EnhancementType.PositiveEnhancementType, "Diamond plus"),
        Pair(EnhancementType.AreaOfEffectEnhancement, "Hexagon")
    )

    Card(
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = containerColor,
            contentColor = contentColor
        )
    ) {


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.padding.small),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            enhancementTypes.forEach { type ->
                Column(
                    horizontalAlignment = CenterHorizontally,
                    modifier = Modifier.padding(8.dp)
                ) {
                    AsyncImage(
                        model = ResourceUtils.getEnhancementTypeIcon(type.first),
                        contentDescription = null,
                        contentScale = ContentScale.Inside,
                        modifier = Modifier.size(48.dp),
                        colorFilter = if
                                              (type.first::class == EnhancementType.AreaOfEffectEnhancement::class) null
                        else ColorFilter.tint(
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    )
                    RadioButton(selected = type.first == selectedType, onClick = {
                        onEnhancementTypeClicked.invoke(type.first)
                    })
                }
            }
        }

    }


}

@Preview(showSystemUi = true)
@Composable
fun EnhancementTypeGroupPrev() {
    HavenCompanionTheme {
        EnhancementTypeGroup(
            selectedType = EnhancementType.NeutralEnhancementType,
            onEnhancementTypeClicked = {})
    }
}