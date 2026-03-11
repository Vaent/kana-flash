package uk.vaent.kanaflash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import uk.vaent.kanaflash.kana.Hiragana
import uk.vaent.kanaflash.kana.Katakana
import uk.vaent.kanaflash.layout.GojuonTable
import uk.vaent.kanaflash.ui.theme.KanaFlashTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainApp()
        }
    }
}

// For initial development (personal use) only "SD vertical" resolution is targeted.
// Support for other layouts/screen sizes is needed if this app will be shared publicly.
//@Preview(name="Small", showBackground = true, widthDp = 320, heightDp = 480)
@Preview(name="SD", showBackground = true, widthDp = 480, heightDp = 720)
//@Preview(name="FHD", showBackground = true, widthDp = 1080, heightDp = 1920)
@Composable
fun VerticalPreview() {
    MainApp()
}

enum class View {
    HOME,
    FLASH_CARDS
}

@Composable
fun MainApp(modifier: Modifier = Modifier) {
    val (view, setView) = remember { mutableStateOf(View.HOME) }
    KanaFlashTheme {
        Surface(color = MaterialTheme.colorScheme.surface) {
            if (view == View.HOME) {
                HomeScreen(showFlashCards = { setView(View.FLASH_CARDS) })
            }
            else if (view == View.FLASH_CARDS) {
                FlashCards(showHomeScreen = { setView(View.HOME) })
            }
        }
    }
}

@Composable
private fun HomeScreen(showFlashCards: () -> Unit) {
    Column {
        Row {
            Titles()
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { showFlashCards() }) {
                Text("Set up flash cards")
            }
        }
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.Top
        ) {
            GojuonTables()
        }
    }
}

@Composable
fun Titles() {
    Surface(color = MaterialTheme.colorScheme.surfaceVariant) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (title in arrayOf("Kana Flash", "かなフラシ")) {
                Text(
                    title,
                    Modifier.padding(vertical = 12.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
private fun GojuonTables() {
    GojuonTable(Hiragana)
    GojuonTable(Katakana)
}
