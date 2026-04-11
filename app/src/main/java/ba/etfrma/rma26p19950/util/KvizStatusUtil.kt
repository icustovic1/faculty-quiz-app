package ba.etfrma.rma26p19950.util

import ba.etfrma.rma26p19950.data.KvizStaticData
import ba.etfrma.rma26p19950.model.Kviz
import java.time.LocalDateTime
import java.time.ZoneId

enum class KvizStatus(val contentDescription: String) {
    PLAVA("Plava"),
    ZELENA("Zelena"),
    ZUTA("Žuta"),
    CRVENA("Crvena")
}

fun getKvizStatus(kviz: Kviz): KvizStatus {
    val ref = KvizStaticData.getReferentDate()

    return when {
        kviz.datumRada != null -> KvizStatus.PLAVA
        kviz.datumPocetak.isAfter(ref) -> KvizStatus.ZUTA
        kviz.datumKraj.isBefore(ref) -> KvizStatus.CRVENA
        else -> KvizStatus.ZELENA
    }
}

fun getDisplayDatum(kviz: Kviz): LocalDateTime {
    return when (getKvizStatus(kviz)) {
        KvizStatus.PLAVA -> kviz.datumRada!!
        KvizStatus.ZELENA -> kviz.datumKraj
        KvizStatus.CRVENA -> kviz.datumKraj
        KvizStatus.ZUTA -> kviz.datumPocetak
    }
}