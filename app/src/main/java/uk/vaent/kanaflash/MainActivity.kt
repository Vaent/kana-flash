package uk.vaent.kanaflash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import uk.vaent.kanaflash.kana.Hiragana
import uk.vaent.kanaflash.kana.Katakana
import uk.vaent.kanaflash.kana.Seion
import uk.vaent.kanaflash.layout.GojuonTableWithButton
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

@Composable
fun MainApp(modifier: Modifier = Modifier) {
    KanaFlashTheme {
        Surface(
            color = MaterialTheme.colorScheme.primary
        ) {
            KanaFlashTitleScreen()
        }
    }
}

@Composable
private fun KanaFlashTitleScreen() {
    Column {
        Row {
            Titles()
        }
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            var selectedSet: Seion? by remember { mutableStateOf(null) }
            if (selectedSet == null) {
                GojuonTables { seion: Seion -> selectedSet = seion }
            } else {
                KanaFlashCard(selectedSet!!) { selectedSet = null }
            }
        }
    }
}

@Composable
private fun KanaFlashCard(selectedSet: Seion, resetFunction: () -> Unit) {
    val candidateKana = selectedSet.getAllKana()
    var currentKana by remember { mutableStateOf(candidateKana.random()) }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(currentKana.value, fontSize = 60.em)
        Button(
            modifier = Modifier.border(Dp.Hairline, Color.White),
            onClick = { currentKana = candidateKana.random() }
        ) {
            Text("Show another ${selectedSet.javaClass.simpleName.lowercase()}")
        }
        Button(
            modifier = Modifier.border(Dp.Hairline, Color.White),
            onClick = resetFunction
        ) {
            Text("Back to gojuon tables")
        }
    }
}

@Composable
fun Titles() {
    Column(
        Modifier
            .background(MaterialTheme.colorScheme.tertiary)
            .fillMaxWidth()
            .padding(vertical = 40.dp),
        verticalArrangement = Arrangement.Center
    ) {
        for (title in arrayOf("Kana Flash", "かなフラシ")) {
            Text(
                title,
                Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 12.dp),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
private fun GojuonTables(clickHandler: (Seion) -> Unit) {
    GojuonTableWithButton(clickHandler, Hiragana)
    GojuonTableWithButton(clickHandler, Katakana)
}
