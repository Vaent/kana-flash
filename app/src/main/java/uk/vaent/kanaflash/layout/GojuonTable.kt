package uk.vaent.kanaflash.layout

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uk.vaent.kanaflash.kana.Kana
import uk.vaent.kanaflash.kana.Seion

private val cellSize: Dp = 36.dp

@Composable
fun GojuonTableWithButton(
    clickHandler: (Seion) -> Unit, seion: Seion
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Button(
            modifier = Modifier.border(Dp.Hairline, Color.White),
            onClick = { clickHandler(seion) }
        ) {
            Text(seion.javaClass.simpleName)
        }
        GojuonTable(seion)
    }
}

@Composable
fun GojuonTable(seion: Seion) {
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
private fun GridCell(kana: Kana?, columns: Int = 1) {
    Column(
        Modifier
            .border(Dp.Hairline, Color(180, 180, 180))
            .height(cellSize)
            .width(cellSize * columns),
        verticalArrangement = Arrangement.Center,
    ) {
        if (kana != null) {
            Text(
                kana.value,
                Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}
