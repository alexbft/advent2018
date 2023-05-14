package day9part1

import java.util.LinkedList

internal class Circle {
    private val list = LinkedList<Int>()
    private var cur = list.listIterator()

    fun add(element: Int) {
        cur.add(element)
    }

    fun current(): Int {
        val result = cur.previous()
        cur.next()
        return result
    }

    fun removeCurrent(): Int {
        val result = cur.previous()
        cur.remove()
        moveToNext()
        return result
    }

    fun moveToNext() {
        if (cur.hasNext()) {
            cur.next()
        } else {
            cur = list.listIterator()
            cur.next()
        }
    }

    fun moveToPrev() {
        cur.previous()
        if (!cur.hasPrevious()) {
            cur = list.listIterator(list.size)
        }
    }

    fun moveBack(steps: Int) {
        for (i in 0 until steps) {
            moveToPrev()
        }
    }
}

fun solve(players: Int, maxMarble: Int): Int {
    val circle = Circle()
    circle.add(0)
    var player = 0
    val playerScore = Array(players) { 0 }
    for (marble in 1..maxMarble) {
        if (marble % 23 == 0) {
            playerScore[player] += marble
            circle.moveBack(7)
            playerScore[player] += circle.removeCurrent()
        } else {
            circle.moveToNext()
            circle.add(marble)
        }
        player = (player + 1) % players
    }
    return playerScore.max()
}

fun main() {
    println(solve(441, 71032))
}
