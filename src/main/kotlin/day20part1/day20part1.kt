package day20part1

import bootstrap.Bootstrap
import java.lang.Exception

private data class Point(val x: Int, val y: Int)

private class Walker(private val directions: String) {
    private val paths = mutableSetOf<Pair<Point, Point>>()
    private var dirIndex = 1

    private fun recWalk(start: Set<Point>): Set<Point> {
        var cur = start
        val result = mutableSetOf<Point>()
        while (true) {
            when (directions[dirIndex++]) {
                ')', '$' -> {
                    result.addAll(cur)
                    return result
                }

                'W' -> cur = move(cur, -1, 0)
                'E' -> cur = move(cur, 1, 0)
                'N' -> cur = move(cur, 0, -1)
                'S' -> cur = move(cur, 0, 1)
                '|' -> {
                    result.addAll(cur)
                    cur = start
                }

                '(' -> cur = recWalk(cur)
                else -> throw Exception("unexpected char")
            }
        }
    }

    private fun move(points: Set<Point>, dx: Int, dy: Int): Set<Point> {
        return points.map { point ->
            val newPoint = Point(point.x + dx, point.y + dy)
            paths.add(point to newPoint)
            paths.add(newPoint to point)
            newPoint
        }.toSet()
    }

    fun walk(): Map<Point, List<Point>> {
        recWalk(setOf(Point(0, 0)))
        return paths.groupBy({ (f, _) -> f }, { (_, t) -> t })
    }
}

private data class PointDist(val p: Point, val dist: Int)

fun solve(directions: String): Int {
    val areaMap = Walker(directions).walk()
    val q = ArrayDeque<PointDist>()
    val been = mutableSetOf<Point>()
    val start = Point(0, 0)
    been.add(start)
    q.addLast(PointDist(start, 0))
    var lastDist = 0
    while (q.isNotEmpty()) {
        val cur = q.removeFirst()
        lastDist = cur.dist
        for (dest in areaMap[cur.p]!!) {
            if (dest !in been) {
                been.add(dest)
                q.addLast(PointDist(dest, cur.dist + 1))
            }
        }
    }
    return lastDist
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllText().trimEnd()))
}
