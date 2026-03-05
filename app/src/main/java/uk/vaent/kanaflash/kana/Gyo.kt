package uk.vaent.kanaflash.kana

interface Gyo {
    val aDan: Kana?
    val iDan: Kana?
    val uDan: Kana?
    val eDan: Kana?
    val oDan: Kana?

    fun getAllKana(): List<Kana?>
}
