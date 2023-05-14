package day12part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day12part1KtTest {

    @Test
    fun testSolve() {
        val result = solve(
            "#..#.#..##......###...###", """
            ...## => #
            ..#.. => #
            .#... => #
            .#.#. => #
            .#.## => #
            .##.. => #
            .#### => #
            #.#.# => #
            #.### => #
            ##.#. => #
            ##.## => #
            ###.. => #
            ###.# => #
            ####. => #
        """.trimIndent().lines()
        )
        assertEquals(325, result)
    }
}
