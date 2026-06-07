package ba.etf.rma26.projekat.data.models
import java.time.LocalDateTime
data class Predmet(
    val id: Int,
    val naziv: String,
    val godina: Int
)

data class Grupa(
    val id: Int,
    val naziv: String,
    val idPredmeta: Int
)



data class Kviz(
    val id: Int,
    val naziv: String,
    val idPredmeta: Int,
    val idGrupe: Int,
    val datumPocetka: LocalDateTime,
    val datumKraj: LocalDateTime,
    val trajanje: Int,
    val nazivPredmeta: String?,
    val nazivGrupe: String?,
    val datumRada: LocalDateTime? = null
)
data class Pitanje(
    val id: Int,
    val idKviza: Int,
    val naziv: String,
    val tekstPitanja: String,
    val opcije: List<String>,
    val tacan: Int
)

data class KvizTaken(
    val id: Int,
    val hash: String,
    val idKviza: Int,
    val datumRada: String?,
    val zavrsen: Boolean,
    val osvojenBodovi: Int?
)

data class Odgovor(
    val id: Int,
    val idKvizTaken: Int,
    val idPitanje: Int,
    val odgovor: Int
)

data class OdgovorRequest(
    val idPitanje: Int,
    val odgovor: Int
)