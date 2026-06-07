package ba.etf.rma26.projekat.data.repositories

import ba.etf.rma26.projekat.data.models.Odgovor
import ba.etf.rma26.projekat.data.models.OdgovorRequest

object OdgovorRepository {

    private fun api() = ApiConfig.getApi()
    private suspend fun hash() = AccountRepository.getHash()

    suspend fun getOdgovoriKviz(idKviza: Int): List<Odgovor> {
        return try {
            val h = hash()
            if (h.isBlank()) return emptyList()
            api().getOdgovoriKviz(h, idKviza)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun postaviOdgovorKviz(idKvizTaken: Int, idPitanje: Int, odgovor: Int): Int {
        return try {
            val h = hash()
            if (h.isBlank()) return -1
            api().postaviOdgovor(
                h,
                idKvizTaken,
                OdgovorRequest(idPitanje, odgovor),
                ApiConfig.getApiKey()
            )
        } catch (e: Exception) {
            -1
        }
    }
}