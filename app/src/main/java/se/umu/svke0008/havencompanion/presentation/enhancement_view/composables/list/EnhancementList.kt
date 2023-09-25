package se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.list

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import se.umu.svke0008.havencompanion.domain.model.enhancement.Enhancement
import se.umu.svke0008.havencompanion.ui.theme.NyalaAlternative

@Composable
fun EnhancementList(
    enhancements: List<Enhancement>,
    listState: LazyListState = rememberLazyListState(),
    paddingValues: PaddingValues
) {


    LazyColumn(
        state = listState
    ) {
        items(enhancements) { enhancement ->
            ListItem(
                colors = ListItemDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.inverseSurface,
                    headlineColor = MaterialTheme.colorScheme.inverseOnSurface,
                    trailingIconColor = MaterialTheme.colorScheme.inverseOnSurface,
                    supportingColor = MaterialTheme.colorScheme.inverseOnSurface
                ),
                leadingContent = {
                    AsyncImage(
                        model = enhancement.icon.first,
                        contentDescription = null,
                        contentScale = ContentScale.Inside,
                        modifier = Modifier.size(48.dp),
                        colorFilter = if
                                (!enhancement.icon.second) ColorFilter.tint(color = MaterialTheme.colorScheme.inverseOnSurface)
                        else null
                    )
                },
                headlineContent = {
                    Text(
                        text = enhancement.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontFamily = NyalaAlternative
                    )
                },
                supportingContent = {
                    Text(
                        text = "Basecost: ${enhancement.baseCost}",
                        style = MaterialTheme.typography.labelLarge
                    )
                },
                trailingContent = {
                    Text(
                        text = enhancement.calculatedCost.toString(),
                        style = MaterialTheme.typography.headlineLarge,
                        fontFamily = NyalaAlternative
                    )
                }
            )
            Divider()
        }

    }
}

