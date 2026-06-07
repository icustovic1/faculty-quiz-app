package ba.etfrma.rma26p19950.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import ba.etf.rma26.projekat.data.models.Predmet
import ba.etf.rma26.projekat.data.models.Grupa
import ba.etfrma.rma26p19950.viewmodel.KvizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpisSection(viewModel: KvizViewModel) {
    val selectedGodina by viewModel.selectedGodina.collectAsState()
    val selectedPredmet by viewModel.selectedPredmet.collectAsState()
    val selectedGrupa by viewModel.selectedGrupa.collectAsState()
    val predmetiZaGodinu by viewModel.predmetiZaGodinu.collectAsState()
    val grupeZaPredmet by viewModel.grupeZaPredmet.collectAsState()

    var expandedGodina by remember { mutableStateOf(false) }
    var expandedPredmet by remember { mutableStateOf(false) }
    var expandedGrupa by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(text = "Upis na predmet", style = MaterialTheme.typography.titleMedium)

        ExposedDropdownMenuBox(
            expanded = expandedGodina,
            onExpandedChange = { expandedGodina = it },
            modifier = Modifier.testTag("odabirGodina")
        ) {
            OutlinedTextField(
                value = selectedGodina?.toString() ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Godina studija") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGodina) },
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedGodina,
                onDismissRequest = { expandedGodina = false }
            ) {
                viewModel.godine.forEach { god ->
                    DropdownMenuItem(
                        text = { Text(god.toString()) },
                        onClick = {
                            viewModel.onGodinaSelected(god)
                            expandedGodina = false
                        }
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = expandedPredmet,
            onExpandedChange = { if (selectedGodina != null) expandedPredmet = it },
            modifier = Modifier.testTag("odabirPredmet")
        ) {
            OutlinedTextField(
                value = selectedPredmet?.naziv ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Predmet") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedPredmet) },
                enabled = selectedGodina != null,
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedPredmet,
                onDismissRequest = { expandedPredmet = false }
            ) {
                if (predmetiZaGodinu.isEmpty()) {
                    DropdownMenuItem(
                        text = { Text("Nema dostupnih predmeta") },
                        onClick = { expandedPredmet = false },
                        enabled = false
                    )
                } else {
                    predmetiZaGodinu.forEach { predmet ->
                        DropdownMenuItem(
                            text = { Text(predmet.naziv) },
                            onClick = {
                                viewModel.onPredmetSelected(predmet)
                                expandedPredmet = false
                            }
                        )
                    }
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = expandedGrupa,
            onExpandedChange = { if (selectedPredmet != null) expandedGrupa = it },
            modifier = Modifier.testTag("odabirGrupa")
        ) {
            OutlinedTextField(
                value = selectedGrupa?.naziv ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text("Grupa") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedGrupa) },
                enabled = selectedPredmet != null,
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandedGrupa,
                onDismissRequest = { expandedGrupa = false }
            ) {
                grupeZaPredmet.forEach { grupa ->
                    DropdownMenuItem(
                        text = { Text(grupa.naziv) },
                        onClick = {
                            viewModel.onGrupaSelected(grupa)
                            expandedGrupa = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = { viewModel.upisi() },
            enabled = selectedGodina != null && selectedPredmet != null && selectedGrupa != null,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("dodajPredmetDugme")
        ) {
            Text("Upiši me")
        }
    }
}