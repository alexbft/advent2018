package day4part2

import bootstrap.Bootstrap
import kotlinx.datetime.LocalDateTime

private enum class EventType {
    BEGIN_SHIFT, SLEEP, WAKE
}

private data class EventRecord(
    val date: LocalDateTime,
    val eventType: EventType,
    val guardId: Int?,
)

private data class SleepRecord(
    val guardId: Int,
    val sleepMinute: Int,
    val wakeMinute: Int,
)

fun solve(lines: List<String>): Int {
    val lineRe = """\[(.+?)] (.+)""".toRegex()
    val guardRe = """Guard #(\d+) begins shift""".toRegex()
    val records = mutableListOf<EventRecord>()
    for (line in lines) {
        val match = lineRe.matchEntire(line) ?: throw Exception("match")
        val date = LocalDateTime.parse(match.groupValues[1].replace(" ", "T"))
        val rest = match.groupValues[2]
        var guardId: Int? = null
        val eventType = when (rest) {
            "falls asleep" -> EventType.SLEEP
            "wakes up" -> EventType.WAKE
            else -> {
                val guardMatch = guardRe.matchEntire(rest) ?: throw Exception("unknown event")
                guardId = guardMatch.groupValues[1].toInt()
                EventType.BEGIN_SHIFT
            }
        }
        records.add(EventRecord(date, eventType, guardId))
    }
    records.sortBy { it.date }
    val sleepRecords = mutableListOf<SleepRecord>()
    var guardId = -1
    var startMinute = -1
    var isSleeping = false
    for (event in records) {
        when (event.eventType) {
            EventType.BEGIN_SHIFT -> {
                if (isSleeping) {
                    throw Exception("last guard asleep")
                }
                guardId = event.guardId!!
            }
            EventType.SLEEP -> {
                startMinute = event.date.minute
                isSleeping = true
            }
            EventType.WAKE -> {
                if (!isSleeping || guardId == -1) {
                    throw Exception("invalid event log")
                }
                val endMinute = event.date.minute
                sleepRecords.add(SleepRecord(guardId, startMinute, endMinute))
                isSleeping = false
            }
        }
    }
    var topGuardId = -1
    var topMinute = -1
    var topFreq = -1
    val allGuards = sleepRecords.map { it.guardId }.toSet()
    for (localGuardId in allGuards) {
        val minutesFreq = mutableMapOf<Int, Int>()
        for (rec in sleepRecords) {
            if (rec.guardId == localGuardId) {
                for (minute in rec.sleepMinute until rec.wakeMinute) {
                    minutesFreq[minute] = (minutesFreq[minute] ?: 0) + 1
                }
            }
        }
        val (localTopMinute, localTopFreq) = minutesFreq.entries.maxBy { (_, freq) -> freq }
        if (localTopFreq > topFreq) {
            topFreq = localTopFreq
            topMinute = localTopMinute
            topGuardId = localGuardId
        }
    }
    return topGuardId * topMinute
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllLines()))
}
