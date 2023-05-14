package day11part1

data class Point(val x: Int, val y: Int)

private class Grid(val width: Int, val height: Int) {
    private val cells = Array(width * height) { 0 }

    private fun checkInside(x: Int, y: Int) {
        if (x !in 0 until width || y !in 0 until height) {
            throw Exception("out of bounds")
        }
    }

    fun get(x: Int, y: Int): Int {
        checkInside(x, y)
        return cells[y * width + x]
    }

    fun set(x: Int, y: Int, value: Int) {
        checkInside(x, y)
        cells[y * width + x] = value
    }
}

fun power(x: Int, y: Int, serialNo: Int): Int {
    val rackId = x + 10
    val total = ((rackId * y) + serialNo) * rackId
    val hundreds = (total % 1000) / 100
    return hundreds - 5
}

fun solve(serialNo: Int): Point {
    val grid = Grid(300, 300)
    for (y in 0 until grid.height) {
        for (x in 0 until grid.width) {
            grid.set(x, y, power(x + 1, y + 1, serialNo))
        }
    }
    val allPoints = (0 until grid.height - 2).flatMap { y ->
        (0 until grid.width - 2).map { x ->
            Point(x, y)
        }
    }
    return allPoints.maxBy { (x0, y0) ->
        var sum = 0
        for (y in y0..y0 + 2) {
            for (x in x0..x0 + 2) {
                sum += grid.get(x, y)
            }
        }
        sum
    }.let { (x, y) -> Point(x + 1, y + 1) }
}

fun main() {
    val (x, y) = solve(9221)
    println("$x,$y")
}
