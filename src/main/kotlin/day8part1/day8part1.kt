package day8part1

import bootstrap.Bootstrap

private data class Node(
    val children: List<Node>,
    val metadata: List<Int>,
)

private data class ParseResult(
    val nextPos: Int,
    val parsedNode: Node,
)

private fun parseNode(source: List<Int>, startPos: Int): ParseResult {
    val childCount = source[startPos]
    val metadataCount = source[startPos + 1]
    val children = mutableListOf<Node>()
    var pos = startPos + 2
    for (i in 0 until childCount) {
        val parseResult = parseNode(source, pos)
        children.add(parseResult.parsedNode)
        pos = parseResult.nextPos
    }
    val metadata = source.slice(pos until pos + metadataCount)
    return ParseResult(pos + metadataCount, Node(children, metadata))
}

private fun recSum(node: Node): Int {
    return node.metadata.sum() + node.children.sumOf(::recSum)
}

fun solve(rawInput: String): Int {
    val nums = rawInput.trim().split(" ").map { it.toInt() }
    val parseRoot = parseNode(nums, 0)
    if (parseRoot.nextPos != nums.size) {
        throw Exception("invalid size")
    }
    return recSum(parseRoot.parsedNode)
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllText()))
}
