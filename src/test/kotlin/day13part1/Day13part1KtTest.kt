package day13part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day13part1KtTest {

    @Test
    fun testSolve() {
        val result = solve("""
            /->-\        
            |   |  /----\
            | /-+--+-\  |
            | | |  | v  |
            \-+-/  \-+--/
              \------/   
        """.trimIndent().lines())
        assertEquals(Point(7, 3), result)
    }
}
