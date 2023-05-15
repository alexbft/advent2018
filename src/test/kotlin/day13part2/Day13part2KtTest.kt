package day13part2

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day13part2KtTest {

    @Test
    fun testSolve() {
        val result = solve("""
            />-<\  
            |   |  
            | /<+-\
            | | | v
            \>+</ |
              |   ^
              \<->/
        """.trimIndent().lines())
        assertEquals(Point(6, 4), result)
    }
}
