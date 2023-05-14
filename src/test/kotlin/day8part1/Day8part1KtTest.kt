package day8part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day8part1KtTest {

    @Test
    fun testSolve() {
        val result = solve("2 3 0 3 10 11 12 1 1 0 1 99 2 1 1 2")
        assertEquals(138, result)
    }
}
