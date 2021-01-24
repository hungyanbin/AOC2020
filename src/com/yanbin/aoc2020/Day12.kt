package com.yanbin.aoc2020

import java.lang.IllegalArgumentException
import kotlin.math.abs

fun main() {
    val instructions = parseInput(input)

    puzzle23(instructions)
    puzzle24(instructions)
}

private fun puzzle24(instructions: List<Pair<NavInstruction, Int>>) {
    val initWayPoint = WayPoint(10, 1)
    val initFerry = Ferry2(Position(0, 0), initWayPoint)
    val ferry = instructions.fold(initFerry) { (position, wayPoint), (navInstruction, number) ->
        when (navInstruction) {
            NavInstruction.North -> {
                val newWayPoint = WayPoint(wayPoint.x, wayPoint.y + number)
                Ferry2(position, newWayPoint)
            }
            NavInstruction.East -> {
                val newWayPoint = WayPoint(wayPoint.x + number, wayPoint.y)
                Ferry2(position, newWayPoint)
            }
            NavInstruction.South -> {
                val newWayPoint = WayPoint(wayPoint.x, wayPoint.y - number)
                Ferry2(position, newWayPoint)
            }
            NavInstruction.West -> {
                val newWayPoint = WayPoint(wayPoint.x - number, wayPoint.y)
                Ferry2(position, newWayPoint)
            }
            NavInstruction.Left -> {
                val newWayPoint = turnLeft(wayPoint, number)
                Ferry2(position, newWayPoint)
            }
            NavInstruction.Right -> {
                val newWayPoint = turnRight(wayPoint, number)
                Ferry2(position, newWayPoint)
            }
            NavInstruction.Forward -> {
                val newPosition = getNextPosition(position, wayPoint, number)
                Ferry2(newPosition, wayPoint)
            }
        }
    }

    val distance = manhattanDistance(ferry.position.x, ferry.position.y)
    println("puzzle24: $distance")
}

private fun turnLeft(wayPoint: WayPoint, degree: Int): WayPoint {
    return when((degree / 90) %4) {
        0 -> wayPoint
        1 -> WayPoint(-wayPoint.y, wayPoint.x)
        2 -> WayPoint(-wayPoint.x, -wayPoint.y)
        3 -> WayPoint(wayPoint.y, -wayPoint.x)
        else -> throw IllegalStateException("Illegal number for $degree")
    }
}

private fun turnRight(wayPoint: WayPoint, degree: Int): WayPoint {
    return when((degree / 90) %4) {
        0 -> wayPoint
        1 -> WayPoint(wayPoint.y, -wayPoint.x)
        2 -> WayPoint(-wayPoint.x, -wayPoint.y)
        3 -> WayPoint(-wayPoint.y, wayPoint.x)
        else -> throw IllegalStateException("Illegal number for $degree")
    }
}

private fun puzzle23(instructions: List<Pair<NavInstruction, Int>>) {
    val initPosition = Ferry(Position(0, 0), CardinalDirection.E)
    val ferry = instructions.fold(initPosition) { ferry, (navInstruction, number) ->
        when (navInstruction) {
            NavInstruction.Forward -> {
                val nextPosition = getNextPosition(ferry.position, ferry.head, number)
                Ferry(nextPosition, ferry.head)
            }
            NavInstruction.North -> {
                val nextPosition = getNextPosition(ferry.position, CardinalDirection.N, number)
                Ferry(nextPosition, ferry.head)
            }
            NavInstruction.East -> {
                val nextPosition = getNextPosition(ferry.position, CardinalDirection.E, number)
                Ferry(nextPosition, ferry.head)
            }
            NavInstruction.South -> {
                val nextPosition = getNextPosition(ferry.position, CardinalDirection.S, number)
                Ferry(nextPosition, ferry.head)
            }
            NavInstruction.West -> {
                val nextPosition = getNextPosition(ferry.position, CardinalDirection.W, number)
                Ferry(nextPosition, ferry.head)
            }
            NavInstruction.Left -> {
                val nextDirection = turnLeft(ferry.head, number)
                Ferry(ferry.position, nextDirection)
            }
            NavInstruction.Right -> {
                val nextDirection = turnRight(ferry.head, number)
                Ferry(ferry.position, nextDirection)
            }
        }
    }

    val distance = manhattanDistance(ferry.position.x, ferry.position.y)
    println("puzzle23: $distance")
}

