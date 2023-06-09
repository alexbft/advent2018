package day23part2

import bootstrap.Bootstrap
import kotlin.math.abs

data class Point3(val x: Int, val y: Int, val z: Int) {
    val manhattanDistanceToOrigin: Int get() = abs(x) + abs(y) + abs(z)
}

private data class Bot(val position: Point3, val radius: Int)

private data class Cube(val position: Point3, val size: Int)

private data class CubeWithBots(val cube: Cube, val bots: List<Int>)

data class SolveResult(val position: Point3, val bots: List<Int>)

private fun distance(x: Int, x0: Int, x1: Int): Int {
    return if (x < x0) {
        x0 - x
    } else if (x > x1) {
        x - x1
    } else {
        0
    }
}

// Returns if any of 1x1x1 cubes in the given cube are in range of the given bot
private fun isInRange(bot: Bot, cube: Cube): Boolean {
    return distance(bot.position.x, cube.position.x, cube.position.x + cube.size - 1) +
            distance(bot.position.y, cube.position.y, cube.position.y + cube.size - 1) +
            distance(bot.position.z, cube.position.z, cube.position.z + cube.size - 1) <= bot.radius
}

fun solveFull(lines: List<String>): SolveResult {
    val lineRe = """pos=<(-?\d+),(-?\d+),(-?\d+)>, r=(\d+)""".toRegex()
    val bots = buildList<Bot> {
        for (line in lines) {
            val match = lineRe.matchEntire(line) ?: throw Exception("Unparsed $line")
            val (x, y, z) = match.groupValues.slice(1..3).map { it.toInt() }
            val r = match.groupValues[4].toInt()
            add(Bot(Point3(x, y, z), r))
        }
    }
    val initialSize = 1 shl 28
    val initialCube = Cube(Point3(-initialSize, -initialSize, -initialSize), 2 * initialSize)
    val initialBots = bots.indices.toList()
    var candidates = listOf(CubeWithBots(initialCube, initialBots))
    var iter = 0
    outer@ while (true) {
        println("Iter ${iter++} candidates: ${candidates.size} bots: ${candidates[0].bots.size}")
        val newCandidates = mutableListOf<CubeWithBots>()
        for (candidate in candidates) {
            if (candidate.cube.size == 1) {
                break@outer
            }
            val newSize = candidate.cube.size / 2
            val (x0, y0, z0) = candidate.cube.position
            for (zz in 0..1) {
                for (yy in 0..1) {
                    for (xx in 0..1) {
                        val newCubePosition = Point3(x0 + xx * newSize, y0 + yy * newSize, z0 + zz * newSize)
                        val newCube = Cube(newCubePosition, newSize)
                        val newBots = candidate.bots.filter { index -> isInRange(bots[index], newCube) }
                        newCandidates.add(CubeWithBots(newCube, newBots))
                    }
                }
            }
        }
        val maxBots = newCandidates.maxOf { candidate -> candidate.bots.size }
        candidates = newCandidates.filter { candidate -> candidate.bots.size == maxBots }
    }
    val resultCandidate = candidates.minBy { candidate -> candidate.cube.position.manhattanDistanceToOrigin }
    println(resultCandidate)
    return SolveResult(resultCandidate.cube.position, resultCandidate.bots)
}

private fun solve(lines: List<String>): Int {
    return solveFull(lines).position.manhattanDistanceToOrigin
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines()))
}
