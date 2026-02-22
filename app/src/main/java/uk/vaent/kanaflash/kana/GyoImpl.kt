package uk.vaent.kanaflash.kana

class GyoImpl : Gyo {
    companion object {
        /**
         * Creates a [GyoImpl] containing a [Kana] for each character of the supplied String.
         * Note: characters are assumed to follow gojuon ordering (-a/-i/-u/-e/-o)
         * rather than standard Latin ordering.
         *
         * @param chars an ordered sequence of the 5 kana in a gyo of the gojuon.
         * @throws IllegalArgumentException if the argument doesn't contain exactly 5 characters.
         */
        fun from(chars: String): GyoImpl {
            if (chars.length != 5) {
                throw IllegalArgumentException("Expected 5 characters, received ${chars.length}")
            }
            return GyoImpl(chars[0], chars[1], chars[2], chars[3], chars[4])
        }
    }

    override val aDan: Kana
    override val iDan: Kana
    override val uDan: Kana
    override val eDan: Kana
    override val oDan: Kana
    
    constructor(aDan: Char, iDan: Char, uDan: Char, eDan: Char, oDan: Char) {
        this.aDan = KanaImpl(aDan)
        this.iDan = KanaImpl(iDan)
        this.uDan = KanaImpl(uDan)
        this.eDan = KanaImpl(eDan)
        this.oDan = KanaImpl(oDan)
    }
    
    override fun getAllKana(): List<Kana> = listOf(aDan, iDan, uDan, eDan, oDan)
}
