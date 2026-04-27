package ba.etfrma.rma26p19950.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ba.etfrma.rma26p19950.ui.components.UpisSection
import ba.etfrma.rma26p19950.viewmodel.FilterOpcija
import ba.etfrma.rma26p19950.viewmodel.KvizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterScreen(
    viewModel: KvizViewModel,
    onNavigate: (String) -> Unit
) {
    val selectedFilter by viewModel.selectedFilter.collectAsState()
    val brojKvizova by viewModel.brojKvizova.collectAsState()
    var expandedFilter by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Postavke") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            UpisSection(viewModel = viewModel)

            HorizontalDivider()


            Text(
                text = "Filteri",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            ExposedDropdownMenuBox(
                expanded = expandedFilter,
                onExpandedChange = { expandedFilter = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("filterKvizova")
            ) {
                OutlinedTextField(
                    value = selectedFilter.label,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Filter kvizova") },
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


            Text(
                text = "Pronađeno je $brojKvizova kvizova",
                modifier = Modifier.testTag("brojKvizova")
            )

            Spacer(modifier = Modifier.weight(1f))


            Button(
                onClick = { onNavigate(selectedFilter.label) },
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("prikaziKvizoveDugme")
            ) {
                Text("Prikaži kvizove")
            }
        }
    }
}