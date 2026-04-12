package uk.vaent.kanaflash

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import uk.vaent.kanaflash.config.FlashCardOptions
import uk.vaent.kanaflash.kana.Hiragana
import uk.vaent.kanaflash.kana.Kana
import uk.vaent.kanaflash.kana.Katakana

@Composable
fun KanaFlashCard(
    options: MutableState<FlashCardOptions>,
    showOptions: () -> Unit
) {
    val (hiragana, katakana, sansSerif, serif, includeObsolete) = options.value

    val selectedKanaName = getSelectedKanaName(hiragana, katakana)
    val candidateKana = getCandidateKana(hiragana, katakana, includeObsolete)
    val fontFamily = getFontFamilyPicker(sansSerif, serif)

    val (currentKana, setCurrentKana) = remember { mutableStateOf(candidateKana.random()) }

    Text(currentKana.value, fontSize = 60.em, fontFamily = fontFamily())
    NextKanaButton(selectedKanaName, getNextKanaPicker(candidateKana, currentKana, setCurrentKana))
    Row(Modifier.padding(20.dp)) {
        Button(onClick = { showOptions() }) {
            Text("Back to options")
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

private fun getFontFamilyPicker(sansSerif: Boolean, serif: Boolean): () -> FontFamily =
    if (sansSerif && !serif) ({ FontFamily.SansSerif })
    else if (serif && !sansSerif) ({ FontFamily.Serif })
    else ({ arrayOf(FontFamily.Serif, FontFamily.SansSerif).random() })

private fun getNextKanaPicker(
    candidateKana: List<Kana>,
    currentKana: Kana,
    setCurrentKana: (Kana) -> Unit
): () -> Unit = {
    var nextKana = candidateKana.random()
    while (nextKana == currentKana) nextKana = candidateKana.random()
    setCurrentKana(nextKana)
}