private fun getNextPosition(position: Position, wayPoint: WayPoint, steps: Int): Position {
    return Position(position.x + wayPoint.x * steps, position.y + wayPoint.y * steps)
}

private fun manhattanDistance(x: Int, y: Int): Int {
    return abs(x) + abs(y)
}

private fun turnLeft(head: CardinalDirection, degree: Int): CardinalDirection {
    val directionsByOrder = listOf(CardinalDirection.N, CardinalDirection.W, CardinalDirection.S, CardinalDirection.E)
    val currentIndex = directionsByOrder.indexOf(head)
    val nextIndex = (currentIndex + degree / 90) % 4
    return directionsByOrder[nextIndex]
}

private fun turnRight(head: CardinalDirection, degree: Int): CardinalDirection {
    val directionsByOrder = listOf(CardinalDirection.N, CardinalDirection.E, CardinalDirection.S, CardinalDirection.W)
    val currentIndex = directionsByOrder.indexOf(head)
    val nextIndex = (currentIndex + degree / 90) % 4
    return directionsByOrder[nextIndex]
}

private fun getNextPosition(position: Position, head: CardinalDirection, steps: Int): Position {
    return when (head) {
        CardinalDirection.E -> position.copy(x = position.x + steps)
        CardinalDirection.N -> position.copy(y = position.y + steps)
        CardinalDirection.S -> position.copy(y = position.y - steps)
        CardinalDirection.W -> position.copy(x = position.x - steps)
    }
}

data class Ferry(val position: Position, val head: CardinalDirection)
data class Ferry2(val position: Position, val wayPoint: WayPoint)
data class Position(val x: Int, val y: Int)
data class WayPoint(val x: Int, val y: Int)


private fun parseInput(input: String): List<Pair<NavInstruction, Int>> {
    val rawInstructions = input.split("\n")
    return rawInstructions.map { rawInstruction ->
        val navInstruction = when (rawInstruction[0]) {
            'N' -> NavInstruction.North
            'E' -> NavInstruction.East
            'S' -> NavInstruction.South
            'W' -> NavInstruction.West
            'L' -> NavInstruction.Left
            'R' -> NavInstruction.Right
            'F' -> NavInstruction.Forward
            else -> throw IllegalArgumentException("Illegal letter")
        }

        val number = rawInstruction.subSequence(1, rawInstruction.length)
            .toString()
            .toInt()

        navInstruction to number
    }
}

private enum class NavInstruction {
    North, East, South, West, Left, Right, Forward
}

enum class CardinalDirection {
    N, E, S, W
}


private val sampleInput = """
F10
N3
F7
R90
F11
""".trimIndent()

