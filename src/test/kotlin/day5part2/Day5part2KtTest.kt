package day5part2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day5part2KtTest {

    @Test
    fun testSolve() {
        val result = solve("dabAcCaCBAcCcaDA")
        assertEquals(4, result)
    }
}
