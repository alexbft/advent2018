package day22part2

import java.lang.Exception
import java.util.PriorityQueue

data class Point(val x: Int, val y: Int)

private data class Point3(val x: Int, val y: Int, val tool: Int)

private data class PointDist(val point: Point3, val dist: Int): Comparable<PointDist> {
    override fun compareTo(other: PointDist): Int {
        return dist.compareTo(other.dist)
    }
}

private const val TOOL_TORCH = 0
private const val TOOL_CLIMBING = 1
private const val TOOL_NEITHER = 2

private val dirs = listOf(Point(1, 0), Point(0, 1), Point(-1, 0), Point(0, -1))

private class Cave(private val depth: Int, private val target: Point) {
    private val erosionCache = mutableMapOf<Point, Int>()

    private fun calcErosion(x: Int, y: Int): Int {
        return if (x == 0 && y == 0 || x == target.x && y == target.y) {
            getErosionByGeoIndex(0)
        } else if (x == 0) {
            getErosionByGeoIndex(y * 48271)
        } else if (y == 0) {
            getErosionByGeoIndex(x * 16807)
        } else {
            getErosionByGeoIndex(getErosion(x - 1, y) * getErosion(x, y - 1))
        }
    }

    private fun getErosion(x: Int, y: Int): Int {
        val p = Point(x, y)
        val maybeResult = erosionCache[p]
        if (maybeResult != null) {
            return maybeResult
        }
        val result = calcErosion(x, y)
        erosionCache[p] = result
        return result
    }

    private fun getErosionByGeoIndex(geoIndex: Int): Int {
        return (geoIndex + depth) % 20183
    }

    fun getRegionType(x: Int, y: Int): Int {
        return getErosion(x, y) % 3
    }
}

private fun allowedTools(regionType: Int): List<Int> {
    return when (regionType) {
        0 -> listOf(TOOL_TORCH, TOOL_CLIMBING)
        1 -> listOf(TOOL_CLIMBING, TOOL_NEITHER)
        2 -> listOf(TOOL_TORCH, TOOL_NEITHER)
        else -> throw Exception("Invalid regionType: $regionType")
    }
}

fun solve(depth: Int, target: Point): Int {
    val cave = Cave(depth, target)
    val been = mutableSetOf<Point3>()
    val target3 = Point3(target.x, target.y, TOOL_TORCH)
    val start = Point3(0, 0, TOOL_TORCH)
    val queue = PriorityQueue<PointDist>()
    queue.add(PointDist(start, 0))
    while (queue.isNotEmpty()) {
        val cur: PointDist = queue.poll()
        //println("[debug] $cur")
        val (point, dist) = cur
        if (point == target3) {
            return dist
        }
        if (point in been) {
            continue
        }
        val regionType = cave.getRegionType(point.x, point.y)
        for (tool in allowedTools(regionType)) {
            if (tool != point.tool) {
                val newP = Point3(point.x, point.y, tool)
                if (newP !in been) {
                    queue.add(PointDist(newP, dist + 7))
                }
            }
        }
        for ((dx, dy) in dirs) {
            val newP = Point3(point.x + dx, point.y + dy, point.tool)
            if (newP.x < 0 || newP.y < 0) {
                continue
            }
            if (newP in been) {
                continue
            }
            val newRegionType = cave.getRegionType(newP.x, newP.y)
            if (newP.tool !in allowedTools(newRegionType)) {
                continue
            }
            queue.add(PointDist(newP, dist + 1))
        }
        been.add(point)
    }
    throw Exception("path not found")
}

fun main() {
    println(solve(7305, Point(13, 734)))
}
