package day17part2

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day17part2KtTest {

    @Test
    fun testSolve() {
        val result = solve(
            """
            x=495, y=2..7
            y=7, x=495..501
            x=501, y=3..7
            x=498, y=2..4
            x=506, y=1..2
            x=498, y=10..13
            x=504, y=10..13
            y=13, x=498..504
        """.trimIndent().lines()
        )
        assertEquals(29, result)
    }
}