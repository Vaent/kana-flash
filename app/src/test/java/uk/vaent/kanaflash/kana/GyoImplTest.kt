package uk.vaent.kanaflash.kana

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

class GyoImplTest {
    @Test
    fun `permits null children in certain positions`() {
        val gyo = GyoImpl('a', null, null, null, 'o')

        assertEquals("a", gyo.aDan.value)
        assertNull(gyo.iDan)
        assertNull(gyo.uDan)
        assertNull(gyo.eDan)
        assertEquals("o", gyo.oDan.value)
    }

    @Test
    fun `creates gyo from characters of supplied string`() {
        val gyo = GyoImpl.from("xy1ab")
        assertEquals("x", gyo.aDan.value)
        assertEquals("y", gyo.iDan?.value)
        assertEquals("1", gyo.uDan?.value)
        assertEquals("a", gyo.eDan?.value)
        assertEquals("b", gyo.oDan.value)
    }

    @Test
    fun `converts spaces in input to null`() {
        val gyo = GyoImpl.from("a   o")

        assertEquals("a", gyo.aDan.value)
        assertNull(gyo.iDan)
        assertNull(gyo.uDan)
        assertNull(gyo.eDan)
        assertEquals("o", gyo.oDan.value)
    }

    @Test
    fun `throws for input of incorrect length`() {
        assertFailsWith<IllegalArgumentException>(block = { GyoImpl.from("xyab") })
        assertFailsWith<IllegalArgumentException>(block = { GyoImpl.from("xxyyab") })
    }

    @Test
    fun `returns all kana in order supplied`() {
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