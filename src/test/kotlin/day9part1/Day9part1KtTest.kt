package day9part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day9part1KtTest {

    @Test
    fun test1() {
        assertEquals(32, solve(9, 25))
    }

    @Test
    fun test2() {
        assertEquals(8317, solve(10, 1618))
    }

    @Test
    fun test3() {
        assertEquals(146373, solve(13, 7999))
    }

    @Test
    fun test4() {
        assertEquals(2764, solve(17, 1104))
    }

    @Test
    fun test5() {
        assertEquals(54718, solve(21, 6111))
    }

    @Test
    fun test6() {
        assertEquals(37305, solve(30, 5807))
    }
}
