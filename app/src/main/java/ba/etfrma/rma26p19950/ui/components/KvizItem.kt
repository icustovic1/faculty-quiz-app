package ba.etfrma.rma26p19950.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ba.etf.rma26.projekat.data.models.Kviz
import ba.etfrma.rma26p19950.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private val REFERENTNO_VRIJEME = LocalDateTime.now()
private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

private fun getStatusDrawable(kviz: Kviz): Int {
    if (kviz.datumRada != null) return R.drawable.plava
    val ref = REFERENTNO_VRIJEME
    return when {
        kviz.datumPocetka.isAfter(ref) -> R.drawable.zuta
        kviz.datumKraj.isBefore(ref) -> R.drawable.crvena
        else -> R.drawable.zelena
    }
}

private fun getStatusOpis(kviz: Kviz): String {
    if (kviz.datumRada != null) return "Plava"
    val ref = REFERENTNO_VRIJEME
    return when {
        kviz.datumPocetka.isAfter(ref) -> "Žuta"
        kviz.datumKraj.isBefore(ref) -> "Crvena"
        else -> "Zelena"
    }
}

private fun getDisplayDatum(kviz: Kviz): LocalDateTime {
    return when {
        kviz.datumRada != null -> kviz.datumRada
        kviz.datumPocetka.isAfter(REFERENTNO_VRIJEME) -> kviz.datumPocetka
        else -> kviz.datumKraj
    }
}

@Composable
fun KvizItem(kviz: Kviz, onClick: () -> Unit = {}) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .testTag("kviz_item_${kviz.naziv}")
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = kviz.nazivPredmeta ?: "",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Image(
                    painter = painterResource(id = getStatusDrawable(kviz)),
                    contentDescription = getStatusOpis(kviz),
                    modifier = Modifier
                        .size(16.dp)
                        .testTag("kviz_status_icon")
                        .semantics {
                            contentDescription = getStatusOpis(kviz)
                        }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = kviz.naziv, fontSize = 14.sp)
            Text(text = getDisplayDatum(kviz).format(dateFormatter), fontSize = 13.sp)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "${kviz.trajanje} min", fontSize = 13.sp)
        }
    }
}