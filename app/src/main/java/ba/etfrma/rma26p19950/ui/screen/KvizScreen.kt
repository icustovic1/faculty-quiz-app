package ba.etfrma.rma26p19950.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import ba.etfrma.rma26p19950.ui.components.KvizItem
import ba.etfrma.rma26p19950.ui.components.UpisSection
import ba.etfrma.rma26p19950.viewmodel.FilterOpcija
import ba.etfrma.rma26p19950.viewmodel.KvizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KvizScreen(
    viewModel: KvizViewModel = viewModel()
) {
    val kvizovi by viewModel.filteredKvizovi.collectAsState()
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    var expandedFilter by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Kvizovi") })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .testTag("listaKvizova"),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            item {
                UpisSection(viewModel = viewModel)
            }

            item {
                ExposedDropdownMenuBox(
                    expanded = expandedFilter,
                    onExpandedChange = { expandedFilter = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                        .testTag("filterKvizova")
                ) {
                    OutlinedTextField(
                        value = selectedFilter.label,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Filter") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedFilter)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expandedFilter,
                        onDismissRequest = { expandedFilter = false }
                    ) {
                        FilterOpcija.entries.forEach { opcija ->
                            DropdownMenuItem(
                                text = { Text(opcija.label) },
                                onClick = {
                                    viewModel.applyFilter(opcija)
                                    expandedFilter = false
                                }
                            )
                        }
                    }
                }
            }

            items(kvizovi) { kviz ->
                KvizItem(kviz = kviz)
            }
        }
    }
}