package day5part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day5part1KtTest {

    @Test
    fun testSolve() {
        val result = solve("dabAcCaCBAcCcaDA")
        assertEquals(10, result)
    }
}
