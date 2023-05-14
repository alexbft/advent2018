package day11part1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day11part1KtTest {

    @Test
    fun power1() {
        assertEquals(-5, power(122, 79, 57))
    }

    @Test
    fun power2() {
        assertEquals(0, power(217, 196, 39))
    }

    @Test
    fun power3() {
        assertEquals(4, power(101, 153, 71))
    }

    @Test
    fun solve1() {
        assertEquals(Point(33, 45), solve(18))
    }

    @Test
    fun solve2() {
        assertEquals(Point(21, 61), solve(42))
    }
}
