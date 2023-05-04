package day3part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day3part1KtTest {

    @Test
    fun testSolve() {
        val result = solve(listOf("#1 @ 1,3: 4x4", "#2 @ 3,1: 4x4", "#3 @ 5,5: 2x2"))
        assertEquals(4, result)
    }
}
