package day5part2

import bootstrap.Bootstrap

private fun match(c1: Char, c2: Char): Boolean {
    return if (c1.isUpperCase()) {
        c1.lowercaseChar() == c2
    } else {
        c1.uppercaseChar() == c2
    }
}

fun solve(s: String): Int {
    return ('a'..'z').minOf { ignoreCharLower ->
        val ignoreStackUpper = ignoreCharLower.uppercaseChar()
        val stack = ArrayDeque<Char>()
        for (c in s) {
            if (c == ignoreCharLower || c == ignoreStackUpper) {
                continue
            }
            if (stack.isEmpty() || !match(stack.last(), c)) {
                stack.addLast(c)
            } else {
                stack.removeLast()
            }
        }
        stack.size
    }
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllText()))
}
