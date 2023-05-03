package day2part1

import bootstrap.Bootstrap

fun solve(items: List<String>): Int {
    var any2 = 0
    var any3 = 0
    for (item in items) {
        val counter = item.groupingBy { it }.eachCount()
        if (counter.containsValue(2)) {
            any2++
        }
        if (counter.containsValue(3)) {
            any3++
        }
    }
    return any2 * any3
}

fun main(args: Array<String>) {
    val lines = Bootstrap(args).readAllLines()
    println(solve(lines))
}
