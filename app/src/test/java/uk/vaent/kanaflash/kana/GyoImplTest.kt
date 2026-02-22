package uk.vaent.kanaflash.kana

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertFailsWith

class GyoImplTest {
    @Test
    fun createsGyoFromCharactersOfSuppliedString() {
        val gyo = GyoImpl.from("xy1ab")
        assertEquals("x", gyo.aDan.value)
        assertEquals("y", gyo.iDan.value)
        assertEquals("1", gyo.uDan.value)
        assertEquals("a", gyo.eDan.value)
        assertEquals("b", gyo.oDan.value)
    }

    @Test
    fun throwsForInputOfIncorrectLength() {
        assertFailsWith<IllegalArgumentException>(block = { GyoImpl.from("xyab") })
        assertFailsWith<IllegalArgumentException>(block = { GyoImpl.from("xxyyab") })
    }

    @Test
    fun returnsAllKanaInOrderSupplied() {
        val expectedKana = listOf(
            KanaImpl('x'),
            KanaImpl('y'),
            KanaImpl('1'),
            KanaImpl('a'),
            KanaImpl('b')
        )

        val gyo1 = GyoImpl.from("xy1ab")
        assertEquals(expectedKana, gyo1.getAllKana())

        val gyo2 = GyoImpl('x', 'y', '1', 'a', 'b')
        assertEquals(expectedKana, gyo2.getAllKana())
    }
}