private val input = """
F70
S4
E3
S4
L90
N4
R90
W3
F75
S5
L90
E1
S4
F98
N4
R90
S3
L90
W1
F39
W2
L90
E1
F99
S3
E5
F63
N4
F26
E1
R180
F58
N3
F4
E1
F45
E4
R90
E3
F76
S1
F22
R90
N1
W1
F76
W1
N5
E3
L180
S5
F87
W4
L90
F9
S2
F11
N4
L180
S3
R90
F92
L90
S1
E4
R90
W1
F1
S2
L90
F27
N3
E1
N1
E3
L180
S1
S5
R180
W5
W5
F60
S5
W5
L270
N3
R90
F65
S5
F53
W5
L90
N1
W5
L180
F87
W2
R180
S2
F77
N1
F81
L180
E5
N5
W4
L90
W4
L90
E3
N2
L90
W2
S1
F19
W1
F82
N4
R270
E5
L90
N3
R90
F81
L270
W3
R90
L270
N3
F53
E2
F84
R90
S2
F39
R180
N1
L90
F11
S2
W5
F20
W1
N4
R90
F76
E3
S5
E3
S5
W5
S2
L90
N3
E3
S5
F27
W1
L90
F65
W3
R180
F84
W2
N5
F43
L180
W3
F11
W2
R90
N1
R90
N5
W1
S4
N4
F88
N3
F87
W3
L90
F77
S5
F18
N4
F97
E5
S5
R90
F94
N5
L180
F8
N4
R90
W2
N2
L180
F4
R90
W4
S3
R90
F38
S3
E1
N5
F4
E3
R90
S4
F95
E5
F77
F32
W5
F3
R90
N1
W3
F96
L270
N2
E2
F30
S3
W2
R90
F57
R90
E1
R90
N5
E1
N4
W4
N1
W2
F47
N5
W3
L90
N4
F50
E3
R90
F27
N3
F78
N2
R90
F100
S3
F67
R90
N4
R90
N4
F88
S4
E2
S2
F31
S5
R90
W3
R180
W2
F97
F31
N1
L90
S4
F50
N3
W2
L180
F85
L180
E3
L90
F95
N4
L90
E1
S2
R180
N2
F19
N5
E5
S1
W5
R90
N1
L180
F76
S4
E5
S2
S5
E3
F53
L90
S3
E4
S1
E1
L90
F54
W1
S1
E2
N1
R90
S3
R90
F63
L90
W4
L90
F47
L90
E5
F23
W2
F97
E3
L90
N4
F54
W3
S4
W3
S2
F67
W1
S4
R90
S5
R90
W4
L180
L90
S4
F19
F42
S4
F91
R90
L180
F64
L180
W4
R90
F32
N3
F18
E2
L180
N4
E2
N1
E4
N4
F54
W5
F50
N3
L90
N5
R90
F100
E4
N1
E3
L90
F8
L90
E4
L270
F95
L90
F44
E5
R90
F79
N5
F61
S2
F71
L90
F4
N3
F25
L180
F7
W4
F96
R90
S1
R90
W1
F9
N2
W5
F1
R90
N2
F36
W4
R90
F96
W2
F26
S2
F28
E4
N1
F33
N5
F51
W2
S1
F40
N3
F67
E3
S2
R90
W1
S3
E3
L90
F75
E3
N5
E2
F52
E3
F7
N4
F4
S4
L90
S2
W5
F85
F7
L180
E1
L90
E2
S3
R180
N3
E2
R90
N5
F6
N2
L90
W1
R90
R90
F91
E2
N4
R90
S2
E3
S3
L90
W3
F61
S1
L90
W3
N2
E1
R180
E2
W5
R90
F65
N4
W3
F54
E1
N3
E5
L180
S4
N3
E5
R90
S3
R90
S4
W4
F31
S5
R90
N2
E3
F49
F47
W3
F79
R270
W2
F90
S3
F73
L180
F14
W4
F27
R90
F75
L90
N5
R90
N4
L90
N4
E2
S1
W1
S4
W5
W1
F7
W5
L180
E1
S1
F82
F36
N2
L90
E1
L90
S4
L180
N2
W3
F21
R270
F18
R180
F93
L90
W2
F4
E1
R90
E2
S3
W4
F30
E1
F69
W5
R90
E2
L180
S4
W1
N1
E3
L90
E3
R90
F69
R90
S2
L90
N4
F13
L90
E2
L90
N2
W2
N5
S4
F70
R90
F67
E4
F62
L270
F98
L90
E5
F15
E5
R90
W3
E2
F25
R180
F7
L180
W4
S3
F42
R180
R270
N1
R180
S2
F37
E2
F72
N5
W5
F61
F43
W3
R90
R270
N5
R270
E4
L90
W4
F31
F43
L180
S3
W4
R90
F20
E2
S5
L90
F75
R90
F52
W3
L90
N5
W5
N4
R90
F52
W3
F91
E1
N2
F81
R90
E2
L90
F24
E2
L180
E1
F55
E1
L90
E5
R90
F23
S3
R180
S3
F8
L180
S1
N3
F90
N5
W3
N4
L90
N3
W5
R90
E4
S4
F89
W3
N2
R90
F18
R180
W5
E4
F100
N4
F40
E3
S2
E2
F16
R90
S2
L180
F58
W1
F70
S1
R90
W3
L90
S4
F48
R90
W1
N5
E3
R90
E1
L90
F1
R90
N1
E3
F39
W3
R90
E3
L90
N5
R90
S3
W4
R180
E1
S3
F56
L90
F98
N2
W4
F67
R90
W3
S1
F33
R90
F42
L90
R90
E4
R90
E3
F74
E4
R270
F62
S5
L90
E4
F21
""".trimIndent()