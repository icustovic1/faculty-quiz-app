package ba.etfrma.rma26p19950.model

import java.time.LocalDateTime

data class Kviz(
    val naziv: String,
    val nazivPredmeta: String,
    val datumPocetak: LocalDateTime,
    val datumKraj: LocalDateTime,
    val datumRada: LocalDateTime?,
    val trajanje: Int,
    val nazivGrupe: String,
    val osvojeniBodovi: Float?
)

data class Predmet(
    val naziv: String,
    val godina: Int
)

data class Grupa(
    val naziv: String,
    val nazivPredmeta: String
)