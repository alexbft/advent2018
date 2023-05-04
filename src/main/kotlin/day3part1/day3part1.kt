package day3part1

import bootstrap.Bootstrap
import java.awt.Dimension
import java.awt.Point
import java.awt.Rectangle

private data class Claim(
    val id: Int,
    val rect: Rectangle,
)

private val lineRe = """#(\d+) @ (\d+),(\d+): (\d+)x(\d+)""".toRegex()
private fun parse(line: String): Claim {
    val match = lineRe.matchEntire(line) ?: throw Exception("Invalid format")
    val id = match.groupValues[1].toInt()
    val start = Point(match.groupValues[2].toInt(), match.groupValues[3].toInt())
    val size = Dimension(match.groupValues[4].toInt(), match.groupValues[5].toInt())
    return Claim(id, Rectangle(start, size))
}

fun solve(claimsRaw: List<String>): Int {
    val claims = claimsRaw.map(::parse)
    val maxX = claims.maxOf { it.rect.maxX.toInt() } + 1
    val maxY = claims.maxOf { it.rect.maxY.toInt() } + 1
    val grid = IntArray(maxX * maxY) { 0 }
    for (claim in claims) {
        val rect = claim.rect
        for (y in rect.y until rect.y + rect.height) {
            for (x in rect.x until rect.x + rect.width) {
                grid[y * maxX + x] += 1
            }
        }
    }
    return grid.count { it >= 2 }
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines()))
}
