package com.yanbin.aoc2020

fun main() {
    val (departTime, busIdPairs) = parseInput(input)

    puzzle25(busIdPairs.map { it.first }, departTime)
    puzzle26(busIdPairs)
}

private fun puzzle26(busIdPairs: List<Pair<Int, Int>>) {
    val remainderPairs =
        busIdPairs.map { (busId, minutesAfter) -> RemainderPair(busId.toLong(), (busId - minutesAfter).toLong()) }
    val remainderFormula = getRemainderFormula(remainderPairs)
    val answer = remainderFormula.baseNumber % remainderFormula.productSeq

    println("puzzle 26: $answer")
}

private fun puzzle25(busIds: List<Int>, departTime: Int) {
    val waitTimePairs = busIds.map { departTime to it }
        .map { (departTime, id) ->
            val waitTime = id - departTime % id
            waitTime to id
        }

    val targetBus = waitTimePairs.minBy { (time, _) -> time }!!
    val answer = targetBus.first * targetBus.second
    println("puzzle 25: $answer")
}


private fun parseInput(input: String): Pair<Int, List<Pair<Int, Int>>> {
    val lines = input.split("\n")
    val departTime = lines[0].toInt()
    val rawBusIds = lines[1].split(",")
    val minutesAfter = 0..rawBusIds.lastIndex
    val busIdPairs = rawBusIds.zip(minutesAfter)
        .filter { it.first.matches(Regex("\\d*")) }
        .map { it.first.toInt() to it.second }

    return departTime to busIdPairs
}

// ref: https://zh.wikipedia.org/wiki/%E4%B8%AD%E5%9B%BD%E5%89%A9%E4%BD%99%E5%AE%9A%E7%90%86
private fun getRemainderFormula(remainderPairs: List<RemainderPair>): RemainderFormula {
    val productSeq = remainderPairs.map { it.mod }
        .reduce { product, next -> product * next }

    val excludeProductSeqs = remainderPairs.map { it.mod }
        .map { productSeq / it }

    fun findInverse(product: Long, mod: Long): Long {
        // sequence from 1
        val (_, inverse) = generateSequence(1L) { it + 1 }
            .map { it * product % mod to it }
            .first { (remainder, inverse) -> remainder == 1L }

        return inverse
    }

    val inverseNumbers = excludeProductSeqs.zip(remainderPairs.map { it.mod })
        .map { (product, mod) -> findInverse(product, mod) }

    val baseNumber = excludeProductSeqs.zip(inverseNumbers).zip(remainderPairs.map { it.remainder })
        .toTriple()
        .map { (excludeProductSeq, inverseNumber, remainder) -> excludeProductSeq * inverseNumber * remainder }
        .sum()

    return RemainderFormula(baseNumber, productSeq)
}

private fun <A, B, C> List<Pair<Pair<A, B>, C>>.toTriple(): List<Triple<A, B, C>> {
    return this.map { (pair, c) -> Triple(pair.first, pair.second, c) }
}

private data class RemainderPair(val mod: Long, val remainder: Long)
private data class RemainderFormula(val baseNumber: Long, val productSeq: Long)

private val sampleInput = """
939
7,13,x,x,59,x,31,19
""".trimIndent()

private val input = """
1002461
29,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,41,x,x,x,x,x,x,x,x,x,521,x,x,x,x,x,x,x,23,x,x,x,x,13,x,x,x,17,x,x,x,x,x,x,x,x,x,x,x,x,x,601,x,x,x,x,x,37,x,x,x,x,x,x,x,x,x,x,x,x,19
""".trimIndent()