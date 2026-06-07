package ba.etf.rma26.projekat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import ba.etf.rma26.projekat.data.repositories.AccountRepository
import ba.etfrma.rma26p19950.navigation.NavGraph
import ba.etfrma.rma26p19950.ui.theme.RMA26P19950Theme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        lifecycleScope.launch {
            AccountRepository.postaviHash("demo")
        }

        setContent {
            RMA26P19950Theme {
                NavGraph()
            }
        }
    }
}