package ba.etfrma.rma26p19950.util

import ba.etf.rma26.projekat.data.models.Kviz
import ba.etfrma.rma26p19950.R
import java.time.LocalDateTime

/**
 * Status kviza odredjen u odnosu na STVARNO trenutno vrijeme (LocalDateTime.now()).
 * Prije je status racunat na fiksni hardkodirani datum, sto nije odrazavalo
 * stvarno stanje. Logika je izdvojena iz KvizItem-a da bi bila na jednom mjestu
 * i ponovo iskoristiva.
 */
enum class KvizStatus(val opis: String, val drawable: Int) {
    URADEN("Urađen", R.drawable.plava),
    BUDUCI("Budući", R.drawable.zuta),
    PROSLI("Propušten", R.drawable.crvena),
    AKTIVAN("Aktivan", R.drawable.zelena)
}

fun odrediStatus(kviz: Kviz, ref: LocalDateTime = LocalDateTime.now()): KvizStatus {
    return when {
        kviz.datumRada != null -> KvizStatus.URADEN
        kviz.datumPocetka.isAfter(ref) -> KvizStatus.BUDUCI
        kviz.datumKraj.isBefore(ref) -> KvizStatus.PROSLI
        else -> KvizStatus.AKTIVAN
    }
}

fun prikazniDatum(kviz: Kviz, ref: LocalDateTime = LocalDateTime.now()): LocalDateTime {
    return when (odrediStatus(kviz, ref)) {
        KvizStatus.URADEN -> kviz.datumRada!!
        KvizStatus.BUDUCI -> kviz.datumPocetka
        else -> kviz.datumKraj
    }
}
