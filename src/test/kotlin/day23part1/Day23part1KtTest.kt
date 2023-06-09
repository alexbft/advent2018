package day23part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day23part1KtTest {

    @Test
    fun testSolve() {
        val input = """
            pos=<0,0,0>, r=4
            pos=<1,0,0>, r=1
            pos=<4,0,0>, r=3
            pos=<0,2,0>, r=1
            pos=<0,5,0>, r=3
            pos=<0,0,3>, r=1
            pos=<1,1,1>, r=1
            pos=<1,1,2>, r=1
            pos=<1,3,1>, r=1
        """.trimIndent().lines()
        assertEquals(7, solve(input))
    }
}
