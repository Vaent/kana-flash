package uk.vaent.kanaflash

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
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import uk.vaent.kanaflash.kana.Hiragana
import uk.vaent.kanaflash.kana.Katakana

@Composable
fun FlashCards(showHomeScreen: () -> Unit) {
    val (playing, setPlaying) = remember { mutableStateOf(false) }
    val hiraganaSelected = remember { mutableStateOf(true) }
    val katakanaSelected = remember { mutableStateOf(true) }
    val sansSerifSelected = remember { mutableStateOf(true) }
    val serifSelected = remember { mutableStateOf(true) }
    val includeObsolete = remember { mutableStateOf(true) }

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
                        hiraganaSelected.value,
                        katakanaSelected.value,
                        sansSerifSelected.value,
                        serifSelected.value,
                        includeObsolete.value,
                        showOptions = { setPlaying(false) }
                    )
                } else {
                    Options(
                        hiraganaSelected,
                        katakanaSelected,
                        sansSerifSelected,
                        serifSelected,
                        includeObsolete,
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
    hiraganaSelected: MutableState<Boolean>,
    katakanaSelected: MutableState<Boolean>,
    sansSerifSelected: MutableState<Boolean>,
    serifSelected: MutableState<Boolean>,
    includeObsolete: MutableState<Boolean>,
    startPlaying: () -> Unit
) {
    val (charsErrorText, setCharsErrorText) = remember { mutableStateOf("") }
    val (fontErrorText, setFontErrorText) = remember { mutableStateOf("") }

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Column(
            Modifier
                .border(Dp.Hairline, Color.White)
                .padding(20.dp)
        ) {
            Text("Character sets")
            Row(
                Modifier.padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = hiraganaSelected.value,
                    onCheckedChange = { hiraganaSelected.value = it },
            modifier = Modifier.border(Dp.Hairline, Color.White)
                )
                Spacer(Modifier.width(5.dp))
                Text("Hiragana")
            }
            Row(
                Modifier.padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = katakanaSelected.value,
                    onCheckedChange = { katakanaSelected.value = it },
            modifier = Modifier.border(Dp.Hairline, Color.White)
                )
                Spacer(Modifier.width(5.dp))
                Text("Katakana")
            }
        }
        Column(
            Modifier
                .border(Dp.Hairline, Color.White)
                .padding(20.dp)
        ) {
            Text("Text styles")
            Row(
                Modifier.padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = sansSerifSelected.value,
                    onCheckedChange = { sansSerifSelected.value = it },
            modifier = Modifier.border(Dp.Hairline, Color.White)
                )
                Spacer(Modifier.width(5.dp))
                Text("Printed")
            }
            Row(
                Modifier.padding(vertical = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = serifSelected.value,
                    onCheckedChange = { serifSelected.value = it },
            modifier = Modifier.border(Dp.Hairline, Color.White)
                )
                Spacer(Modifier.width(5.dp))
                Text("Calligraphic")
            }
        }
    }
    Spacer(Modifier.height(20.dp))
    Row(
        Modifier
            .border(Dp.Hairline, Color.White)
            .padding(20.dp)
            .fillMaxWidth(0.8f),
        horizontalArrangement = Arrangement.Center
    ) {
        Column {
            Text("Include obsolete kana?")
            Row {
                Text("Yes")
                RadioButton(
                    selected = includeObsolete.value,
                    onClick = { includeObsolete.value = true },
                    colors = RadioButtonColors(
                        selectedColor = Color.White,
                        unselectedColor = Color.White,
                        Color.Gray,
                        Color.Gray
                    )
                )
                Spacer(Modifier.width(15.dp))
                Text("No")
                RadioButton(
                    selected = !includeObsolete.value,
                    onClick = { includeObsolete.value = false },
                    colors = RadioButtonColors(
                        selectedColor = Color.White,
                        unselectedColor = Color.White,
                        Color.Gray,
                        Color.Gray
                    )
                )
            }
        }
    }
    Row(Modifier.padding(vertical = 20.dp)) {
        Text(charsErrorText)
    }
    Row(Modifier.padding(vertical = 20.dp)) {
        Text(fontErrorText)
    }
    Row(Modifier.padding(20.dp)) {
        Button(
            onClick = {
                val isCharSelectionValid = hiraganaSelected.value || katakanaSelected.value
                val isFontSelectionValid = sansSerifSelected.value || serifSelected.value
                setCharsErrorText(if (isCharSelectionValid) "" else "Select at least one character set")
                setFontErrorText(if (isFontSelectionValid) "" else "Select at least one style")
                if (isCharSelectionValid && isFontSelectionValid) startPlaying()
            },
            modifier = Modifier.border(Dp.Hairline, Color.White)
        ) {
            Text("Start playing")
        }
    }
}

@Composable
fun KanaFlashCard(
    hiragana: Boolean,
    katakana: Boolean,
    sansSerif: Boolean,
    serif: Boolean,
    includeObsolete: Boolean,
    showOptions: () -> Unit
) {
    val selectedKanaName =
        if (hiragana && !katakana) Hiragana.javaClass.simpleName
        else if (katakana && !hiragana) Katakana.javaClass.simpleName
        else "${Hiragana.javaClass.simpleName}/${Katakana.javaClass.simpleName}"
    val candidateKana =
        if (hiragana && !katakana) Hiragana.getAllKana(includeObsolete)
        else if (katakana && !hiragana) Katakana.getAllKana(includeObsolete)
        else Hiragana.getAllKana(includeObsolete).plus(Katakana.getAllKana(includeObsolete))

    val fontFamily: () -> FontFamily =
        if (sansSerif && !serif) ({ FontFamily.SansSerif })
        else if (serif && !sansSerif) ({ FontFamily.Serif })
        else ({ arrayOf(FontFamily.Serif, FontFamily.SansSerif).random() })

    val (currentKana, setCurrentKana) = remember { mutableStateOf(candidateKana.random()) }

    Text(currentKana.value, fontSize = 60.em, fontFamily = fontFamily())
    Button(
        modifier = Modifier.border(Dp.Hairline, Color.White),
        onClick = {
            var nextKana = candidateKana.random()
            while (nextKana == currentKana) nextKana = candidateKana.random()
            setCurrentKana(nextKana)
        }
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
