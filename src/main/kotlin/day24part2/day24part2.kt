package day24part2

import bootstrap.Bootstrap

private data class UnitGroup(
    val id: Int,
    val team: Int,
    var units: Int,
    val unitHp: Int,
    val immunities: Set<String>,
    val weaknesses: Set<String>,
    val damage: Int,
    val damageType: String,
    val initiative: Int,
) {
    val isAlive: Boolean get() = units > 0
    val effectivePower: Int get() = units * damage

    fun damageTo(other: UnitGroup): Int {
        if (damageType in other.immunities) {
            return 0
        }
        if (damageType in other.weaknesses) {
            return 2 * effectivePower
        }
        return effectivePower
    }

    fun boost(attackBonus: Int): UnitGroup {
        return UnitGroup(id, team, units, unitHp, immunities, weaknesses, damage = damage + attackBonus, damageType, initiative)
    }
}

private data class BattleResult(val winner: Int, val unitsLeft: Int)

private fun parseGroups(text: String): List<UnitGroup> {
    val textRe = """Immune System:(.+?)Infection:(.+)""".toRegex(RegexOption.DOT_MATCHES_ALL)
    val textMatch = textRe.matchEntire(text) ?: throw Exception("Format error")
    val parts = textMatch.groupValues.slice(1..2).map { it.trim() }
    val groups = mutableListOf<UnitGroup>()
    val lineRe =
        """(?<units>\d+) units each with (?<unitHp>\d+) hit points (?:\((?<resistances>.+?)\) )?with an attack that does (?<damage>\d+) (?<damageType>\w+) damage at initiative (?<initiative>\d+)""".toRegex()
    val resistRe = """weak to (?<weak>.+)|immune to (?<immune>.+)""".toRegex()
    var nextId = 1
    for ((teamId, part) in parts.withIndex()) {
        for (line in part.lines()) {
            val lineMatch = lineRe.matchEntire(line) ?: throw Exception("Unparsed line: $line")
            val units = lineMatch.groups["units"]!!.value.toInt()
            val unitHp = lineMatch.groups["unitHp"]!!.value.toInt()
            val immunities = mutableSetOf<String>()
            val weaknesses = mutableSetOf<String>()
            val resistances = lineMatch.groups["resistances"]?.value
            if (resistances != null) {
                val resistanceParts = resistances.split("; ")
                for (resistancePart in resistanceParts) {
                    val resistMatch =
                        resistRe.matchEntire(resistancePart) ?: throw Exception("Unparsed resist: $resistancePart")
                    val weakStr = resistMatch.groups["weak"]?.value
                    if (weakStr != null) {
                        weaknesses.addAll(weakStr.split(", "))
                    }
                    val immuneStr = resistMatch.groups["immune"]?.value
                    if (immuneStr != null) {
                        immunities.addAll(immuneStr.split(", "))
                    }
                }
            }
            val damage = lineMatch.groups["damage"]!!.value.toInt()
            val damageType = lineMatch.groups["damageType"]!!.value
            val initiative = lineMatch.groups["initiative"]!!.value.toInt()
            groups.add(
                UnitGroup(
                    id = nextId++,
                    team = teamId,
                    units, unitHp, immunities, weaknesses, damage, damageType, initiative
                )
            )
        }
    }
    return groups
}

private fun simBattle(groups_: List<UnitGroup>): BattleResult {
    val groups = groups_.associateBy { it.id }
    var battleOver = false
    for (round in 1..10000) {
        val aliveGroups = groups.values.filter { it.isAlive }
        // Target selection phase
        val groupIdsByTeam = aliveGroups
            .groupBy { it.team }
            .mapValues { (_, groups) -> groups.map { it.id }.toMutableSet() }
        if (groupIdsByTeam.size < 2) {
            battleOver = true
            break
        }
        val targetSelectionOrder = aliveGroups.sortedWith(
            compareByDescending<UnitGroup> { it.effectivePower }
                .thenByDescending { it.initiative })
        val targets = mutableMapOf<Int, Int>()
        for (group in targetSelectionOrder) {
            val possibleTargetIds = groupIdsByTeam[1 - group.team]!!
            val possibleTargets = possibleTargetIds.map { groups[it]!! }
            val selectedTarget = possibleTargets.maxWithOrNull(
                compareBy<UnitGroup> { group.damageTo(it) }
                    .thenBy { it.effectivePower }
                    .thenBy { it.initiative })
            if (selectedTarget != null && group.damageTo(selectedTarget) > 0) {
                targets[group.id] = selectedTarget.id
                possibleTargetIds.remove(selectedTarget.id)
            }
        }
        if (targets.isEmpty()) {
            // Draw
            return BattleResult(2, 0)
        }
        // Attack phase
        val initiativeOrder = aliveGroups.sortedByDescending { it.initiative }
        for (group in initiativeOrder) {
            if (!group.isAlive) {
                continue
            }
            val targetId = targets[group.id] ?: continue
            val target = groups[targetId]!!
            val killed = group.damageTo(target) / target.unitHp
            target.units -= killed
        }
    }
    val aliveGroups = groups.values.filter { it.isAlive }
    if (!battleOver) {
        println(aliveGroups)
        throw Exception("Battle too long")
    }
    return BattleResult(aliveGroups[0].team, aliveGroups.sumOf { it.units })
}

private fun battleWithBoost(groups: List<UnitGroup>, boost: Int): BattleResult {
    val boosted = groups.map { group -> if (group.team == 0) group.boost(boost) else group.boost(0) }
    return simBattle(boosted)
}

fun solve(text: String): Int {
    val groups = parseGroups(text)
    for (boost in 0..10000) {
        val result = battleWithBoost(groups, boost)
        if (result.winner == 0) {
            println("$boost $result")
            return result.unitsLeft
        }
    }
    throw Exception("no solution")
}

fun main(args: Array<String>) {
    println(solve(Bootstrap(args).readAllText()))
}
