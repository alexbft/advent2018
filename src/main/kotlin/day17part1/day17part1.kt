package day17part1

import bootstrap.Bootstrap
import java.lang.Exception

private data class Point(val x: Int, val y: Int) {
    fun down(): Point = Point(x, y + 1)
    fun left(): Point = Point(x - 1, y)
    fun right(): Point = Point(x + 1, y)
}

private const val WALL = 1
private const val RUNNING_WATER = 2
private const val STILL_WATER = 3

private class WaterCounter(private val map: MutableMap<Point, Int>) {
    var water = 0
    val minY = map.keys.minOf { it.y }
    val maxY = map.keys.maxOf { it.y }
    init {
        if (minY <= 0) {
            throw Exception("crap")
        }
    }

    fun rec(p: Point, prev: Point): Boolean {
        if (p.y > maxY) {
            return false
        }
        when (map[p]) {
            WALL -> return true
            STILL_WATER -> return true
            RUNNING_WATER -> return false
        }
        map[p] = RUNNING_WATER
        if (p.y >= minY) {
            ++water
        }
        if (!rec(p.down(), p)) {
            return false
        }
        val mustPropagateStillWater = prev.down() == p
        val isBoundedLeft = p.left() == prev || rec(p.left(), p)
        val isBoundedRight = p.right() == prev || rec(p.right(), p)
        if (mustPropagateStillWater && isBoundedLeft && isBoundedRight) {
            map[p] = STILL_WATER
            var cur = p.left()
            while (map[cur] == RUNNING_WATER) {
                map[cur] = STILL_WATER
                cur = cur.left()
            }
            cur = p.right()
            while (map[cur] == RUNNING_WATER) {
                map[cur] = STILL_WATER
                cur = cur.right()
            }
        }
        return isBoundedLeft && isBoundedRight
    }
}

fun solve(lines: List<String>): Int {
    val lineRe = """(.)=(\d+), .=(\d+)\.\.(\d+)""".toRegex()
    val map = mutableMapOf<Point, Int>()
    for (line in lines) {
        val match = lineRe.matchEntire(line) ?: throw Exception("Unparsed $line")
        val isHorizontal = match.groupValues[1] == "y"
        val a = match.groupValues[2].toInt()
        val b0 = match.groupValues[3].toInt()
        val b1 = match.groupValues[4].toInt()
        if (isHorizontal) {
            for (x in b0..b1) {
                map[Point(x, a)] = WALL
            }
        } else {
            for (y in b0..b1) {
                map[Point(a, y)] = WALL
            }
        }
    }
    val counter = WaterCounter(map).apply { rec(Point(500, 0), Point(500, -1)) }
    return counter.water
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines()))
}