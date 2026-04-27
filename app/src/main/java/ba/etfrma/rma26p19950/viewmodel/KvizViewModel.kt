/*package ba.etfrma.rma26p19950.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ba.etfrma.rma26p19950.data.GrupaStaticData
import ba.etfrma.rma26p19950.data.KvizStaticData
import ba.etfrma.rma26p19950.data.PredmetStaticData
import ba.etfrma.rma26p19950.model.Grupa
import ba.etfrma.rma26p19950.model.Kviz
import ba.etfrma.rma26p19950.model.Predmet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
        applyFilter(FilterOpcija.SVI_MOJI)
    }

    fun applyFilter(filter: FilterOpcija) {
        _selectedFilter.value = filter
        viewModelScope.launch {
            _filteredKvizovi.value = when (filter) {
                FilterOpcija.SVI_MOJI -> KvizStaticData.getUpisani()
                FilterOpcija.SVI -> KvizStaticData.getAll()
                FilterOpcija.URADENI -> KvizStaticData.getDone()
                FilterOpcija.BUDUCI -> KvizStaticData.getFuture()
                FilterOpcija.PROSLI_NEURADENI -> KvizStaticData.getNotTaken()
            }
        }
    }

    fun onGodinaSelected(godina: Int) {
        _selectedGodina.value = godina
        _selectedPredmet.value = null
        _selectedGrupa.value = null
        _grupeZaPredmet.value = emptyList()
        val upisaniNazivi = PredmetStaticData.getUpisani().map { it.naziv }
        _predmetiZaGodinu.value = PredmetStaticData.getAll()
            .filter { it.godina == godina && it.naziv !in upisaniNazivi }
    }

    fun onPredmetSelected(predmet: Predmet) {
        _selectedPredmet.value = predmet
        _selectedGrupa.value = null
        _grupeZaPredmet.value = GrupaStaticData.getGrupaFromPredmet(predmet.naziv)
    }

    fun onGrupaSelected(grupa: Grupa) {
        _selectedGrupa.value = grupa
    }

    fun upisi() {
        val predmet = _selectedPredmet.value ?: return
        val grupa = _selectedGrupa.value ?: return
        PredmetStaticData.upisi(predmet)
        KvizStaticData.upisi(predmet.naziv, grupa.naziv)
        _selectedPredmet.value = null
        _selectedGrupa.value = null
        _grupeZaPredmet.value = emptyList()
        applyFilter(_selectedFilter.value)
        _selectedGodina.value?.let { onGodinaSelected(it) }
    }
}*/
package ba.etfrma.rma26p19950.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ba.etfrma.rma26p19950.data.GrupaStaticData
import ba.etfrma.rma26p19950.data.KvizStaticData
import ba.etfrma.rma26p19950.data.PredmetStaticData
import ba.etfrma.rma26p19950.model.Grupa
import ba.etfrma.rma26p19950.model.Kviz
import ba.etfrma.rma26p19950.model.Predmet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
        applyFilter(FilterOpcija.SVI_MOJI)
    }

    fun applyFilter(filter: FilterOpcija) {
        _selectedFilter.value = filter
        viewModelScope.launch {
            val lista = when (filter) {
                FilterOpcija.SVI_MOJI         -> KvizStaticData.getUpisani()
                FilterOpcija.SVI              -> KvizStaticData.getAll()
                FilterOpcija.URADENI          -> KvizStaticData.getDone()
                FilterOpcija.BUDUCI           -> KvizStaticData.getFuture()
                FilterOpcija.PROSLI_NEURADENI -> KvizStaticData.getNotTaken()
            }
            _filteredKvizovi.value = lista
            _brojKvizova.value = lista.size
        }
    }

    fun onGodinaSelected(godina: Int) {
        _selectedGodina.value = godina
        _selectedPredmet.value = null
        _selectedGrupa.value = null
        _grupeZaPredmet.value = emptyList()
        val upisaniNazivi = PredmetStaticData.getUpisani().map { it.naziv }
        _predmetiZaGodinu.value = PredmetStaticData.getAll()
            .filter { it.godina == godina && it.naziv !in upisaniNazivi }
    }

    fun onPredmetSelected(predmet: Predmet) {
        _selectedPredmet.value = predmet
        _selectedGrupa.value = null
        _grupeZaPredmet.value = GrupaStaticData.getGrupaFromPredmet(predmet.naziv)
    }

    fun onGrupaSelected(grupa: Grupa) {
        _selectedGrupa.value = grupa
    }

    fun upisi() {
        val predmet = _selectedPredmet.value ?: return
        val grupa   = _selectedGrupa.value   ?: return
        PredmetStaticData.upisi(predmet)
        KvizStaticData.upisi(predmet.naziv, grupa.naziv)
        _selectedPredmet.value = null
        _selectedGrupa.value   = null
        _grupeZaPredmet.value  = emptyList()
        applyFilter(_selectedFilter.value)
        _selectedGodina.value?.let { onGodinaSelected(it) }
    }
}