package uk.vaent.kanaflash.kana

class GyoImpl : Gyo {
    companion object {
        fun from(chars: String): GyoImpl {
            if (chars.length != 5) {
                throw IllegalArgumentException("Expected 5 characters, received ${chars.length}")
            }
            return GyoImpl(
                KanaImpl(chars[0]),
                KanaImpl(chars[1]),
                KanaImpl(chars[2]),
                KanaImpl(chars[3]),
                KanaImpl(chars[4])
            )
        }
    }

    override val aDan: Kana
    override val iDan: Kana
    override val uDan: Kana
    override val eDan: Kana
    override val oDan: Kana
    
    constructor(a: Kana, i: Kana, u: Kana, e: Kana, o: Kana) {
        this.aDan = a
        this.iDan = i
        this.uDan = u
        this.eDan = e
        this.oDan = o
    }
    
    override fun getAllKana(): List<Kana> = listOf(aDan, iDan, uDan, eDan, oDan)
}
