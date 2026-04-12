package uk.vaent.kanaflash

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import uk.vaent.kanaflash.config.FlashCardOptions
import uk.vaent.kanaflash.kana.Hiragana
import uk.vaent.kanaflash.kana.Kana
import uk.vaent.kanaflash.kana.Katakana

@Composable
fun FlashCardsScreen(
    options: MutableState<FlashCardOptions>,
    showOptions: () -> Unit,
    showHomeScreen: () -> Unit
) {
    val (hiragana, katakana, printed, calligraphic, includeObsolete) = options.value

    val selectedKanaName = getSelectedKanaName(hiragana, katakana)
    val candidateKana = getCandidateKana(hiragana, katakana, includeObsolete)
    val fontFamily = getFontFamilyPicker(printed, calligraphic)

    val currentKanaString = rememberSaveable { mutableStateOf(candidateKana.random().value) }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(currentKanaString.value, fontSize = 60.em, fontFamily = fontFamily())
        NextKanaButton(selectedKanaName) { getNextKana(candidateKana, currentKanaString) }
        Row(Modifier.padding(20.dp)) {
            Button(onClick = { showOptions() }) {
                Text("Back to options")
            }
        }
        Row(Modifier.padding(20.dp)) {
            Button(onClick = { showHomeScreen() }) {
                Text("Back to home screen")
            }
        }
    }
}

@Composable
private fun NextKanaButton(
    selectedKanaName: String?,
    nextKanaPicker: () -> Unit
) {
    Row(Modifier.padding(20.dp)) {
        Button(onClick = nextKanaPicker) {
            Text("Show another $selectedKanaName")
        }
    }
}

private fun getSelectedKanaName(hiragana: Boolean, katakana: Boolean): String? =
    if (hiragana && !katakana) Hiragana.javaClass.simpleName
    else if (katakana && !hiragana) Katakana.javaClass.simpleName
    else "${Hiragana.javaClass.simpleName}/${Katakana.javaClass.simpleName}"

private fun getCandidateKana(
    hiragana: Boolean,
    katakana: Boolean,
    includeObsolete: Boolean
): List<Kana> =
    if (hiragana && !katakana) Hiragana.getAllKana(includeObsolete)
    else if (katakana && !hiragana) Katakana.getAllKana(includeObsolete)
    else Hiragana.getAllKana(includeObsolete).plus(Katakana.getAllKana(includeObsolete))

private fun getFontFamilyPicker(printed: Boolean, calligraphic: Boolean): () -> FontFamily =
    if (printed && !calligraphic) ({ FontFamily.SansSerif })
    else if (calligraphic && !printed) ({ FontFamily.Serif })
    else ({ arrayOf(FontFamily.Serif, FontFamily.SansSerif).random() })

private fun getNextKana(candidateKana: List<Kana>, currentKanaString: MutableState<String>) {
    var nextKana = candidateKana.random()
    while (nextKana.value == currentKanaString.value) nextKana = candidateKana.random()
    currentKanaString.value = nextKana.value
}
