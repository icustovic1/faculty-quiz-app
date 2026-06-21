package ba.etfrma.rma26p19950.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ba.etfrma.rma26p19950.ui.components.KvizItem
import ba.etfrma.rma26p19950.viewmodel.KvizViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KvizoviScreen(
    viewModel: KvizViewModel,
    filterLabel: String,
    onBack: () -> Unit,
    onKvizClick: (Int) -> Unit
) {
    val kvizovi by viewModel.filteredKvizovi.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val greska by viewModel.greska.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(filterLabel) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Nazad"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                greska != null -> {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = greska!!,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(onClick = { viewModel.applyFilter(viewModel.selectedFilter.value) }) {
                            Text("Pokušaj ponovo")
                        }
                    }
                }
                kvizovi.isEmpty() -> {
                    Text(
                        text = "Nema kvizova za prikaz.",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(24.dp),
                        textAlign = TextAlign.Center
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .testTag("listaKvizova"),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(kvizovi) { kviz ->
                            KvizItem(
                                kviz = kviz,
                                onClick = { onKvizClick(kviz.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}