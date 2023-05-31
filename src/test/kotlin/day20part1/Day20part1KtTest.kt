package day20part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day20part1KtTest {

    @Test
    fun testSolveSimple() {
        assertEquals(3, solve("^WNE\$"))
    }

    @Test
    fun testSolve1() {
        assertEquals(23, solve("^ESSWWN(E|NNENN(EESS(WNSE|)SSS|WWWSSSSE(SW|NNNE)))\$"))
    }

    @Test
    fun testSolve2() {
        assertEquals(31, solve("^WSSEESWWWNW(S|NENNEEEENN(ESSSSW(NWSW|SSEN)|WSWWN(E|WWS(E|SS))))\$"))
    }
}
