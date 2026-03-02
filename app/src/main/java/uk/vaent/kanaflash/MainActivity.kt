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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import uk.vaent.kanaflash.kana.Kana
import uk.vaent.kanaflash.kana.Katakana
import uk.vaent.kanaflash.kana.Seion
import uk.vaent.kanaflash.ui.theme.KanaFlashTheme
import kotlin.random.Random

private val cellSize: Dp = 36.dp

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
@Preview(name="Small", showBackground = true, widthDp = 320, heightDp = 480)
@Preview(name="SD", showBackground = true, widthDp = 480, heightDp = 720)
@Preview(name="FHD", showBackground = true, widthDp = 1080, heightDp = 1920)
@Composable
fun VerticalPreview() {
    MainApp()
}

@Composable
fun MainApp(modifier: Modifier = Modifier) {
    KanaFlashTheme {
//        Scaffold(modifier) { innerPadding ->
            Surface(
//                modifier = Modifier.padding(innerPadding),
                color = MaterialTheme.colorScheme.primary
            ) {
                KanaFlashTitleScreen()
            }
//        }
    }
}

@Composable
private fun KanaFlashTitleScreen() {
    Column {
        Row {
            Titles()
        }
        Row(
            Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            var selectedSet: Seion? by remember { mutableStateOf(null) }
            if (selectedSet == null) {
                GojuonTables { seion: Seion -> selectedSet = seion }
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    if (Random.nextInt(50) == 0) {
                        Text(selectedSet!!.hatsuon.value, fontSize = 120.em)
                    } else {
                        val gyo = selectedSet!!.getAllGyo()[Random.nextInt(10)]
                        var kana = gyo.getAllKana()[Random.nextInt(5)]
                        while (kana.value == " ") kana = gyo.getAllKana()[Random.nextInt(5)]
                        Text(kana.value, fontSize = 60.em)
                    }
                    Button(onClick = { selectedSet = null }) {
                        Text("Reset")
                    }
                }
            }
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
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            modifier = Modifier.border(Dp.Hairline, Color.White),
            onClick = { clickHandler(Hiragana) }
        ) {
            Text("Hiragana")
        }
        GojuonTable(Hiragana)
    }
    Spacer(modifier = Modifier.width(10.dp))
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            modifier = Modifier.border(Dp.Hairline, Color.White),
            onClick = { clickHandler(Katakana) }
        ) {
            Text("Katakana")
        }
        GojuonTable(Katakana)
    }
}

@Composable
private fun GojuonTable(seion: Seion) {
    Column(Modifier.padding(vertical = cellSize / 2)) {
        seion.getAllGyo().forEach { gyo ->
            Row(Modifier.align(Alignment.CenterHorizontally)) {
                gyo.getAllKana().forEach { kana ->
                    GridCell(kana)
                }
            }
        }
        Row(Modifier.align(Alignment.CenterHorizontally)) {
            GridCell(seion.hatsuon, 5)
        }
    }
}

@Composable
private fun GridCell(kana: Kana, columns: Int = 1) {
    Column(
        Modifier
            .border(Dp.Hairline, Color(180, 180, 180))
            .height(cellSize)
            .width(cellSize * columns),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            kana.value,
            Modifier.align(Alignment.CenterHorizontally),
//            style = MaterialTheme.typography.bodyLarge,
//            fontSize = 6.em
        )
    }
}
