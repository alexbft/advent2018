package day15part1

import bootstrap.Bootstrap
import java.lang.Exception
import kotlin.math.abs

private const val TEAM_ELF = 0
private const val TEAM_GOBLIN = 1

private const val HP_MAX = 200
private const val ATTACK = 3

private data class Point(val x: Int, val y: Int) : Comparable<Point> {
    operator fun plus(other: Point) = Point(x + other.x, y + other.y)
    override operator fun compareTo(other: Point) = compareValuesBy(this, other, { it.y }, { it.x })

    fun isNear(other: Point): Boolean {
        return abs(x - other.x) + abs(y - other.y) == 1
    }
}

private val dirs = listOf(Point(0, -1), Point(-1, 0), Point(1, 0), Point(0, 1))

private data class Mob(
    val team: Int,
    var hp: Int,
    var position: Point,
) {
    val isAlive: Boolean
        get() = hp > 0

    fun attack(area: List<List<Cell>>, target: Mob) {
//        println("ATTACK $position ${target.position}")
        target.hp -= ATTACK
        if (!target.isAlive) {
            getCell(area, target.position).mob = null
        }
    }

    fun move(area: List<List<Cell>>, to: Point) {
//        println("MOVE $position $to")
        val destCell = getCell(area, to)
        assert(destCell.isPassable)
        destCell.mob = this
        getCell(area, position).mob = null
        position = to
    }
}

private data class Cell(
    val hasWall: Boolean,
    var mob: Mob?,
) {
    val isPassable: Boolean
        get() = !hasWall && mob == null

    fun toChar(): String {
        return if (mob != null) {
            if (mob!!.team == TEAM_ELF) "E" else "G"
        } else {
            if (hasWall) "#" else "."
        }
    }
}

data class SolveResult(val turns: Int, val sumHp: Int)

private fun getCell(area: List<List<Cell>>, pos: Point) = area[pos.y][pos.x]

private fun getMobs(area: List<List<Cell>>): List<Mob> {
    return buildList {
        area.flatten().forEach {
            if (it.mob != null) add(it.mob!!)
        }
    }
}

private fun getNearTarget(area: List<List<Cell>>, targets: List<Mob>, center: Point): Mob? {
    val nearTargets = targets.filter { it.position.isNear(center) }
    if (nearTargets.isNotEmpty()) {
        return nearTargets.minWith(compareBy<Mob> {it.hp}.thenBy { it.position })
    }
    return null
}

private fun getFirstMoveTo(area: List<List<Cell>>, from: Point, toList: List<Point>): Point? {
    if (toList.isEmpty()) {
        return null
    }
    val prev = mutableMapOf<Point, Point>()
    prev[from] = from
    val destSet = toList.toSet()
    var curList = listOf(from)
    var nearestDest: Point? = null
    while (curList.isNotEmpty()) {
        val nextList = mutableListOf<Point>()
        val nearestCandidates = mutableListOf<Point>()
        for (cur in curList) {
            if (cur in destSet) {
                nearestCandidates.add(cur)
                continue
            }
            for (dir in dirs) {
                val newP = cur + dir
                if (newP !in prev && getCell(area, newP).isPassable) {
                    nextList.add(newP)
                    prev[newP] = cur
                }
            }
        }
        if (nearestCandidates.isNotEmpty()) {
            nearestDest = nearestCandidates.min()
            break
        }
        curList = nextList
    }
    if (nearestDest == null) {
        return null
    }
    var pathNode: Point = nearestDest
    while (true) {
        val prevNode = prev[pathNode]!!
        if (prevNode == from) {
            return pathNode
        }
        pathNode = prevNode
    }
}

fun solveFull(areaMapRaw: List<String>): SolveResult {
    val areaMap = areaMapRaw.withIndex().map { (y, row) ->
        row.trimEnd().withIndex().map { (x, c) ->
            when (c) {
                '.' -> Cell(false, null)
                '#' -> Cell(true, null)
                'E', 'G' -> Cell(
                    false,
                    Mob(if (c == 'E') TEAM_ELF else TEAM_GOBLIN, HP_MAX, Point(x, y))
                )

                else -> throw Exception("unknown cell $c")
            }
        }
    }
    var turn = 0
    outer@ while (turn++ < 10000) {
//        println("Round $turn")
//        println(areaMap.joinToString("\n") { row -> row.joinToString("") { it.toChar() } })
        val mobs = getMobs(areaMap)
        for (mob in mobs) {
            if (!mob.isAlive) {
                continue
            }
            val targets = mobs.filter { it.isAlive && it.team != mob.team }
            if (targets.isEmpty()) {
                break@outer
            }
            var nearTarget = getNearTarget(areaMap, targets, mob.position)
            if (nearTarget == null) {
                val posInRange = targets.flatMap { target -> dirs.map { target.position + it } }
                    .filter { getCell(areaMap, it).isPassable }
                val firstMove = getFirstMoveTo(areaMap, mob.position, posInRange)
                if (firstMove != null) {
                    mob.move(areaMap, firstMove)
                    nearTarget = getNearTarget(areaMap, targets, mob.position)
                }
            }
            if (nearTarget != null) {
                mob.attack(areaMap, nearTarget)
            }
        }
//        println("Round $turn")
//        println(areaMap.joinToString("\n") { row -> row.joinToString("") { it.toChar() } })
//        println(getMobs(areaMap).filter { it.isAlive }.map { it.hp }.joinToString())
    }
//    println("Round $turn")
//    println(areaMap.joinToString("\n") { row -> row.joinToString("") { it.toChar() } })
    val sumHp = getMobs(areaMap).filter { it.isAlive }.sumOf { it.hp }
    // println(sumHp)
    return SolveResult(turn - 1, sumHp)
}

fun solve(areaMapRaw: List<String>): Int {
    val result = solveFull(areaMapRaw)
    return result.turns * result.sumHp
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines()))
}