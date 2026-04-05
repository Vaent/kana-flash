package uk.vaent.kanaflash.config

data class FlashCardOptions(
    var hiragana: Boolean = true,
    val katakana: Boolean = true,
    val sansSerif: Boolean = true,
    val serif: Boolean = true,
    val includeObsolete: Boolean = true
)
