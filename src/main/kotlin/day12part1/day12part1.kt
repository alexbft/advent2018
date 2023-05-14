package day12part1

import bootstrap.Bootstrap

private data class State(val plus: List<Char>, val minus: List<Char>) {
    constructor(s: String) : this(s.toList(), listOf())

    fun get(pos: Int): Char {
        return if (pos >= 0) {
            if (pos < plus.size) plus[pos] else '.'
        } else {
            val mPos = (-pos) - 1
            if (mPos < minus.size) minus[mPos] else '.'
        }
    }

    fun getArea(pos: Int): String {
        return (pos - 2..pos + 2).map(this::get).joinToString("")
    }
}

fun solve(initialState: String, rulesRaw: List<String>): Int {
    val ruleRe = """([.#]{5}) => ([.#])""".toRegex()
    val rules = rulesRaw.associate { rule ->
        val match = ruleRe.matchEntire(rule)!!
        match.groupValues[1] to match.groupValues[2].first()
    }
    var state = State(initialState)
    for (gen in 1..20) {
        val newPlus = (0 until state.plus.size + 2).map { index ->
            val area = state.getArea(index)
            rules[area] ?: '.'
        }
        val newMinus = (-1 downTo -(state.minus.size + 2)).map { index ->
            val area = state.getArea(index)
            rules[area] ?: '.'
        }
        state = State(newPlus, newMinus)
    }
    return (-state.minus.size until state.plus.size).filter { state.get(it) == '#' }.sum()
}

fun main(args: Array<String>) {
    val lines = Bootstrap(args).readAllLines()
    val initialState = lines[0].substring("initial state: ".length)
    val rules = lines.slice(2 until lines.size)
    println(solve(initialState, rules))
}
