package uk.vaent.kanaflash.config

import kotlin.reflect.KProperty1

data class FlashCardOptions(
    val hiragana: Boolean = true,
    val katakana: Boolean = true,
    val printed: Boolean = true,
    val calligraphic: Boolean = true,
    val includeObsolete: Boolean = true
) {
    fun replace(property: KProperty1<FlashCardOptions, Boolean>, value: Boolean): FlashCardOptions {
        return when(property) {
            FlashCardOptions::hiragana -> this.copy(hiragana = value)
            FlashCardOptions::katakana -> this.copy(katakana = value)
            FlashCardOptions::printed -> this.copy(printed = value)
            FlashCardOptions::calligraphic -> this.copy(calligraphic = value)
            FlashCardOptions::includeObsolete -> this.copy(includeObsolete = value)
            else -> throw IllegalArgumentException("Invalid property specified")
        }
    }
}
