package day1part2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day1part2KtTest {

    @Test
    fun test1() {
        val result = solve(listOf(1, -1))
        assertEquals(0, result)
    }

    @Test
    fun test2() {
        val result = solve(listOf(3, 3, 4, -2, -4))
        assertEquals(10, result)
    }

    @Test
    fun test3() {
        val result = solve(listOf(-6, 3, 8, 5, -6))
        assertEquals(5, result)
    }

    @Test
    fun test4() {
        val result = solve(listOf(7, 7, -2, -7, -4))
        assertEquals(14, result)
    }
}
