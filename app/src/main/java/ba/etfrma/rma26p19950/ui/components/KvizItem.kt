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
import ba.etfrma.rma26p19950.config.Config
import ba.etfrma.rma26p19950.util.odrediStatus
import ba.etfrma.rma26p19950.util.prikazniDatum

@Composable
fun KvizItem(kviz: Kviz, onClick: () -> Unit = {}) {
    val status = odrediStatus(kviz)
    val datum = prikazniDatum(kviz)

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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = status.opis,
                        fontSize = 13.sp,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Image(
                        painter = painterResource(id = status.drawable),
                        contentDescription = status.opis,
                        modifier = Modifier
                            .size(16.dp)
                            .testTag("kviz_status_icon")
                            .semantics { contentDescription = status.opis }
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = kviz.naziv, fontSize = 14.sp)
            Text(text = datum.format(Config.DATUM_FORMAT), fontSize = 13.sp)

            Spacer(modifier = Modifier.height(4.dp))

            Text(text = "${kviz.trajanje} min", fontSize = 13.sp)
        }
    }
}