package day14part2

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day14part2KtTest {

    @Test
    fun test1() {
        assertEquals(9, solve("51589"))
    }

    @Test
    fun test2() {
        assertEquals(5, solve("01245"))
    }

    @Test
    fun test3() {
        assertEquals(18, solve("92510"))
    }

    @Test
    fun test4() {
        assertEquals(2018, solve("59414"))
    }
}