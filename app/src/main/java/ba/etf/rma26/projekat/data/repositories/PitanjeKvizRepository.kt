package ba.etf.rma26.projekat.data.repositories

import ba.etf.rma26.projekat.data.models.Pitanje

object PitanjeKvizRepository {

    private fun api() = ApiConfig.getApi()

    suspend fun getPitanja(idKviza: Int): List<Pitanje> {
        return try {
            api().getPitanja(idKviza)
        } catch (e: Exception) {
            emptyList()
        }
    }
}