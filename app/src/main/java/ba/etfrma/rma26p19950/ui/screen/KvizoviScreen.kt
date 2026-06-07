package ba.etfrma.rma26p19950.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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