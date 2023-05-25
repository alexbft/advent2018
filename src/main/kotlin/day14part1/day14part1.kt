package day14part1

fun solve(pos: Int): List<Int> {
    var pos1 = 0
    var pos2 = 1
    val list = mutableListOf(3, 7)
    while (list.size < pos + 10) {
        val cur1 = list[pos1]
        val cur2 = list[pos2]
        list.addAll((cur1 + cur2).toString().map { it.digitToInt() })
        pos1 = (pos1 + cur1 + 1) % list.size
        pos2 = (pos2 + cur2 + 1) % list.size
    }
    return list.slice(pos until pos + 10)
}

fun main() {
    println(solve(652601).joinToString(""))
}