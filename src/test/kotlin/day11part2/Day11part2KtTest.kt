package day11part2

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day11part2KtTest {

    @Test
    fun solve1() {
        assertEquals(Square(90, 269, 16), solve(18))
    }

    @Test
    fun solve2() {
        assertEquals(Square(232, 251, 12), solve(42))
    }
}
