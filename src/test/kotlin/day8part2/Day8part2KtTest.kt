package day8part2

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day8part2KtTest {

    @Test
    fun testSolve() {
        val result = solve("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2")
        assertEquals(66, result)
    }
}
