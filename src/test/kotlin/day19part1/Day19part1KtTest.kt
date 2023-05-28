package day19part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day19part1KtTest {

    @Test
    fun testSolve() {
        val input = """
            #ip 0
            seti 5 0 1
            seti 6 0 2
            addi 0 1 0
            addr 1 2 3
            setr 1 0 0
            seti 8 0 4
            seti 9 0 5
        """.trimIndent().lines()
        val expected = listOf(6, 5, 6, 0, 0, 9)
        assertEquals(expected, solveFull(input).regs)
    }
}
