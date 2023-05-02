package day1part1

import bootstrap.Bootstrap

fun main(args: Array<String>) {
    val lines = Bootstrap(args).readAllLines()
    val nums = lines.map { it.toInt() }
    println(nums.sum())
}
