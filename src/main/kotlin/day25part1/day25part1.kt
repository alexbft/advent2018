package day25part1

import bootstrap.Bootstrap
import kotlin.math.abs

private data class Point4(val x: List<Int>) {
    init {
        if (x.size != 4) {
            throw Exception("Invalid point")
        }
    }

    fun isAdjacentTo(other: Point4): Boolean {
        return x.zip(other.x).sumOf { (x0, x1) -> abs(x0 - x1) } <= 3
    }
}

fun solve(lines: List<String>): Int {
    val points = lines.map { line ->
        Point4(line.split(",").map { it.toInt() })
    }
    val edges = mutableListOf<Pair<Int, Int>>()
    for ((i, p0) in points.withIndex()) {
        for ((j, p1) in points.withIndex()) {
            if (i != j && p0.isAdjacentTo(p1)) {
                edges.add(i to j)
            }
        }
    }
    val graph = edges.groupBy({ it.first }, { it.second })
    val paint = mutableMapOf<Int, Int>()
    var nextPaint = 1
    for (i in points.indices) {
        if (i in paint) {
            continue
        }
        val paintId = nextPaint++
        val queue = ArrayDeque<Int>()
        queue.addLast(i)
        paint[i] = paintId
        while (queue.isNotEmpty()) {
            val cur = queue.removeFirst()
            val paths = graph[cur] ?: continue
            for (next in paths) {
                if (next !in paint) {
                    paint[next] = paintId
                    queue.addLast(next)
                }
            }
        }
    }
    return nextPaint - 1
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines()))
}
