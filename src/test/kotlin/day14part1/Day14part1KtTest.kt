package day14part1

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day14part1KtTest {

    @Test
    fun test1() {
        val expected = listOf(5, 1, 5, 8, 9, 1, 6, 7, 7, 9)
        assertEquals(expected, solve(9))
    }

    @Test
    fun test2() {
        val expected = listOf(0, 1, 2, 4, 5, 1, 5, 8, 9, 1)
        assertEquals(expected, solve(5))
    }

    @Test
    fun test3() {
        val expected = listOf(9, 2, 5, 1, 0, 7, 1, 0, 8, 5)
        assertEquals(expected, solve(18))
    }

    @Test
    fun test4() {
        val expected = listOf(5, 9, 4, 1, 4, 2, 9, 8, 8, 2)
        assertEquals(expected, solve(2018))
    }
}