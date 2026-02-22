package uk.vaent.kanaflash.kana

class KanaImpl : Kana {
    override val value: String

    constructor(char: Char) {
        this.value = char.toString()
    }
}
