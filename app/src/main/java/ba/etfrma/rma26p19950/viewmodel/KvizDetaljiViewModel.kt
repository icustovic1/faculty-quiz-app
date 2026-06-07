package ba.etfrma.rma26p19950.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ba.etf.rma26.projekat.data.models.KvizTaken
import ba.etf.rma26.projekat.data.models.Kviz
import ba.etf.rma26.projekat.data.models.Pitanje
import ba.etf.rma26.projekat.data.repositories.KvizRepository
import ba.etf.rma26.projekat.data.repositories.OdgovorRepository
import ba.etf.rma26.projekat.data.repositories.PitanjeKvizRepository
import ba.etf.rma26.projekat.data.repositories.TakeKvizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class KvizDetaljiViewModel : ViewModel() {

    private val _kviz = MutableStateFlow<Kviz?>(null)
    val kviz: StateFlow<Kviz?> = _kviz.asStateFlow()

    private val _pitanja = MutableStateFlow<List<Pitanje>>(emptyList())
    val pitanja: StateFlow<List<Pitanje>> = _pitanja.asStateFlow()

    private val _pokusaj = MutableStateFlow<KvizTaken?>(null)
    val pokusaj: StateFlow<KvizTaken?> = _pokusaj.asStateFlow()

    private val _rezultat = MutableStateFlow<Int?>(null)
    val rezultat: StateFlow<Int?> = _rezultat.asStateFlow()

    private val _greska = MutableStateFlow<String?>(null)
    val greska: StateFlow<String?> = _greska.asStateFlow()

    private val _odabraniOdgovori = MutableStateFlow<Map<Int, Int>>(emptyMap())
    val odabraniOdgovori: StateFlow<Map<Int, Int>> = _odabraniOdgovori.asStateFlow()

    fun ucitaj(idKviza: Int) {
        viewModelScope.launch {
            _kviz.value = KvizRepository.getById(idKviza)
            _pitanja.value = PitanjeKvizRepository.getPitanja(idKviza)

            val poceti = TakeKvizRepository.getPocetiKvizovi()
            _pokusaj.value = poceti?.find { it.idKviza == idKviza }
        }
    }

    fun zapocniKviz(idKviza: Int) {
        viewModelScope.launch {
            val p = TakeKvizRepository.zapocniKviz(idKviza)
            if (p != null) {
                _pokusaj.value = p
                _greska.value = null
            } else {
                _greska.value = "Nije moguće započeti kviz."
            }
        }
    }

    fun posaljiOdgovor(idPokusaja: Int, idPitanja: Int, odgovor: Int) {
        viewModelScope.launch {
            val bodovi = OdgovorRepository.postaviOdgovorKviz(idPokusaja, idPitanja, odgovor)
            if (bodovi >= 0) {
                _rezultat.value = bodovi
                _odabraniOdgovori.value = _odabraniOdgovori.value + (idPitanja to odgovor)
                _greska.value = null
            } else {
                _greska.value = "Nevalidan odgovor."
            }
        }
    }
}