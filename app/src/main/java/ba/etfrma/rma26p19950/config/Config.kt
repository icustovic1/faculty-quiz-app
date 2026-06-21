package ba.etfrma.rma26p19950.config

import java.time.format.DateTimeFormatter

/**
 * Centralizovane UI konstante. Prije su format datuma i referentno vrijeme
 * bili hardkodirani na vise mjesta (KvizItem fiksni datum 2021, KvizViewModel now()),
 * sto je davalo neslaganje statusa kviza i filtera. Sada postoji jedan izvor istine.
 */
object Config {
    val DATUM_FORMAT: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
}
