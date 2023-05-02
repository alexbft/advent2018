package day1part2

import bootstrap.Bootstrap

fun solve(nums: List<Int>): Int {
    val partialSums = mutableSetOf<Int>()
    var sum = 0
    partialSums.add(sum)
    while (true) {
        for (num in nums) {
            sum += num
            if (partialSums.contains(sum)) {
                return sum
            }
            partialSums.add(sum)
        }
    }
}

fun main(args: Array<String>) {
    val lines = Bootstrap(args).readAllLines()
    val nums = lines.map { it.toInt() }
    println(solve(nums));
}
