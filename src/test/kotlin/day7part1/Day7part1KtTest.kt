package day7part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day7part1KtTest {

    @Test
    fun testSolve() {
        val result = solve("""
            Step C must be finished before step A can begin.
            Step C must be finished before step F can begin.
            Step A must be finished before step B can begin.
            Step A must be finished before step D can begin.
            Step B must be finished before step E can begin.
            Step D must be finished before step E can begin.
            Step F must be finished before step E can begin.
        """.trimIndent().lines())
        assertEquals("CABDFE", result)
    }
}
