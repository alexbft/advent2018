package day23part2

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day23part2KtTest {

    @Test
    fun solve() {
        val input = """
            pos=<10,12,12>, r=2
            pos=<12,14,12>, r=2
            pos=<16,12,12>, r=4
            pos=<14,14,14>, r=6
            pos=<50,50,50>, r=200
            pos=<10,10,10>, r=5
        """.trimIndent().lines()
        assertEquals(SolveResult(Point3(12, 12, 12), listOf(0, 1, 2, 3, 4)), solveFull(input))
    }

    @Test
    fun counter() {
        val input = """
            pos=<-1000,-1000,-1000>, r=1
            pos=<-1100,-1000,-1000>, r=1
            pos=<-1200,-1000,-1000>, r=1
            pos=<1000,1000,1000>, r=1
            pos=<1001,1000,1000>, r=1
        """.trimIndent().lines()
        assertEquals(SolveResult(Point3(1000, 1000, 1000), listOf(3, 4)), solveFull(input))
    }
}
