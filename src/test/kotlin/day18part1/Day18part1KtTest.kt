package day18part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach

class Day18part1KtTest {
    lateinit var input: List<String>
    lateinit var inputChars: List<List<Char>>

    @BeforeEach
    fun setUp() {
        input = """
            .#.#...|#.
            .....#|##|
            .|..|...#.
            ..|#.....#
            #.#|||#|#|
            ...#.||...
            .|....|...
            ||...#|.#|
            |.||||..|.
            ...#.|..|.
        """.trimIndent().lines()
        inputChars = input.map { it.toList() }
    }

    @Test
    fun testSolve() {
        val expected = """
            .||##.....
            ||###.....
            ||##......
            |##.....##
            |##.....##
            |##....##|
            ||##.####|
            ||#####|||
            ||||#|||||
            ||||||||||
        """.trimIndent().lines().map { it.toList() }
        assertEquals(AreaMap(expected), solveFull(AreaMap(inputChars), 10))
        assertEquals(1147, solve(input))
    }

    @Test
    fun testOneIter() {
        val expected = """
            .......##.
            ......|###
            .|..|...#.
            ..|#||...#
            ..##||.|#|
            ...#||||..
            ||...|||..
            |||||.||.|
            ||||||||||
            ....||..|.
        """.trimIndent().lines().map { it.toList() }
        assertEquals(AreaMap(expected), solveFull(AreaMap(inputChars), 1))
    }
}