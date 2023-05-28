package day18part1

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

    override fun toString(): String {
        return "AreaMap(\n${rows.joinToString("\n") { row -> "    ${row.joinToString("")}" }})"
    }
}

fun solveFull(area: AreaMap, iterations: Int): AreaMap {
    var newArea = area
    for (iter in 0 until iterations) {
        newArea = newArea.mapIndexed { p, c ->
            val adjacent = newArea.adjacent(p)
            when (c) {
                '.' -> if (adjacent.count { it == '|' } >= 3) '|' else '.'
                '|' -> if (adjacent.count { it == '#' } >= 3) '#' else '|'
                '#' -> if ('#' in adjacent && '|' in adjacent) '#' else '.'
                else -> throw Exception("Unexpected char $c")
            }
        }
    }
    return newArea
}

fun solve(lines: List<String>): Int {
    val area = AreaMap(lines.map { it.toList() })
    val newArea = solveFull(area, 10)
    val allCells = newArea.rows.flatten()
    val wood = allCells.count { it == '|' }
    val lumber = allCells.count { it == '#' }
    return wood * lumber
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines()))
}