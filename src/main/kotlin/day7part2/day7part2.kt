package day7part2

import bootstrap.Bootstrap
import java.util.PriorityQueue

private data class DoneEvent(
    val time: Int,
    val node: Char,
)

fun solve(lines: List<String>, maxWorkers: Int, stepTime: Int): Int {
    val re = """Step (.) must be finished before step (.) can begin\.""".toRegex()
    val pairs = lines.map { line ->
        val match = re.matchEntire(line)!!
        match.groupValues[2].first() to match.groupValues[1].first()
    }
    val requirements = pairs.groupBy({ it.first }, { it.second })
    val allNodes = pairs.flatMap { it.toList() }.distinct().sorted()
    val done = mutableSetOf<Char>()
    val working = mutableSetOf<Char>()
    val events = PriorityQueue<DoneEvent>(compareBy { it.time })
    var time = 0
    var workers = maxWorkers
    while (true) {
        for (c in allNodes) {
            if (workers <= 0) {
                break
            }
            if (c in done || c in working) {
                continue
            }
            if (c !in requirements || requirements[c]!!.all { it in done }) {
                val workTime = stepTime + (c - 'A') + 1
                events.add(DoneEvent(time + workTime, c))
                working.add(c)
                --workers
            }
        }
        if (events.isEmpty()) {
            break
        }
        val nextEvent = events.poll()!!
        time = nextEvent.time
        working.remove(nextEvent.node)
        done.add(nextEvent.node)
        ++workers
    }

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
    return time
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines(), 5, 60))
}
