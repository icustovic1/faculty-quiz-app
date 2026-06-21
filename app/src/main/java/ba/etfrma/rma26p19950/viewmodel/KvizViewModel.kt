package ba.etfrma.rma26p19950.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ba.etf.rma26.projekat.data.models.Grupa
import ba.etf.rma26.projekat.data.models.Kviz
import ba.etf.rma26.projekat.data.models.Predmet
import ba.etf.rma26.projekat.data.repositories.AccountRepository
import ba.etf.rma26.projekat.data.repositories.KvizRepository
import ba.etf.rma26.projekat.data.repositories.PredmetIGrupaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime

enum class FilterOpcija(val label: String) {
    SVI_MOJI("Svi moji kvizovi"),
    SVI("Svi kvizovi"),
    URADENI("Urađeni kvizovi"),
    BUDUCI("Budući kvizovi"),
    PROSLI_NEURADENI("Prošli kvizovi")
}

class KvizViewModel : ViewModel() {

    private val _selectedFilter = MutableStateFlow(FilterOpcija.SVI_MOJI)
    val selectedFilter: StateFlow<FilterOpcija> = _selectedFilter.asStateFlow()

    private val _filteredKvizovi = MutableStateFlow<List<Kviz>>(emptyList())
    val filteredKvizovi: StateFlow<List<Kviz>> = _filteredKvizovi.asStateFlow()

    private val _brojKvizova = MutableStateFlow(0)
    val brojKvizova: StateFlow<Int> = _brojKvizova.asStateFlow()

    // NOVO: loading i error stanja za bolji UX
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _greska = MutableStateFlow<String?>(null)
    val greska: StateFlow<String?> = _greska.asStateFlow()

    private val _selectedGodina = MutableStateFlow<Int?>(null)
    val selectedGodina: StateFlow<Int?> = _selectedGodina.asStateFlow()

    private val _selectedPredmet = MutableStateFlow<Predmet?>(null)
    val selectedPredmet: StateFlow<Predmet?> = _selectedPredmet.asStateFlow()

    private val _selectedGrupa = MutableStateFlow<Grupa?>(null)
    val selectedGrupa: StateFlow<Grupa?> = _selectedGrupa.asStateFlow()

    private val _predmetiZaGodinu = MutableStateFlow<List<Predmet>>(emptyList())
    val predmetiZaGodinu: StateFlow<List<Predmet>> = _predmetiZaGodinu.asStateFlow()

    private val _grupeZaPredmet = MutableStateFlow<List<Grupa>>(emptyList())
    val grupeZaPredmet: StateFlow<List<Grupa>> = _grupeZaPredmet.asStateFlow()

    val godine = listOf(1, 2, 3, 4, 5)

    init {
        viewModelScope.launch {
            AccountRepository.hashFlow.collect {
                applyFilter(_selectedFilter.value)
            }
        }
        applyFilter(FilterOpcija.SVI_MOJI)
    }

    fun applyFilter(filter: FilterOpcija) {
        _selectedFilter.value = filter
        viewModelScope.launch {
            _isLoading.value = true
            _greska.value = null
            try {
                val ref = LocalDateTime.now()
                val osnova = when (filter) {
                    FilterOpcija.SVI -> KvizRepository.getAll()
                    else -> KvizRepository.getUpisani()
                }
                val lista = when (filter) {
                    FilterOpcija.SVI_MOJI -> osnova
                    FilterOpcija.SVI -> osnova
                    FilterOpcija.URADENI -> osnova.filter { it.datumRada != null }
                    FilterOpcija.BUDUCI -> osnova.filter { it.datumPocetka.isAfter(ref) }
                    FilterOpcija.PROSLI_NEURADENI -> osnova.filter {
                        it.datumKraj.isBefore(ref) && it.datumRada == null
                    }
                }.sortedBy { it.datumPocetka }

                _filteredKvizovi.value = lista
                _brojKvizova.value = lista.size
            } catch (e: Exception) {
                _greska.value = "Nije moguće učitati kvizove. Provjerite konekciju."
                _filteredKvizovi.value = emptyList()
                _brojKvizova.value = 0
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun onGodinaSelected(godina: Int) {
        _selectedGodina.value = godina
        _selectedPredmet.value = null
        _selectedGrupa.value = null
        _grupeZaPredmet.value = emptyList()
        viewModelScope.launch {
            val svi = PredmetIGrupaRepository.getPredmeti()
            val upisane = PredmetIGrupaRepository.getUpisaneGrupe()
            val upisaniIdPredmeta = upisane.map { it.idPredmeta }.toSet()
            _predmetiZaGodinu.value = svi.filter {
                it.godina == godina && it.id !in upisaniIdPredmeta
            }
        }
    }

    fun onPredmetSelected(predmet: Predmet) {
        _selectedPredmet.value = predmet
        _selectedGrupa.value = null
        viewModelScope.launch {
            _grupeZaPredmet.value = PredmetIGrupaRepository.getGrupeZaPredmet(predmet.id)
        }
    }

    fun onGrupaSelected(grupa: Grupa) {
        _selectedGrupa.value = grupa
    }

    fun upisi() {
        val grupa = _selectedGrupa.value ?: return
        viewModelScope.launch {
            val uspjelo = PredmetIGrupaRepository.upisiUGrupu(grupa.id)
            if (uspjelo) {
                _selectedPredmet.value = null
                _selectedGrupa.value = null
                _grupeZaPredmet.value = emptyList()
                applyFilter(_selectedFilter.value)
                _selectedGodina.value?.let { onGodinaSelected(it) }
            }
        }
    }
}