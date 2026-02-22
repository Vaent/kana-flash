package uk.vaent.kanaflash.kana

data class KanaImpl(override val value: String) : Kana {
    constructor(char: Char) : this(char.toString())
}
