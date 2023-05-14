package day12part2

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

const val SIMULATE_GENS = 1000
fun solve(initialState: String, rulesRaw: List<String>): Long {
    val ruleRe = """([.#]{5}) => ([.#])""".toRegex()
    val rules = rulesRaw.associate { rule ->
        val match = ruleRe.matchEntire(rule)!!
        match.groupValues[1] to match.groupValues[2].first()
    }
    var state = State(initialState)
    var prevState = state
    for (gen in 1..SIMULATE_GENS) {
        val newPlus = (0 until state.plus.size + 2).map { index ->
            val area = state.getArea(index)
            rules[area] ?: '.'
        }
        val newMinus = (-1 downTo -(state.minus.size + 2)).map { index ->
            val area = state.getArea(index)
            rules[area] ?: '.'
        }
        var plusSize = newPlus.size
        while (plusSize > 0 && newPlus[plusSize - 1] == '.') --plusSize
        var minusSize = newMinus.size
        while (minusSize > 0 && newMinus[minusSize - 1] == '.') --minusSize
        prevState = state
        state = State(newPlus.slice(0 until plusSize), newMinus.slice(0 until minusSize))
        //println(state.plus.joinToString(""))
    }
    // After some steps, plants tend to move right or left by a fixed number of positions. Let's check
    val prevPlantsCount = prevState.plus.count { it == '#' } + prevState.minus.count { it == '#' }
    val curPlantsCount = state.plus.count { it == '#' } + state.minus.count { it == '#' }
    if (prevPlantsCount != curPlantsCount) {
        throw Exception("hypothesis disproved")
    }
    val shift = if (state.plus.size > prevState.plus.size) {
        state.plus.size - prevState.plus.size
    } else if (state.minus.size > prevState.minus.size) {
        -(state.minus.size - prevState.minus.size)
    } else {
        0
    }
    println("Shift = $shift")
    val bigShift = shift.toLong() * (50_000_000_000L - SIMULATE_GENS)
    return (-state.minus.size until state.plus.size).filter { state.get(it) == '#' }.sumOf { it + bigShift }
}

fun main(args: Array<String>) {
    val lines = Bootstrap(args).readAllLines()
    val initialState = lines[0].substring("initial state: ".length)
    val rules = lines.slice(2 until lines.size)
    println(solve(initialState, rules))
}
