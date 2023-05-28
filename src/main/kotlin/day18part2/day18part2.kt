package day18part2

import bootstrap.Bootstrap
import java.lang.Exception

data class Point(val x: Int, val y: Int)

data class AreaMap(val rows: List<List<Char>>) {
    private val width = rows[0].size
    private val height = rows.size

    fun get(p: Point) = if (isInside(p)) rows[p.y][p.x] else '.'

    private fun isInside(p: Point) = p.x in 0 until width && p.y in 0 until height

    fun mapIndexed(transform: (p: Point, c: Char) -> Char): AreaMap {
        val newRows = rows.mapIndexed { y, row ->
            row.mapIndexed { x, c ->
                transform(Point(x, y), c)
            }
        }
        return AreaMap(newRows)
    }

    fun adjacent(p: Point): List<Char> {
        return buildList {
            for (y in p.y - 1..p.y + 1) {
                for (x in p.x - 1..p.x + 1) {
                    val p2 = Point(x, y)
                    if (p != p2) {
                        add(get(p2))
                    }
                }
            }
        }
    }

    fun count(c: Char): Int {
        return rows.flatten().count { it == c }
    }

    override fun toString(): String {
        return "AreaMap(\n${rows.joinToString("\n") { row -> "    ${row.joinToString("")}" }})"
    }
}

private data class SolveResult(val cycleStart: Int, val cycleLength: Int, val areaCycleStart: AreaMap)

private fun next(area: AreaMap): AreaMap {
    return area.mapIndexed { p, c ->
        val adjacent = area.adjacent(p)
        when (c) {
            '.' -> if (adjacent.count { it == '|' } >= 3) '|' else '.'
            '|' -> if (adjacent.count { it == '#' } >= 3) '#' else '|'
            '#' -> if ('#' in adjacent && '|' in adjacent) '#' else '.'
            else -> throw Exception("Unexpected char $c")
        }
    }
}

private fun iterate(area: AreaMap, iterations: Int): AreaMap {
    var newArea = area
    for (i in 0 until iterations) {
        newArea = next(newArea)
    }
    return newArea
}

private fun solveFull(area: AreaMap): SolveResult {
    var newArea = area
    val log = mutableMapOf<AreaMap, Int>()
    for (iter in 0 until 10000) {
        if (newArea in log) {
            return SolveResult(log[newArea]!!, iter - log[newArea]!!, newArea)
        }
        log[newArea] = iter
        newArea = next(newArea)
    }
    println(newArea)
    throw Exception("cycle not found")
}

fun solve(lines: List<String>): Int {
    val area = AreaMap(lines.map { it.toList() })
    val cycle = solveFull(area)
    val iters = (1000000000 - cycle.cycleStart) % cycle.cycleLength
    val newArea = iterate(cycle.areaCycleStart, iters)
    val allCells = newArea.rows.flatten()
    val wood = allCells.count { it == '|' }
    val lumber = allCells.count { it == '#' }
    //println("wood = $wood lumber = $lumber")
    return wood * lumber
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines()))
}