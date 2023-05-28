package day19part2

import bootstrap.Bootstrap
import java.lang.Exception
import kotlin.math.sqrt

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

fun solve(lines: List<String>): Int {
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
    registers[0] = 1
    var ip = 0
    // The program seems to calculate the sum of all divisors of reg[5] in a very inefficient way
    // Do some iterations so reg[5] is initialized and calculate the sum directly
    for (iter in 0..1000) {
        if (ip !in program.indices) {
            break
        }
        registers[ipRegIndex] = ip
        registers = program[ip].execute(registers).toMutableList()
        ip = registers[ipRegIndex]
        ip += 1
    }
    val n = registers[5]
    var result = 0
    for (i in 1..sqrt(n.toDouble()).toInt()) {
        if (n % i == 0) {
            val b = n / i
            result += i
            if (b != i) result += b
        }
    }
    return result
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines()))
}
