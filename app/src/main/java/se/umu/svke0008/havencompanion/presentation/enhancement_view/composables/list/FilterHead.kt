package se.umu.svke0008.havencompanion.presentation.enhancement_view.composables.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import se.umu.svke0008.havencompanion.domain.utils.EnhancementFilter
import se.umu.svke0008.havencompanion.domain.utils.OrderType
import se.umu.svke0008.havencompanion.ui.padding

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FilterHead(
    filters: List<EnhancementFilter>,
    sorters: List<OrderType>,
    gold: Int? = null,
    onCurrentGold: (Int?) -> Unit,
    onFilterSelect: (EnhancementFilter) -> Unit,
    onSortSelect: (OrderType) -> Unit
) {

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var expanded by remember { mutableStateOf(false) }
    var selectedSort by remember { mutableStateOf(sorters[0]) }
    var currentGold by remember { mutableStateOf(gold?.toString() ?: "") }



    Card(
        shape = MaterialTheme.shapes.extraSmall,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        )
    ) {



        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.padding.small)
        ) {
            Text(
                text = "Sort cost by: $selectedSort",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onTertiaryContainer
            )
            Box {

                IconButton(onClick = { expanded = !expanded }) {
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null)
                }

                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    sorters.forEach { sorter ->
                        DropdownMenuItem(
                            text = { Text(text = sorter.toString()) },
                            onClick = {
                                onSortSelect(sorter)
                                expanded = false
                                selectedSort = sorter
                            })
                    }

                }
            }


            TextField(
                modifier = Modifier
                    .padding(8.dp)
                    .focusRequester(focusRequester)
                    .onFocusChanged { focusState ->
                        if (currentGold.isNotBlank()) {
                            onCurrentGold(currentGold.toInt())
                        }
                    },
                value = currentGold,

                label = {
                    Text(
                        text = "Gold",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                colors = TextFieldDefaults.colors(focusedTextColor = MaterialTheme.colorScheme.primary),
                onValueChange = {
                    val filteredInput = it.filter { char -> char.isDigit() }
                    if(filteredInput.length <= 5) {
                        currentGold = filteredInput
                        onCurrentGold(currentGold.toIntOrNull())
                    }
                },
                singleLine = true,

                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    },
                ),
            )

            if(!WindowInsets.isImeVisible) {
                focusManager.clearFocus()
            }

        }


        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            filters.forEach { filter ->
                FilterChip(
                    label = { Text(text = filter.toString()) },
                    selected = filter.selected,
                    onClick = { onFilterSelect(filter) },
                    trailingIcon = {
                        if (filter.selected) Icon(
                            Icons.Default.Check,
                            contentDescription = null
                        )
                    },
                    colors =
                    FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.tertiary,
                        selectedLabelColor = MaterialTheme.colorScheme.onTertiary,
                        selectedTrailingIconColor = MaterialTheme.colorScheme.onTertiary
                    )
                )
            }
        }

    }


}