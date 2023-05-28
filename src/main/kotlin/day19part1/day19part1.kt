package day19part1

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

data class SolveResult(val regs: List<Int>)

fun solveFull(lines: List<String>): SolveResult {
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
    var registers = Array(6) { 0 }.toMutableList()
    var ip = 0
    while (true) {
        if (ip !in program.indices) {
            break
        }
        registers[ipRegIndex] = ip
        registers = program[ip].execute(registers).toMutableList()
        ip = registers[ipRegIndex]
        ip += 1
    }
    return SolveResult(registers)
}

private fun solve(lines: List<String>): Int {
    return solveFull(lines).regs[0]
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines()))
}