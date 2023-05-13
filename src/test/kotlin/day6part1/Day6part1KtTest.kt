package day6part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day6part1KtTest {

    @Test
    fun testSolve() {
        val result = solve(
            listOf(
                Point(1, 1),
                Point(1, 6),
                Point(8, 3),
                Point(3, 4),
                Point(5, 5),
                Point(8, 9),
            )
        )
        assertEquals(17, result)
    }
}
