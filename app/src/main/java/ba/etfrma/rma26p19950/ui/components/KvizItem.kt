package ba.etfrma.rma26p19950.ui.components

import androidx.compose.foundation.Image
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
import ba.etfrma.rma26p19950.R
import ba.etfrma.rma26p19950.model.Kviz
import ba.etfrma.rma26p19950.util.KvizStatus
import ba.etfrma.rma26p19950.util.getDisplayDatum
import ba.etfrma.rma26p19950.util.getKvizStatus
import java.time.format.DateTimeFormatter

private val dateFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

@Composable
fun KvizItem(kviz: Kviz) {
    val status = getKvizStatus(kviz)
    val displayDatum = getDisplayDatum(kviz)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .testTag("kviz_item_${kviz.naziv}"),
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
                    text = kviz.nazivPredmeta,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Image(
                    painter = painterResource(id = statusToDrawable(status)),
                    contentDescription = status.contentDescription,
                    modifier = Modifier
                        .size(16.dp)
                        .testTag("kviz_status_icon")
                        .semantics {
                            contentDescription = status.contentDescription
                        }
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = kviz.naziv, fontSize = 14.sp)
            Text(text = displayDatum.format(dateFormatter), fontSize = 13.sp)

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "${kviz.trajanje} min", fontSize = 13.sp)
                kviz.osvojeniBodovi?.let {
                    Text(text = it.toString(), fontSize = 13.sp)
                }
            }
        }
    }
}

private fun statusToDrawable(status: KvizStatus): Int = when (status) {
    KvizStatus.PLAVA -> R.drawable.plava
    KvizStatus.ZELENA -> R.drawable.zelena
    KvizStatus.ZUTA -> R.drawable.zuta
    KvizStatus.CRVENA -> R.drawable.crvena
}