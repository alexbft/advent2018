package day15part2

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day15part2KtTest {

    @Test
    fun solveFull1() {
        val result = solveFull(
            """
            #######
            #.G...#
            #...EG#
            #.#.#G#
            #..G#E#
            #.....#
            #######
        """.trimIndent().lines()
        )
        assertEquals(SolveResult(29, 172), result)
    }

    @Test
    fun solveFull3() {
        val result = solveFull(
            """
            #######
            #E..EG#
            #.#G.E#
            #E.##E#
            #G..#.#
            #..E#.#   
            #######  
        """.trimIndent().lines()
        )
        assertEquals(SolveResult(33, 948), result)
    }

    @Test
    fun solveFull4() {
        val result = solveFull(
            """
            #######   
            #E.G#.#
            #.#G..#
            #G.#.G#   
            #G..#.#
            #...E.#
            #######
        """.trimIndent().lines()
        )
        assertEquals(SolveResult(37, 94), result)
    }

    @Test
    fun solveFull5() {
        val result = solveFull(
            """
            #######   
            #.E...#   
            #.#..G#
            #.###.#   
            #E#G#G#   
            #...#G#
            ####### 
        """.trimIndent().lines()
        )
        assertEquals(SolveResult(39, 166), result)
    }

    @Test
    fun solveFull6() {
        val result = solveFull(
            """
            #########   
            #G......#
            #.E.#...#
            #..##..G#
            #...##..#   
            #...#...#
            #.G...G.#   
            #.....G.#   
            #########   
        """.trimIndent().lines()
        )
        assertEquals(SolveResult(30, 38), result)
    }
}