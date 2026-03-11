package uk.vaent.kanaflash.kana

interface Seion {
    val boon: Gyo
    val kaGyo: Gyo
    val saGyo: Gyo
    val taGyo: Gyo
    val naGyo: Gyo
    val haGyo: Gyo
    val maGyo: Gyo
    val yaGyo: Gyo
    val raGyo: Gyo
    val waGyo: Gyo
    val hatsuon: Kana

    fun getAllGyo(): List<Gyo> {
        return listOf(boon, kaGyo, saGyo, taGyo, naGyo, haGyo, maGyo, yaGyo, raGyo, waGyo)
    }

    fun getAllKana(includeObsolete: Boolean = true): List<Kana> {
        return arrayOf(boon, kaGyo, saGyo, taGyo, naGyo, haGyo, maGyo, yaGyo, raGyo, waGyo)
            .flatMap(Gyo::getAllKana)
            .filterNotNull()
            .filter { includeObsolete || (it != waGyo.iDan && it != waGyo.eDan) }
            .plus(hatsuon)
    }
}

object Hiragana : Seion {
    override val boon: Gyo = GyoImpl.from("あいうえお")
    override val kaGyo: Gyo = GyoImpl.from("かきくけこ")
    override val saGyo: Gyo = GyoImpl.from("さしすせそ")
    override val taGyo: Gyo = GyoImpl.from("たちつてと")
    override val naGyo: Gyo = GyoImpl.from("なにぬねの")
    override val haGyo: Gyo = GyoImpl.from("はひふへほ")
    override val maGyo: Gyo = GyoImpl.from("まみむめも")
    override val yaGyo: Gyo = GyoImpl.from("や ゆ よ")
    override val raGyo: Gyo = GyoImpl.from("らりるれろ")
    override val waGyo: Gyo = GyoImpl.from("わゐ ゑを")
    override val hatsuon: Kana = KanaImpl('ん')

    override fun toString(): String {
        return this.javaClass.simpleName
    }
}

object Katakana : Seion {
    override val boon: Gyo = GyoImpl.from("アイウエオ")
    override val kaGyo: Gyo = GyoImpl.from("カキクケコ")
    override val saGyo: Gyo = GyoImpl.from("サシスセソ")
    override val taGyo: Gyo = GyoImpl.from("タチツテト")
    override val naGyo: Gyo = GyoImpl.from("ナニヌネノ")
    override val haGyo: Gyo = GyoImpl.from("ハヒフヘホ")
    override val maGyo: Gyo = GyoImpl.from("マミムメモ")
    override val yaGyo: Gyo = GyoImpl.from("ヤ ユ ヨ")
    override val raGyo: Gyo = GyoImpl.from("ラリルレロ")
    override val waGyo: Gyo = GyoImpl.from("ワヰ ヱヲ")
    override val hatsuon: Kana = KanaImpl('ン')

    override fun toString(): String {
        return this.javaClass.simpleName
    }
}
