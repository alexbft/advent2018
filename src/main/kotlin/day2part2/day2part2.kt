package day2part2

import bootstrap.Bootstrap

fun solve(items: List<String>): String {
    for (item1 in items) {
        for (item2 in items) {
            val zipped = item1.zip(item2)
            val diffCount = zipped.count { (x, y) -> x != y }
            if (diffCount == 1) {
                return zipped.filter { (x, y) -> x == y }.map { (x, _) -> x }.joinToString("")
            }
        }
    }
    throw Exception("no answer")
}

fun main(args: Array<String>) {
    val lines = Bootstrap(args).readAllLines()
    println(solve(lines))
}
