package day9part1

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class CircleTest {

    @Test
    fun removeCurrent() {
        val circle = Circle()
        circle.add(0)
        circle.add(1)
        circle.add(2)
        circle.moveToPrev()
        assertEquals(1, circle.removeCurrent())
        assertEquals(2, circle.current())
    }
}
