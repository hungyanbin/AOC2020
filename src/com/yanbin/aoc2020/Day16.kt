package com.yanbin.aoc2020

fun main() {
    val (rules, myTicket, nearbyTickets) = parseInput(sampleInput)

    // find invalid tickets
}

private fun parseInput(sampleInput: String): Triple<List<Rule>, Ticket, List<Ticket>> {
    val indexOfMyTicket = sampleInput.indexOf("your ticket:")
    val indexOfNearByTickets = sampleInput.indexOf("nearby tickets:")

    val rawRules = sampleInput.subSequence(0, indexOfMyTicket - 1).trim()
    val rawMyTicket = sampleInput.subSequence(indexOfMyTicket + 12, indexOfNearByTickets - 1).trim()
    val rawNearByTickets = sampleInput.subSequence(indexOfNearByTickets + 15, sampleInput.length).trim()

    // parse rules
    val parseRule = { rawRule: String ->
        val splitedRule = rawRule.split(":")
        val key = splitedRule[0]
        val ranges = FieldRange.create(splitedRule[1])
        Rule(key, ranges)
    }
    val rules = rawRules.split("\n")
            .map { parseRule(it) }

    // parse tickets
    val parseTicket = { rawTicket: String -> Ticket(rawTicket.split(",").map { it.toInt() }) }
    val myTicket = parseTicket(rawMyTicket.toString())
    val nearByTickets = rawNearByTickets.split("\n")
            .map { parseTicket(it) }

    return Triple(rules, myTicket, nearByTickets)
}

private data class Rule(val key: String, val range: FieldRange)

private data class FieldRange(private val ranges: List<IntRange>) {

    fun isInRange(number: Int): Boolean {
        return ranges.any { range -> range.contains(number) }
    }

    companion object {
        fun create(rawInput: String): FieldRange {
            val trimmedRanges = rawInput.split("or")
                    .map(String::trim)
                    .filter { it != "or" }

            val ranges = trimmedRanges.map {
                val numbers = it.split("-")
                        .map(String::toInt)
                numbers[0]..numbers[1]
            }

            return FieldRange(ranges)
        }
    }
}

private data class Ticket(val fields: List<Int>)

private val sampleInput = """
class: 1-3 or 5-7
row: 6-11 or 33-44
seat: 13-40 or 45-50

your ticket:
7,1,14

nearby tickets:
7,3,47
40,4,50
55,2,20
38,6,12
""".trimIndent()