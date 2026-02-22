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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uk.vaent.kanaflash.kana.Hiragana
import uk.vaent.kanaflash.kana.Kana
import uk.vaent.kanaflash.kana.Katakana
import uk.vaent.kanaflash.kana.Seion
import uk.vaent.kanaflash.ui.theme.KanaFlashTheme

private val cellSize: Dp = 24.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MainApp()
        }
    }
}

@Preview(showBackground = true, widthDp = 320, heightDp = 480)
@Composable
fun GreetingPreview() {
    MainApp()
}

@Composable
fun MainApp(modifier: Modifier = Modifier) {
    KanaFlashTheme {
        Scaffold(modifier) { innerPadding ->
            Surface(
                modifier = Modifier.padding(innerPadding),
                color = MaterialTheme.colorScheme.primary
            ) {
                Column {
                    Row {
                        Titles()
                    }
                    Row(Modifier.fillMaxHeight().padding(top = 10.dp)) {
                        GojuonTable(Hiragana)
                        GojuonTable(Katakana)
                    }
                }
            }
        }
    }
}

@Composable
fun Titles() {
        Column(
            Modifier.background(MaterialTheme.colorScheme.tertiary)
                .fillMaxWidth()
                .padding(vertical = 40.dp),
            verticalArrangement = Arrangement.Center
        ) {
            for (title in arrayOf("Kana Flash", "かなフラシ")) {
                Text(
                    title,
                    Modifier.align(Alignment.CenterHorizontally)
                        .padding(vertical = 12.dp),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
}

@Composable
private fun GojuonTable(seion: Seion) {
    Column(Modifier.padding(horizontal = 20.dp)) {
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
        Modifier.border(Dp.Hairline, Color(180, 180, 180))
            .height(cellSize)
            .width(cellSize * columns),
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            kana.value,
            Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodySmall
        )
    }
}
