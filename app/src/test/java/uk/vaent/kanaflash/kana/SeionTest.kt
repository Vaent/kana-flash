package uk.vaent.kanaflash.kana

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@RunWith(Parameterized::class)
class SeionTest(private val instance: Seion) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data() = listOf(Hiragana, Katakana)
    }

    @Test
    fun `getAllKana includes obsolete kana`() {
        val allKana = instance.getAllKana()
        assertTrue { allKana.contains(instance.waGyo.iDan) }
        assertTrue { allKana.contains(instance.waGyo.eDan) }
    }

    @Test
    fun `getAllKana excludes obsolete kana`() {
        val allKana = instance.getAllKana(includeObsolete = false)
        assertFalse { allKana.contains(instance.waGyo.iDan) }
        assertFalse { allKana.contains(instance.waGyo.eDan) }
    }
}
