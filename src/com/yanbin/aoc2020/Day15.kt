package com.yanbin.aoc2020

fun main() {
    val numbers = parseNumbers(input)

    puzzle29(numbers)
    puzzle30(numbers)
}

private fun puzzle29(numbers: List<Int>) {
    val answer = playMemoryGame(numbers, 2020)

    println("puzzle 29: $answer")
}

private fun puzzle30(numbers: List<Int>) {
    val answer = playMemoryGame(numbers, 30000000)

    println("puzzle 30: $answer")
}

private fun playMemoryGame(numbers: List<Int>, rounds: Int): Int {
    // read starting numbers
    val roundOfNextTurn = numbers.size + 1
    val memory = numbers.mapIndexed { index, number ->
        number to index + 1
    }.toMap().toMutableMap()

    val remainRounds = roundOfNextTurn until rounds

    // Can not use immutable data structure, takes long time to finish
    return remainRounds.fold(0) { lastRoundNumber: Int, round: Int ->
        val lastPosOfRound = memory[lastRoundNumber] ?: -1

        val newNumber = if (lastPosOfRound == -1) {
            0
        } else {
            round - lastPosOfRound
        }

        memory[lastRoundNumber] = round
        newNumber
    }
}

fun parseNumbers(input: String): List<Int> {
    return input.split(",")
            .map { it.toInt() }
}

private val sampleInput = """
0,3,6
""".trimIndent()

private val input = """
0,3,1,6,7,5
""".trimIndent()