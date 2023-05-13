package day6part2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day6part2KtTest {

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
            ),
            32
        )
        assertEquals(16, result)
    }
}
