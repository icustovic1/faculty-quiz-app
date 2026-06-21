package ba.etfrma.rma26p19950.ui.screen

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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ba.etfrma.rma26p19950.util.KvizStatus
import ba.etfrma.rma26p19950.util.odrediStatus
import ba.etfrma.rma26p19950.viewmodel.KvizDetaljiViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun KvizDetaljiScreen(
    idKviza: Int,
    onBack: () -> Unit
) {
    val viewModel: KvizDetaljiViewModel = viewModel()

    val kviz by viewModel.kviz.collectAsState()
    val pitanja by viewModel.pitanja.collectAsState()
    val pokusaj by viewModel.pokusaj.collectAsState()
    val rezultat by viewModel.rezultat.collectAsState()
    val greska by viewModel.greska.collectAsState()
    val odabraniOdgovori by viewModel.odabraniOdgovori.collectAsState()

    LaunchedEffect(idKviza) {
        viewModel.ucitaj(idKviza)
    }

    // Status kviza - kviz se moze raditi samo ako je aktivan
    val status = kviz?.let { odrediStatus(it) }
    val mozeRaditi = status == KvizStatus.AKTIVAN

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(kviz?.naziv ?: "Kviz") },
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
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                when {
                    kviz == null -> {
                        // jos se ucitava
                    }
                    !mozeRaditi && pokusaj == null -> {
                        // Kviz nije aktivan - ne moze se zapoceti
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Text(
                                text = when (status) {
                                    KvizStatus.BUDUCI -> "Ovaj kviz još nije počeo. Možete ga raditi kada postane aktivan."
                                    KvizStatus.PROSLI -> "Ovaj kviz je istekao i više se ne može raditi."
                                    KvizStatus.URADEN -> "Ovaj kviz ste već uradili."
                                    else -> "Ovaj kviz trenutno nije dostupan."
                                },
                                modifier = Modifier.padding(16.dp),
                                fontSize = 15.sp
                            )
                        }
                    }
                    pokusaj == null -> {
                        Button(
                            onClick = { viewModel.zapocniKviz(idKviza) },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Započni kviz")
                        }
                    }
                    else -> {
                        Text(
                            text = "Kviz u toku — odgovorite na pitanja ispod",
                            fontSize = 14.sp
                        )
                    }
                }
            }

            if (greska != null) {
                item {
                    Text(
                        text = greska!!,
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }

            if (rezultat != null) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Text(
                            text = "Ukupno bodova: $rezultat",
                            modifier = Modifier.padding(16.dp),
                            fontSize = 18.sp
                        )
                    }
                }
            }

            // Pitanja se prikazuju samo ako je kviz zapocet (aktivan)
            if (pokusaj != null) {
                items(pitanja) { pitanje ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = pitanje.tekstPitanja,
                                fontSize = 16.sp
                            )

                            val odgovoreno = odabraniOdgovori.containsKey(pitanje.id)

                            pitanje.opcije.forEachIndexed { index, opcija ->
                                val odabran = odabraniOdgovori[pitanje.id] == index
                                OutlinedButton(
                                    onClick = {
                                        val p = pokusaj
                                        if (p != null && !odgovoreno) {
                                            viewModel.posaljiOdgovor(p.id, pitanje.id, index)
                                        }
                                    },
                                    enabled = !odgovoreno,
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = if (odabran) ButtonDefaults.outlinedButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer
                                    ) else ButtonDefaults.outlinedButtonColors()
                                ) {
                                    Text(opcija)
                                }
                            }

                            if (odgovoreno) {
                                Text(
                                    text = "Odgovor zabilježen",
                                    fontSize = 12.sp,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}