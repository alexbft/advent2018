package day6part2

import bootstrap.Bootstrap
import kotlin.math.abs

data class Point(val x: Int, val y: Int)
private const val MAX_DIST = 10000

fun solve(points: List<Point>, maxDist: Int): Int {
    val minX = points.minOf { it.x }
    val maxX = points.maxOf { it.x }
    val minY = points.minOf { it.y }
    val maxY = points.maxOf { it.y }
    val safeMargin = maxDist / points.size
    var result = 0
    for (y in minY-safeMargin..maxY+safeMargin) {
        for (x in minX-safeMargin..maxX+safeMargin) {
            var dist = 0
            for (p in points) {
                dist += abs(x - p.x) + abs(y - p.y)
            }
            if (dist < maxDist) {
                ++result
            }
        }
    }
    return result
}

fun main(args: Array<String>) {
    val points = Bootstrap(args).readAllLines().map {
        val (x, y) = it.split(", ").map { xs -> xs.toInt() }
        Point(x, y)
    }
    println(solve(points, MAX_DIST))
}
