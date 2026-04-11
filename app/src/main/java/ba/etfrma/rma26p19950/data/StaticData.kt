package ba.etfrma.rma26p19950.data

import ba.etfrma.rma26p19950.model.Grupa
import ba.etfrma.rma26p19950.model.Kviz
import ba.etfrma.rma26p19950.model.Predmet
import java.time.LocalDateTime


private val REFERENTNO_VRIJEME = LocalDateTime.of(2021, 4, 10, 12, 0, 0)

object GrupaStaticData {
    private val grupe = listOf(
        Grupa("Grupa1", "RMA"),
        Grupa("Grupa2", "RMA"),
        Grupa("Grupa1", "DM"),
        Grupa("Grupa2", "DM"),
        Grupa("Grupa1", "IM"),
        Grupa("Grupa2", "IM"),
        Grupa("Grupa1", "OS"),
        Grupa("Grupa2", "OS")
    )

    fun getGrupaFromPredmet(nazivPredmeta: String): List<Grupa> =
        grupe.filter { it.nazivPredmeta == nazivPredmeta }
}

object PredmetStaticData {
    private val sviPredmeti = listOf(
        Predmet("RMA", 3),
        Predmet("DM", 1),
        Predmet("IM", 2),
        Predmet("OS", 2)
    )

    private val upisaniPredmeti = mutableListOf(
        Predmet("RMA", 3)
    )

    fun getAll(): List<Predmet> = sviPredmeti

    fun getUpisani(): List<Predmet> = upisaniPredmeti.toList()

    fun upisi(predmet: Predmet) {
        if (!upisaniPredmeti.any { it.naziv == predmet.naziv }) {
            upisaniPredmeti.add(predmet)
        }
    }
}

object KvizStaticData {
    private val korisnikovaGrupa = mutableMapOf(
        "RMA" to "Grupa1"
    )

    fun upisi(nazivPredmeta: String, nazivGrupe: String) {
        korisnikovaGrupa[nazivPredmeta] = nazivGrupe
    }

    private val sviKvizovi = listOf(

        Kviz(
            naziv = "Kviz 1",
            nazivPredmeta = "RMA",
            datumPocetak = LocalDateTime.of(2021, 3, 10, 10, 0),
            datumKraj = LocalDateTime.of(2021, 3, 10, 10, 30),
            datumRada = LocalDateTime.of(2021, 3, 10, 10, 5),
            trajanje = 30,
            nazivGrupe = "Grupa1",
            osvojeniBodovi = 1.5f
        ),

        Kviz(
            naziv = "Kviz 2",
            nazivPredmeta = "RMA",
            datumPocetak = LocalDateTime.of(2021, 4, 10, 10, 0),
            datumKraj = LocalDateTime.of(2021, 4, 10, 14, 0),
            datumRada = null,
            trajanje = 2,
            nazivGrupe = "Grupa1",
            osvojeniBodovi = null
        ),

        Kviz(
            naziv = "Kviz 3",
            nazivPredmeta = "RMA",
            datumPocetak = LocalDateTime.of(2021, 5, 1, 10, 0),
            datumKraj = LocalDateTime.of(2021, 5, 1, 10, 30),
            datumRada = null,
            trajanje = 5,
            nazivGrupe = "Grupa1",
            osvojeniBodovi = null
        ),

        Kviz(
            naziv = "Kviz 0",
            nazivPredmeta = "RMA",
            datumPocetak = LocalDateTime.of(2021, 3, 9, 10, 0),
            datumKraj = LocalDateTime.of(2021, 3, 9, 10, 30),
            datumRada = null,
            trajanje = 2,
            nazivGrupe = "Grupa1",
            osvojeniBodovi = null
        ),

        Kviz(
            naziv = "Kviz 1",
            nazivPredmeta = "DM",
            datumPocetak = LocalDateTime.of(2021, 3, 10, 10, 0),
            datumKraj = LocalDateTime.of(2021, 3, 10, 10, 30),
            datumRada = LocalDateTime.of(2021, 3, 10, 10, 3),
            trajanje = 5,
            nazivGrupe = "Grupa1",
            osvojeniBodovi = 2.5f
        ),

        Kviz(
            naziv = "Kviz 2",
            nazivPredmeta = "DM",
            datumPocetak = LocalDateTime.of(2021, 4, 8, 10, 0),
            datumKraj = LocalDateTime.of(2021, 4, 8, 10, 30),
            datumRada = null,
            trajanje = 5,
            nazivGrupe = "Grupa2",
            osvojeniBodovi = null
        ),

        Kviz(
            naziv = "Kviz 1",
            nazivPredmeta = "IM",
            datumPocetak = LocalDateTime.of(2021, 5, 10, 10, 0),
            datumKraj = LocalDateTime.of(2021, 5, 10, 10, 30),
            datumRada = null,
            trajanje = 5,
            nazivGrupe = "Grupa1",
            osvojeniBodovi = null
        ),

        Kviz(
            naziv = "Kviz 2",
            nazivPredmeta = "IM",
            datumPocetak = LocalDateTime.of(2021, 4, 5, 10, 0),
            datumKraj = LocalDateTime.of(2021, 4, 5, 10, 30),
            datumRada = null,
            trajanje = 5,
            nazivGrupe = "Grupa2",
            osvojeniBodovi = null
        ),

        Kviz(
            naziv = "Kviz 1",
            nazivPredmeta = "OS",
            datumPocetak = LocalDateTime.of(2021, 3, 20, 10, 0),
            datumKraj = LocalDateTime.of(2021, 3, 20, 10, 30),
            datumRada = null,
            trajanje = 5,
            nazivGrupe = "Grupa1",
            osvojeniBodovi = null
        ),

        Kviz(
            naziv = "Kviz 2",
            nazivPredmeta = "OS",
            datumPocetak = LocalDateTime.of(2021, 6, 1, 10, 0),
            datumKraj = LocalDateTime.of(2021, 6, 1, 10, 30),
            datumRada = null,
            trajanje = 5,
            nazivGrupe = "Grupa2",
            osvojeniBodovi = null
        )
    )

    fun getReferentDate(): LocalDateTime = REFERENTNO_VRIJEME

    fun getUpisani(): List<Kviz> {
        val upisaniNazivi = PredmetStaticData.getUpisani().map { it.naziv }
        return sviKvizovi.filter { kviz ->
            val grupa = korisnikovaGrupa[kviz.nazivPredmeta]
            kviz.nazivPredmeta in upisaniNazivi && kviz.nazivGrupe == grupa
        }.sortedBy { it.datumPocetak }
    }

    fun getAll(): List<Kviz> = sviKvizovi.sortedBy { it.datumPocetak }

    fun getDone(): List<Kviz> = getUpisani().filter { it.datumRada != null }

    fun getFuture(): List<Kviz> =
        getUpisani().filter { it.datumPocetak.isAfter(REFERENTNO_VRIJEME) }

    fun getNotTaken(): List<Kviz> =
        getUpisani().filter {
            it.datumKraj.isBefore(REFERENTNO_VRIJEME) && it.datumRada == null
        }
}