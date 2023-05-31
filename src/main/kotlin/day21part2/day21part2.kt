package day21part2

import bootstrap.Bootstrap
import java.lang.Exception

private const val MAX_REGISTER = 5

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
        if (regIndex !in 0..MAX_REGISTER) {
            throw IllegalArgumentException("regIndex out of range: $regIndex")
        }
        return regs[regIndex]
    }

    fun setReg(argIndex: Int, value: Int) {
        val regIndex = args[argIndex]
        if (regIndex !in 0..MAX_REGISTER) {
            throw IllegalArgumentException("regIndex out of range: $regIndex")
        }
        resultRegs[regIndex] = value
    }

    fun getArg(argIndex: Int): Int {
        return args[argIndex]
    }
}

private typealias ExecuteFunc = ExecuteHelper.() -> Int

private enum class Command(private val executeFunc: ExecuteFunc) {
    addr({ getReg(0) + getReg(1) }),
    addi({ getReg(0) + getArg(1) }),
    mulr({ getReg(0) * getReg(1) }),
    muli({ getReg(0) * getArg(1) }),
    banr({ getReg(0) and getReg(1) }),
    bani({ getReg(0) and getArg(1) }),
    borr({ getReg(0) or getReg(1) }),
    bori({ getReg(0) or getArg(1) }),
    setr({ getReg(0) }),
    seti({ getArg(0) }),
    gtir({ if (getArg(0) > getReg(1)) 1 else 0 }),
    gtri({ if (getReg(0) > getArg(1)) 1 else 0 }),
    gtrr({ if (getReg(0) > getReg(1)) 1 else 0 }),
    eqir({ if (getArg(0) == getReg(1)) 1 else 0 }),
    eqri({ if (getReg(0) == getArg(1)) 1 else 0 }),
    eqrr({ if (getReg(0) == getReg(1)) 1 else 0 });

    fun execute(args: List<Int>, regs: List<Int>): List<Int> {
        return ExecuteHelper(args, regs, executeFunc).execute()
    }
}

private data class Instruction(val command: Command, val args: List<Int>) {
    fun execute(regs: List<Int>) = command.execute(args, regs)
}

private enum class StopReason {
    halt, maxIterationsReached, breakPoint
}

private data class RunResult(
    val iterations: Int,
    val regs: List<Int>,
    val stopReason: StopReason,
)

private class Computer(private val program: List<Instruction>, private val ipRegIndex: Int) {
    fun run(regs: List<Int>, maxIterations: Int, debugMode: Boolean, breakpoints: Set<Int>): RunResult {
        var registers = regs.toMutableList()
        var ip = registers[ipRegIndex]
        var iteration = 0
        var stopReason = StopReason.maxIterationsReached
        while (iteration++ < maxIterations) {
            if (ip !in program.indices) {
                stopReason = StopReason.halt
                break
            }
            registers[ipRegIndex] = ip
            if (debugMode) {
                println("[$iteration] Regs: $registers")
                println("Executing: $ip@ ${program[ip].command} ${program[ip].args.joinToString(" ")}")
            }
            if (iteration > 1 && ip in breakpoints) {
                println("BREAKPOINT [$iteration] Regs: $registers")
                stopReason = StopReason.breakPoint
                break
            }
            registers = program[ip].execute(registers).toMutableList()
            ip = registers[ipRegIndex]
            ip += 1
        }
        if (debugMode && stopReason != StopReason.breakPoint) {
            println("END [$iteration] Regs: $registers")
        }
        return RunResult(iteration, registers, stopReason)
    }
}

private fun parseProgram(lines: List<String>): Computer {
    val ipRe = """#ip (\d+)""".toRegex()
    val ipMatch = ipRe.matchEntire(lines[0]) ?: throw Exception("Expected #ip")
    val ipRegIndex = ipMatch.groupValues[1].toInt()
    val lineRe = """(\w+) (\d+) (\d+) (\d+)""".toRegex()
    val program = lines.slice(1 until lines.size).map { line ->
        val lineMatch = lineRe.matchEntire(line) ?: throw Exception("Unparsed instruction: $line")
        val command = Command.valueOf(lineMatch.groupValues[1])
        val args = lineMatch.groupValues.slice(2..4).map { it.toInt() }
        Instruction(command, args)
    }
    return Computer(program, ipRegIndex)
}

fun solve(lines: List<String>): Int {
    val computer = parseProgram(lines)
    var regs = mutableListOf(0, 0, 0, 0, 0, 0)
    val cycle = mutableSetOf<Int>()
    var prevValue = 0
    var loop = 0
    for (i in 0..1000000) {
        val runResult = computer.run(regs, 1000, debugMode = false, breakpoints = setOf(18, 28))
        if (runResult.stopReason != StopReason.breakPoint) {
            throw Exception("unexpected stop")
        }
        regs = runResult.regs.toMutableList()
        if (regs[4] == 18) {
            // let's speed up the division
            regs[5] = regs[2] / 256
        } else {
            // Instruction #28 is eqrr 1, 0, 5
            // If reg#0 = reg#1 then the program halts
            // Let's store the value in reg#1. When a cycle is found, then the previous value should be the answer.
            val value = regs[1]
            println("Loop ${++loop}. Value = $value")
            if (value in cycle) {
                return prevValue
            }
            cycle.add(value)
            prevValue = value
        }
    }
    throw Exception("Cycle too long")
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines()))
}
