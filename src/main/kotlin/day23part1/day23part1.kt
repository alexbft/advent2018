package day23part1

import bootstrap.Bootstrap
import kotlin.math.abs

private data class Point3(val x: Int, val y: Int, val z: Int) {
    fun manhattanDistance(other: Point3): Int {
        return abs(x - other.x) + abs(y - other.y) + abs(z - other.z)
    }
}
private data class Bot(val position: Point3, val radius: Int)

fun solve(lines: List<String>): Int {
    val lineRe = """pos=<(-?\d+),(-?\d+),(-?\d+)>, r=(\d+)""".toRegex()
    val bots = buildList<Bot> {
        for (line in lines) {
            val match = lineRe.matchEntire(line) ?: throw Exception("Unparsed $line")
            val (x, y, z) = match.groupValues.slice(1..3).map { it.toInt() }
            val r = match.groupValues[4].toInt()
            add(Bot(Point3(x, y, z), r))
        }
    }
    val bigBot = bots.maxBy { it.radius }
    return bots.count { bot -> bot.position.manhattanDistance(bigBot.position) <= bigBot.radius }
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines()))
}
