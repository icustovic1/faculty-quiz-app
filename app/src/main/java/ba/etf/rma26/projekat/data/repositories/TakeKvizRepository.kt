package ba.etf.rma26.projekat.data.repositories

import ba.etf.rma26.projekat.data.models.KvizTaken

object TakeKvizRepository {

    private fun api() = ApiConfig.getApi()
    private suspend fun hash() = AccountRepository.getHash()

    suspend fun zapocniKviz(idKviza: Int): KvizTaken? {
        return try {
            val h = hash()
            if (h.isBlank()) return null
            val response = api().zapocniKviz(h, idKviza, ApiConfig.getApiKey())
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getPocetiKvizovi(): List<KvizTaken>? {
        return try {
            val h = hash()
            if (h.isBlank()) return null
            api().getPocetiKvizovi(h)
        } catch (e: Exception) {
            null
        }
    }
}