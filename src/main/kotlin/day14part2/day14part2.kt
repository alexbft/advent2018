package day14part2

fun solve(targetDigits: String): Int {
    var pos1 = 0
    var pos2 = 1
    val list = mutableListOf(3, 7)
    val targetList = targetDigits.map { it.digitToInt() }
    while (true) {
        val cur1 = list[pos1]
        val cur2 = list[pos2]
        val toAdd = (cur1 + cur2).toString().map { it.digitToInt() }
        for (num in toAdd) {
            list.add(num)
            if (list.size >= targetList.size && list.slice(list.size - targetList.size until list.size) == targetList) {
                return list.size - targetList.size
            }
        }
        pos1 = (pos1 + cur1 + 1) % list.size
        pos2 = (pos2 + cur2 + 1) % list.size
    }
}

fun main() {
    println(solve("652601"))
}