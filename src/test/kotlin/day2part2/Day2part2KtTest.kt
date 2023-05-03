package day2part2

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day2part2KtTest {

    @Test
    fun testSolve() {
        val result = solve("""
            abcde
            fghij
            klmno
            pqrst
            fguij
            axcye
            wvxyz""".trimIndent().lines())
        assertEquals("fgij", result)
    }
}
