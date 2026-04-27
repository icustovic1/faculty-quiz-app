/*package ba.etfrma.rma26p19950

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ba.etfrma.rma26p19950.ui.screen.KvizScreen
import ba.etfrma.rma26p19950.ui.theme.RMA26P19950Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RMA26P19950Theme {
                KvizScreen()
            }
        }
    }
}*/

package ba.etfrma.rma26p19950

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ba.etfrma.rma26p19950.navigation.NavGraph
import ba.etfrma.rma26p19950.ui.theme.RMA26P19950Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RMA26P19950Theme {
                NavGraph()
            }
        }
    }
}