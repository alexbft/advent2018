package day16part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class Day16part1KtTest {

    @Test
    fun testGuessCommands() {
        val result = guessCommands(instruction = listOf(9, 2, 1, 2), before = listOf(3, 2, 1, 1), after = listOf(3, 2, 2, 1))
        assertEquals(setOf(Command.mulr, Command.addi, Command.seti), result.toSet())
    }
}