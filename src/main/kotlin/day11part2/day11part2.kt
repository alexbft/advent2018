package day11part2

data class Point(val x: Int, val y: Int)
data class Square(val x: Int, val y: Int, val size: Int)

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

private fun getSum(sums: Grid, x: Int, y: Int): Int {
    if (x < 0 || y < 0) {
        return 0
    }
    return sums.get(x, y)
}

private fun calcSum(sums: Grid, x0: Int, y0: Int, size: Int): Int {
    val x1 = x0 + size - 1
    val y1 = y0 + size - 1
    return getSum(sums, x1, y1) - getSum(sums, x1, y0 - 1) - getSum(sums, x0 - 1, y1) + getSum(sums, x0 - 1, y0 - 1)
}

fun solve(serialNo: Int): Square {
    val grid = Grid(300, 300)
    for (y in 0 until grid.height) {
        for (x in 0 until grid.width) {
            grid.set(x, y, power(x + 1, y + 1, serialNo))
        }
    }
    val sums = Grid(grid.width, grid.height)
    for (y in 0 until sums.height) {
        var rowSum = 0
        for (x in 0 until sums.width) {
            rowSum += grid.get(x, y)
            val sumUp = if (y > 0) sums.get(x, y - 1) else 0
            sums.set(x, y, sumUp + rowSum)
        }
    }
    var maxSum = Int.MIN_VALUE
    var resSquare = Square(0, 0, 0)
    for (size in 1..grid.width) {
        for (y0 in 0..grid.height - size) {
            for (x0 in 0..grid.width - size) {
                val sum = calcSum(sums, x0, y0, size)
                if (maxSum < sum) {
                    maxSum = sum
                    resSquare = Square(x0 + 1, y0 + 1, size)
                }
            }
        }
    }
    return resSquare
}

fun main() {
    val (x, y, size) = solve(9221)
    println("$x,$y,$size")
}
