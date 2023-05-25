package day15part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day15part1KtTest {

    @Test
    fun solveFull1() {
        val result = solveFull("""
            #######
            #.G...#
            #...EG#
            #.#.#G#
            #..G#E#
            #.....#
            #######
        """.trimIndent().lines())
        assertEquals(SolveResult(47, 590), result)
    }

    @Test
    fun solveFull2() {
        val result = solveFull("""
            #######
            #G..#E#
            #E#E.E#
            #G.##.#
            #...#E#
            #...E.#
            #######
        """.trimIndent().lines())
        assertEquals(SolveResult(37, 982), result)
    }

    @Test
    fun solveFull3() {
        val result = solveFull("""
            #######
            #E..EG#
            #.#G.E#
            #E.##E#
            #G..#.#
            #..E#.#   
            #######  
        """.trimIndent().lines())
        assertEquals(SolveResult(46, 859), result)
    }

    @Test
    fun solveFull4() {
        val result = solveFull("""
            #######   
            #E.G#.#
            #.#G..#
            #G.#.G#   
            #G..#.#
            #...E.#
            #######
        """.trimIndent().lines())
        assertEquals(SolveResult(35, 793), result)
    }

    @Test
    fun solveFull5() {
        val result = solveFull("""
            #######   
            #.E...#   
            #.#..G#
            #.###.#   
            #E#G#G#   
            #...#G#
            ####### 
        """.trimIndent().lines())
        assertEquals(SolveResult(54, 536), result)
    }

    @Test
    fun solveFull6() {
        val result = solveFull("""
            #########   
            #G......#
            #.E.#...#
            #..##..G#
            #...##..#   
            #...#...#
            #.G...G.#   
            #.....G.#   
            #########   
        """.trimIndent().lines())
        assertEquals(SolveResult(20, 937), result)
    }

}