package uk.vaent.kanaflash

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import uk.vaent.kanaflash.kana.Hiragana
import uk.vaent.kanaflash.kana.Katakana

@Composable
fun FlashCards(showHomeScreen: () -> Unit) {
    val (playing, setPlaying) = remember { mutableStateOf(false) }
    val (hiraganaSelected, setHiraganaSelected) = remember { mutableStateOf(true) }
    val (katakanaSelected, setKatakanaSelected) = remember { mutableStateOf(true) }

    Scaffold { innerPadding ->
        Surface(
            Modifier.padding(innerPadding),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (playing) {
                    KanaFlashCard(
                        hiraganaSelected,
                        katakanaSelected,
                        showOptions = { setPlaying(false) }
                    )
                } else {
                    Options(
                        hiraganaSelected,
                        setHiraganaSelected,
                        katakanaSelected,
                        setKatakanaSelected,
                        startPlaying = { setPlaying(true) }
                    )
                }
                Row(horizontalArrangement = Arrangement.Center) {
                    Button(
                        modifier = Modifier.border(Dp.Hairline, Color.White),
                        onClick = { showHomeScreen() }
                    ) {
                        Text("Back to home screen")
                    }
                }
            }
        }
    }
}

@Composable
private fun Options(
    hiraganaSelected: Boolean,
    setHiraganaSelected: (Boolean) -> Unit,
    katakanaSelected: Boolean,
    setKatakanaSelected: (Boolean) -> Unit,
    startPlaying: () -> Unit
) {
    val (errorText, setErrorText) = remember { mutableStateOf("") }

    Row(Modifier.padding(20.dp)) {
        Checkbox(
            checked = hiraganaSelected,
            onCheckedChange = { checked -> setHiraganaSelected(checked) },
            modifier = Modifier.border(Dp.Hairline, Color.White)
        )
        Text("Hiragana")
    }
    Row(Modifier.padding(20.dp)) {
        Checkbox(
            checked = katakanaSelected,
            onCheckedChange = { checked -> setKatakanaSelected(checked) },
            modifier = Modifier.border(Dp.Hairline, Color.White)
        )
        Text("Katakana")
    }
    Row(Modifier.padding(20.dp)) {
        Button(
            onClick = {
                if (hiraganaSelected || katakanaSelected) startPlaying()
                else setErrorText("At least one option must be selected")
            },
            modifier = Modifier.border(Dp.Hairline, Color.White)
        ) {
            Text("Start playing")
        }
    }
    Row(Modifier.padding(20.dp)) {
        Text(errorText)
    }
}

@Composable
fun KanaFlashCard(hiraganaSelected: Boolean, katakanaSelected: Boolean, showOptions: () -> Unit) {
    val selectedKanaName =
        if (hiraganaSelected && !katakanaSelected) Hiragana.javaClass.simpleName
        else if (katakanaSelected && !hiraganaSelected) Katakana.javaClass.simpleName
        else "${Hiragana.javaClass.simpleName}/${Katakana.javaClass.simpleName}"
    val candidateKana =
        if (hiraganaSelected && !katakanaSelected) Hiragana.getAllKana()
        else if (katakanaSelected && !hiraganaSelected) Katakana.getAllKana()
        else Hiragana.getAllKana().plus(Katakana.getAllKana())
    val (currentKana, setCurrentKana) = remember { mutableStateOf(candidateKana.random()) }

    Text(currentKana.value, fontSize = 60.em)
    Button(
        modifier = Modifier.border(Dp.Hairline, Color.White),
        onClick = { setCurrentKana(candidateKana.random()) }
    ) {
        Text("Show another $selectedKanaName")
    }
    Button(
        modifier = Modifier.border(Dp.Hairline, Color.White),
        onClick = { showOptions() }
    ) {
        Text("Back to options")
    }
}
