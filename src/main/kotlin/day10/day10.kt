package day10

import bootstrap.Bootstrap

private data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    operator fun times(factor: Int) = Point(x * factor, y * factor)
}

private data class Star(val position: Point, val velocity: Point) {
    fun positionAfter(seconds: Int): Point {
        return position + velocity * seconds
    }
}

data class Result(val time: Int, val message: String)

private fun calcHeight(stars: List<Star>, time: Int): Int {
    val starPos = stars.map { it.positionAfter(time) }
    val minY = starPos.minOf { it.y }
    val maxY = starPos.maxOf { it.y }
    return maxY - minY + 1
}

private fun heightIncrease(stars: List<Star>, time: Int): Int {
    return calcHeight(stars, time + 1) - calcHeight(stars, time)
}

fun solve(lines: List<String>): Result {
    val lineRe = """position=<\s*(-?\d+),\s*(-?\d+)> velocity=<\s*(-?\d+),\s*(-?\d+)>""".toRegex()
    val stars = lines.map { line ->
        val match = lineRe.matchEntire(line)!!
        val (px, py, vx, vy) = match.groupValues.subList(1, 5).map { it.toInt() }
        Star(Point(px, py), Point(vx, vy))
    }
    var l = 0
    var r = 1000000
    while (l < r) {
        val mid = (l + r) / 2
        if (mid == l) {
            break
        }
        val midValue = heightIncrease(stars, mid)
        if (midValue >= 0) {
            r = mid
        } else {
            l = mid
        }
    }
    val resTime = l + 1
    val starPos = stars.map { it.positionAfter(resTime) }
    val minX = starPos.minOf { it.x }
    val maxX = starPos.maxOf { it.x }
    val minY = starPos.minOf { it.y }
    val maxY = starPos.maxOf { it.y }
    val w = maxX - minX + 1
    val h = maxY - minY + 1
    val chars = Array(w * h) { '.' }
    for ((x, y) in starPos) {
        chars[(y - minY) * w + (x - minX)] = '#'
    }
    val message = chars.toList().chunked(w) { it.joinToString("") }.joinToString("\n")
    return Result(resTime, message)
}

fun main(args: Array<String>) {
    val result = solve(Bootstrap(args).readAllLines())
    println(result.time)
    println(result.message)
}
