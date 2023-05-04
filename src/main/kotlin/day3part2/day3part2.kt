package day3part2

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
    for (claim in claims) {
        if (claims.all { other -> claim === other || !claim.rect.intersects(other.rect) }) {
            return claim.id
        }
    }
    throw Exception("no solution")
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines()))
}
