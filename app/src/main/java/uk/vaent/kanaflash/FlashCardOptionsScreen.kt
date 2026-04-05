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
import androidx.compose.material3.RadioButtonDefaults.colors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import uk.vaent.kanaflash.config.FlashCardOptions
import uk.vaent.kanaflash.kana.Hiragana
import uk.vaent.kanaflash.kana.Katakana

@Composable
fun FlashCardOptionsScreen(
    flashCardOptions: MutableState<FlashCardOptions>,
    showHomeScreen: () -> Unit
) {
    val (playing, setPlaying) = remember { mutableStateOf(false) }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (playing) {
            KanaFlashCard(
                flashCardOptions,
                showOptions = { setPlaying(false) }
            )
        } else {
            OptionsForm(
                flashCardOptions,
                startPlaying = { setPlaying(true) }
            )
        }
        Row(horizontalArrangement = Arrangement.Center) {
            Button(onClick = { showHomeScreen() }) {
                Text("Back to home screen")
            }
        }
    }
}

@Composable
private fun OptionsForm(
    options: MutableState<FlashCardOptions>,
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
                .border(Dp.Hairline, MaterialTheme.colorScheme.onSurface)
                .padding(20.dp)
        ) {
            Text("Character sets")
            CheckboxOption("Hiragana", options.value.hiragana) {
                options.value = options.value.copy(hiragana = it)
            }
            CheckboxOption("Katakana", options.value.katakana) {
                options.value = options.value.copy(katakana = it)
            }
        }
        Column(
            Modifier
                .border(Dp.Hairline, MaterialTheme.colorScheme.onSurface)
                .padding(20.dp)
        ) {
            Text("Text styles")
            CheckboxOption("Printed", options.value.sansSerif) {
                options.value = options.value.copy(sansSerif = it)
            }
            CheckboxOption("Calligraphic", options.value.serif) {
                options.value = options.value.copy(serif = it)
            }
        }
    }
    Spacer(Modifier.height(20.dp))
    Row(
        Modifier
            .border(Dp.Hairline, MaterialTheme.colorScheme.onSurface)
            .padding(20.dp)
            .fillMaxWidth(0.8f),
        horizontalArrangement = Arrangement.Center
    ) {
        Column {
            Text("Include obsolete kana?")
            Row {
                Text("Yes")
                RadioButton(
                    selected = options.value.includeObsolete,
                    onClick = { options.value = options.value.copy(includeObsolete = true) },
                    colors = colors(unselectedColor = MaterialTheme.colorScheme.secondary)
                )
                Spacer(Modifier.width(15.dp))
                Text("No")
                RadioButton(
                    selected = !options.value.includeObsolete,
                    onClick = { options.value = options.value.copy(includeObsolete = false) },
                    colors = colors(unselectedColor = MaterialTheme.colorScheme.secondary)
                )
            }
        }
    }
    Row(Modifier.padding(20.dp)) {
        Button(
            onClick = {
                val isCharSelectionValid = options.value.hiragana || options.value.katakana
                val isFontSelectionValid = options.value.sansSerif || options.value.serif
                setCharsErrorText(if (isCharSelectionValid) "" else "Select at least one character set")
                setFontErrorText(if (isFontSelectionValid) "" else "Select at least one style")
                if (isCharSelectionValid && isFontSelectionValid) startPlaying()
            },
        ) {
            Text("Start playing")
        }
    }
    Row(Modifier.padding(vertical = 20.dp)) {
        Text(charsErrorText)
    }
    Row(Modifier.padding(vertical = 20.dp)) {
        Text(fontErrorText)
    }
}

@Composable
private fun CheckboxOption(displayText: String, value: Boolean, valueSetter: (Boolean) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = value,
            onCheckedChange = { valueSetter(it) }
        )
        Spacer(Modifier.width(5.dp))
        Text(displayText)
    }
}

@Composable
fun KanaFlashCard(
    options: MutableState<FlashCardOptions>,
    showOptions: () -> Unit
) {
    val selectedKanaName =
        if (options.value.hiragana && !options.value.katakana) Hiragana.javaClass.simpleName
        else if (options.value.katakana && !options.value.hiragana) Katakana.javaClass.simpleName
        else "${Hiragana.javaClass.simpleName}/${Katakana.javaClass.simpleName}"
    val candidateKana =
        if (options.value.hiragana && !options.value.katakana) {
            Hiragana.getAllKana(options.value.includeObsolete)
        } else if (options.value.katakana && !options.value.hiragana) {
            Katakana.getAllKana(options.value.includeObsolete)
        } else {
            Hiragana.getAllKana(options.value.includeObsolete)
                .plus(Katakana.getAllKana(options.value.includeObsolete))
        }

    val fontFamily: () -> FontFamily =
        if (options.value.sansSerif && !options.value.serif) ({ FontFamily.SansSerif })
        else if (options.value.serif && !options.value.sansSerif) ({ FontFamily.Serif })
        else ({ arrayOf(FontFamily.Serif, FontFamily.SansSerif).random() })

    val (currentKana, setCurrentKana) = remember { mutableStateOf(candidateKana.random()) }

    Text(currentKana.value, fontSize = 60.em, fontFamily = fontFamily())
    Row(Modifier.padding(20.dp)) {
        Button(
            onClick = {
                var nextKana = candidateKana.random()
                while (nextKana == currentKana) nextKana = candidateKana.random()
                setCurrentKana(nextKana)
            }
        ) {
            Text("Show another $selectedKanaName")
        }
    }
    Row(Modifier.padding(20.dp)) {
        Button(onClick = { showOptions() }) {
            Text("Back to options")
        }
    }
}
