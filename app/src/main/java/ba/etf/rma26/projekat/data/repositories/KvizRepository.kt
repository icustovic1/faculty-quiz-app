package ba.etf.rma26.projekat.data.repositories

import ba.etf.rma26.projekat.data.models.Kviz

object KvizRepository {

    private fun api() = ApiConfig.getApi()
    private suspend fun hash() = AccountRepository.getHash()

    suspend fun getAll(): List<Kviz> {
        return try {
            api().getKvizovi()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getById(id: Int): Kviz? {
        return try {
            val response = api().getKvizById(id)
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getUpisani(): List<Kviz> {
        return try {
            val h = hash()
            if (h.isBlank()) return emptyList()
            api().getUpisaniKvizovi(h)
        } catch (e: Exception) {
            emptyList()
        }
    }
}