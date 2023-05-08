package day5part1

import bootstrap.Bootstrap

private fun match(c1: Char, c2: Char): Boolean {
    return if (c1.isUpperCase()) {
        c1.lowercaseChar() == c2
    } else {
        c1.uppercaseChar() == c2
    }
}

fun solve(s: String): Int {
    val stack = ArrayDeque<Char>()
    for (c in s) {
        if (stack.isEmpty() || !match(stack.last(), c)) {
            stack.addLast(c)
        } else {
            stack.removeLast()
        }
    }
    return stack.size
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllText()))
}
