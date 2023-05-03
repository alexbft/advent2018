package day2part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day2part1KtTest {

    @Test
    fun test1() {
        val result = solve(listOf("abcdef", "bababc", "abbcde", "abcccd", "aabcdd", "abcdee", "ababab"))
        assertEquals(12, result)
    }
}
