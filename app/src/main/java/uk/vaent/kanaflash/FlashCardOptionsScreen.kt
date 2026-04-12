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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import uk.vaent.kanaflash.config.FlashCardOptions
import uk.vaent.kanaflash.ui.component.CheckboxGroup
import uk.vaent.kanaflash.ui.component.YesNoRadio
import kotlin.reflect.KProperty1

@Composable
fun FlashCardOptionsScreen(
    options: MutableState<FlashCardOptions>,
    showFlashCards: () -> Unit,
    showHomeScreen: () -> Unit
) {
    val isCharsetValid = remember { mutableStateOf(true) }
    val isStyleValid = remember { mutableStateOf(true) }

    fun getPropertySetter(property: KProperty1<FlashCardOptions, Boolean>): (Boolean) -> Unit = {
        options.value = options.value.replace(property, it)
    }

    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(120.dp))

        TextOptions(options.value) { getPropertySetter(it) }
        Spacer(Modifier.height(20.dp))
        ObsoleteKanaOption(options.value) { getPropertySetter(it) }

        StartPlayingButton(options, isCharsetValid, isStyleValid, showFlashCards)
        PaddedButton("Back to home screen", showHomeScreen)
        ErrorIfInvalid(isCharsetValid, "Select at least one character set")
        ErrorIfInvalid(isStyleValid, "Select at least one style")
    }
}

@Composable
private fun TextOptions(
    options: FlashCardOptions,
    getPropertySetter: (KProperty1<FlashCardOptions, Boolean>) -> (Boolean) -> Unit
) {
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OptionCheckboxes(
            options,
            "Character sets",
            FlashCardOptions::hiragana,
            FlashCardOptions::katakana,
            getPropertySetter = getPropertySetter
        )
        OptionCheckboxes(
            options,
            "Text styles",
            FlashCardOptions::printed,
            FlashCardOptions::calligraphic,
            getPropertySetter = getPropertySetter
        )
    }
}

@Composable
fun OptionCheckboxes(
    options: FlashCardOptions,
    title: String,
    vararg properties: KProperty1<FlashCardOptions, Boolean>,
    getPropertySetter: (KProperty1<FlashCardOptions, Boolean>) -> (Boolean) -> Unit
) {
    CheckboxGroup(options, title, *properties) { getPropertySetter(it) }
}

@Composable
private fun ObsoleteKanaOption(
    options: FlashCardOptions,
    getPropertySetter: (KProperty1<FlashCardOptions, Boolean>) -> (Boolean) -> Unit
) {
    Row(
        Modifier
            .border(Dp.Hairline, MaterialTheme.colorScheme.onSurface)
            .padding(20.dp)
            .fillMaxWidth(0.8f),
        horizontalArrangement = Arrangement.Center
    ) {
        YesNoRadio(
            options,
            FlashCardOptions::includeObsolete,
            "Include obsolete kana?",
            getPropertySetter(FlashCardOptions::includeObsolete)
        )
    }
}

@Composable
private fun StartPlayingButton(
    options: MutableState<FlashCardOptions>,
    isCharsetValid: MutableState<Boolean>,
    isStyleValid: MutableState<Boolean>,
    showFlashCards: () -> Unit
) {
    PaddedButton("Start playing") {
        isCharsetValid.value = options.value.hiragana || options.value.katakana
        isStyleValid.value = options.value.printed || options.value.calligraphic
        if (isCharsetValid.value && isStyleValid.value) showFlashCards()
    }
}

@Composable
private fun PaddedButton(label: String, onClick: () -> Unit) {
    Row(Modifier.padding(20.dp)) {
        Button(onClick) {
            Text(label)
        }
    }
}

@Composable
private fun ErrorIfInvalid(validation: MutableState<Boolean>, text: String) {
    if (!validation.value) {
        Row(Modifier.padding(vertical = 20.dp)) {
            Text(
                text,
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
