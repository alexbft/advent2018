package day7part1

import bootstrap.Bootstrap

fun solve(lines: List<String>): String {
    val re = """Step (.) must be finished before step (.) can begin\.""".toRegex()
    val pairs = lines.map { line ->
        val match = re.matchEntire(line)!!
        match.groupValues[2].first() to match.groupValues[1].first()
    }
    val requirements = pairs.groupBy({ it.first }, { it.second })
    val allNodes = pairs.flatMap { it.toList() }.distinct().sorted()
    val result = mutableListOf<Char>()
    for (step in allNodes.indices) {
        for (c in allNodes) {
            if (c in result) {
                continue
            }
            if (c !in requirements || requirements[c]!!.all { it in result }) {
                result.add(c)
                break
            }
        }
    }
    return result.joinToString("")
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines()))
}
