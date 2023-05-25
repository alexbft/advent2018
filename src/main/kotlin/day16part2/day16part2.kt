package day16part2

import bootstrap.Bootstrap
import java.lang.Exception

private class ExecuteHelper(
    private val args: List<Int>,
    private val regs: List<Int>,
    private val executeFunc: ExecuteFunc,
) {
    private val resultRegs = regs.toMutableList()

    fun execute(): List<Int> {
        setReg(2, executeFunc())
        return resultRegs
    }

    fun getReg(argIndex: Int): Int {
        val regIndex = args[argIndex]
        if (regIndex !in 0..3) {
            throw IllegalArgumentException("regIndex out of range: $regIndex")
        }
        return regs[regIndex]
    }

    fun setReg(argIndex: Int, value: Int) {
        val regIndex = args[argIndex]
        if (regIndex !in 0..3) {
            throw IllegalArgumentException("regIndex out of range: $regIndex")
        }
        resultRegs[regIndex] = value
    }

    fun getArg(argIndex: Int): Int {
        return args[argIndex]
    }
}

private typealias ExecuteFunc = ExecuteHelper.() -> Int

enum class Command(private val executeFunc: ExecuteFunc) {
    addr({getReg(0) + getReg(1)}),
    addi({getReg(0) + getArg(1)}),
    mulr({getReg(0) * getReg(1)}),
    muli({getReg(0) * getArg(1)}),
    banr({getReg(0) and getReg(1)}),
    bani({getReg(0) and getArg(1)}),
    borr({getReg(0) or getReg(1)}),
    bori({getReg(0) or getArg(1)}),
    setr({getReg(0)}),
    seti({getArg(0)}),
    gtir({ if (getArg(0) > getReg(1)) 1 else 0}),
    gtri({ if (getReg(0) > getArg(1)) 1 else 0}),
    gtrr({ if (getReg(0) > getReg(1)) 1 else 0}),
    eqir({ if (getArg(0) == getReg(1)) 1 else 0}),
    eqri({ if (getReg(0) == getArg(1)) 1 else 0}),
    eqrr({ if (getReg(0) == getReg(1)) 1 else 0});

    fun execute(args: List<Int>, regs: List<Int>): List<Int> {
        return ExecuteHelper(args, regs, executeFunc).execute()
    }
}

fun guessCommands(commandByCode: MutableMap<Int, Set<Command>>, instruction: List<Int>, before: List<Int>, after: List<Int>) {
    val code = instruction[0]
    val args = instruction.slice(1..3)
    val allCommands = commandByCode[code] ?: Command.values().toSet()
    val possibleCommands = buildSet {
        for (cmd in allCommands) {
            try {
                val execResult = cmd.execute(args, before)
                if (execResult == after) {
                    add(cmd)
                }
            } catch (e: IllegalArgumentException) {
                // skip
            }
        }
    }
    commandByCode[code] = possibleCommands
}

fun solve(linesRaw: List<String>): Int {
    val (part1, part2) = linesRaw.joinToString("\n").split("\n\n\n\n")
    val inputs = part1.split("\n\n")
    val inputRe = """Before: \[(.+?)]\n(.+?)\nAfter:  \[(.+?)]""".toRegex()
    val commandByCode = mutableMapOf<Int, Set<Command>>()
    for (input in inputs) {
        val match = inputRe.matchEntire(input) ?: throw Exception("Invalid block: $input")
        val before = match.groupValues[1].split(", ").map { it.toInt() }
        val instruction = match.groupValues[2].split(" ").map { it.toInt() }
        val after = match.groupValues[3].split(", ").map { it.toInt() }
        guessCommands(commandByCode, instruction, before, after)
    }
    val realCommandByCode = mutableMapOf<Int, Command>()
    val unknownCommands = Command.values().toMutableSet()
    while (unknownCommands.isNotEmpty()) {
        var found = false
        for (cmd in unknownCommands.toList()) {
            for ((code, allCmd) in commandByCode) {
                if (code in realCommandByCode) {
                    continue
                }
                val filteredCmd = allCmd.filter { it in unknownCommands }
                if (filteredCmd.size == 1 && filteredCmd.first() == cmd) {
                    unknownCommands.remove(cmd)
                    realCommandByCode[code] = cmd
                    found = true
                }
            }
        }
        if (!found) {
            throw Exception("Can't find exact matches")
        }
    }
    val instructions = part2.split("\n").map { cmd -> cmd.split(" ").map { it.toInt() }}
    var regs = listOf(0, 0, 0, 0)
    for (instruction in instructions) {
        val code = instruction[0]
        val args = instruction.slice(1..3)
        val command = realCommandByCode[code]!!
        regs = command.execute(args, regs)
    }
    return regs[0]
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines()))
}