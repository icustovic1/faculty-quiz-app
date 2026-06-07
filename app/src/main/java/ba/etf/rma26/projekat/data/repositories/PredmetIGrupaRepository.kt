package ba.etf.rma26.projekat.data.repositories

import ba.etf.rma26.projekat.data.models.Grupa
import ba.etf.rma26.projekat.data.models.Predmet

object PredmetIGrupaRepository {

    private fun api() = ApiConfig.getApi()
    private suspend fun hash() = AccountRepository.getHash()

    suspend fun getPredmeti(): List<Predmet> {
        return try {
            api().getPredmeti()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getGrupe(): List<Grupa> {
        return try {
            api().getGrupe()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getGrupeZaPredmet(idPredmeta: Int): List<Grupa> {
        return try {
            api().getGrupeZaPredmet(idPredmeta)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun upisiUGrupu(idGrupa: Int): Boolean {
        return try {
            val h = hash()
            if (h.isBlank()) return false
            api().upisiUGrupu(h, idGrupa, ApiConfig.getApiKey())
        } catch (e: Exception) {
            false
        }
    }

    suspend fun getUpisaneGrupe(): List<Grupa> {
        return try {
            val h = hash()
            if (h.isBlank()) return emptyList()
            api().getUpisaneGrupe(h)
        } catch (e: Exception) {
            emptyList()
        }
    }
}