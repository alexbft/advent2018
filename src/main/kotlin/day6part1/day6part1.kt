package day6part1

import bootstrap.Bootstrap

data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
}

private data class Visited(val id: Int, val dist: Int)

private const val ID_MULTI = -1
private val dirs = listOf(Point(-1, 0), Point(1, 0), Point(0, -1), Point(0, 1))

fun solve(points: List<Point>): Int {
    val minX = points.minOf { it.x }
    val maxX = points.maxOf { it.x }
    val minY = points.minOf { it.y }
    val maxY = points.maxOf { it.y }
    val isInfinite = mutableSetOf<Int>()
    val visited = points.withIndex().associate { (index, point) -> point to Visited(index, 0) }.toMutableMap()
    val queue = ArrayDeque(points)
    while (queue.isNotEmpty()) {
        val cur = queue.removeFirst()
        val (curId, curDist) = visited[cur]!!
        for (dir in dirs) {
            val newP = cur + dir
            val (newX, newY) = newP
            if (newX < minX || newY < minY || newX > maxX || newY > maxY) {
                isInfinite.add(curId)
                continue
            }
            if (newP !in visited) {
                visited[newP] = Visited(curId, curDist + 1)
                queue.addLast(newP)
            } else {
                val prev = visited[newP]!!
                if (prev.id != curId && prev.dist == curDist + 1) {
                    visited[newP] = Visited(ID_MULTI, curDist + 1)
                }
            }
        }
    }
    val countById = visited.values.groupingBy { it.id }.eachCount()
    return countById.entries.filter { (k, _) -> k != ID_MULTI && k !in isInfinite }.maxOf { it.value }
}

fun main(args: Array<String>) {
    val points = Bootstrap(args).readAllLines().map {
        val (x, y) = it.split(", ").map { xs -> xs.toInt() }
        Point(x, y)
    }
    println(solve(points))
}
