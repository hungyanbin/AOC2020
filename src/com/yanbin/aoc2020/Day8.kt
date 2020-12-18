package com.yanbin.aoc2020

import java.lang.IllegalArgumentException

fun main() {
    val instructions = parseInstruction(input)
    puzzle15(instructions)
    puzzle16(instructions)
}

private fun puzzle16(instructions: List<Instruction>) {
    val replacements = listOf(replaceJmpByNop, replaceNopByJmp).asSequence()
    val range = (0..instructions.lastIndex).asSequence()

    val curriedReplacements = range.flatMap { index ->
        replacements.map { replacement ->
            replacement.curried()(index)
        }
    }

    val answer = curriedReplacements.map { curriedReplacement: (List<Instruction>) -> List<Instruction> ->
        val candidate = curriedReplacement(instructions)

        if (candidate.isEmpty()) {
            Int.MIN_VALUE
        } else {
            checkLoopCompiler(0, candidate, 0, listOf())
        }
    }.first { accumulator -> accumulator != Int.MIN_VALUE }

    println("puzzle16:  $answer")
}

fun <A, B, R> ((A, B) -> R).curried(): (A) -> (B) -> R = { a ->
    { b ->
        this(a, b)
    }
}

private val replaceNopByJmp: (Int, List<Instruction>) -> List<Instruction> = { index, instructions ->
    val instruction = instructions[index]
    if (instruction is Instruction.NOP) {
        instructions.subList(0, index) + listOf(Instruction.JMP(instruction.number)) + instructions.subList(index + 1, instructions.size)
    } else {
        emptyList()
    }
}

private val replaceJmpByNop: (Int, List<Instruction>) -> List<Instruction> = { index, instructions ->
    val instruction = instructions[index]
    if (instruction is Instruction.JMP) {
        instructions.subList(0, index) + listOf(Instruction.NOP(instruction.number)) + instructions.subList(index + 1, instructions.size)
    } else {
        emptyList()
    }
}

private fun puzzle15(instructions: List<Instruction>) {
    val answer = compiler(0, instructions, 0, listOf())
    println("puzzle15:  $answer")
}

private tailrec fun compiler(accumulator: Int, code: List<Instruction>, cursor: Int, lineTrace: List<Int>): Int {
    val instruction = code[cursor]
    if (lineTrace.contains(cursor)) {
        return accumulator
    }

    val newLineTrace = lineTrace + listOf(cursor)

    return when (instruction) {
        is Instruction.NOP -> compiler(accumulator, code, cursor + 1, newLineTrace)
        is Instruction.ACC -> compiler(accumulator + instruction.number, code, cursor + 1, newLineTrace)
        is Instruction.JMP -> compiler(accumulator, code, cursor + instruction.number, newLineTrace)
    }
}

private tailrec fun checkLoopCompiler(accumulator: Int, code: List<Instruction>, cursor: Int, lineTrace: List<Int>): Int {
    if (lineTrace.contains(cursor)) {
        return Int.MIN_VALUE
    } else if (cursor > code.lastIndex) {
        return accumulator
    }

    val instruction = code[cursor]

    val newLineTrace = lineTrace + listOf(cursor)

    return when (instruction) {
        is Instruction.NOP -> checkLoopCompiler(accumulator, code, cursor + 1, newLineTrace)
        is Instruction.ACC -> checkLoopCompiler(accumulator + instruction.number, code, cursor + 1, newLineTrace)
        is Instruction.JMP -> checkLoopCompiler(accumulator, code, cursor + instruction.number, newLineTrace)
    }
}

fun parseInstruction(input: String): List<Instruction> {
    val rawInstructions = input.split("\n")

    val parseNumber: (String) -> Int = { number ->
        if (number.substring(0, 1) == "+") {
            number.substring(1, number.length).toInt()
        } else {
            -number.substring(1, number.length).toInt()
        }
    }
    return rawInstructions.map {
        val data = it.split(" ")
        when (data[0]) {
            "nop" -> Instruction.NOP(parseNumber(data[1]))
            "acc" -> Instruction.ACC(parseNumber(data[1]))
            "jmp" -> Instruction.JMP(parseNumber(data[1]))
            else -> throw IllegalArgumentException("illegal type: ${data[0]}")
        }
    }
}

sealed class Instruction(val number: Int) {
    class NOP(number: Int) : Instruction(number)
    class ACC(number: Int) : Instruction(number)
    class JMP(number: Int) : Instruction(number)

    override fun toString(): String {
        return "${this::class.java.simpleName}(number=$number)"
    }

}

private val sampleInput = """
nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6
""".trimIndent()

private val input = """
acc +33
acc -7
acc +39
jmp +214
jmp +250
jmp +51
acc +29
acc +6
acc +20
jmp +489
nop +181
acc +4
jmp +187
nop +454
acc -10
acc +44
jmp +343
acc +14
acc +24
acc +37
acc -12
jmp +596
acc +21
acc +39
jmp +601
acc -15
jmp +304
acc -7
jmp +302
acc +38
jmp +148
acc -6
jmp +235
acc +6
nop +429
acc +49
acc +3
jmp +255
acc +2
jmp +10
acc +27
acc +0
acc -3
acc +28
jmp +565
acc -16
acc +39
acc -5
jmp +513
acc +43
acc +24
jmp +26
nop +19
nop +71
nop +182
jmp +477
acc +42
jmp +535
acc +38
acc +29
acc +1
jmp +1
jmp +72
acc +25
acc +43
acc +6
jmp +1
jmp +111
acc +43
acc +13
jmp +30
acc +4
acc +24
acc +20
acc -14
jmp +161
jmp +73
nop +108
jmp +547
nop +273
acc -8
nop +358
nop +284
jmp +526
acc +50
jmp +274
jmp +486
nop +167
acc -13
jmp +11
acc +10
jmp +508
acc -11
acc +46
acc +44
jmp +335
jmp +1
acc -16
acc +30
jmp +289
acc +15
nop +265
jmp +1
nop +68
jmp +107
acc -15
jmp -101
acc +28
acc -13
jmp +17
acc +21
acc +46
acc +19
acc -8
jmp +274
nop +237
jmp -111
nop +419
acc +28
acc +26
jmp +275
acc -4
jmp +483
jmp +1
jmp +201
jmp +234
acc +26
acc +21
acc +18
jmp +149
acc +0
acc +29
acc +11
jmp -41
nop +111
nop +212
jmp +172
acc +31
acc +17
acc +6
jmp -40
acc +7
acc +44
acc +41
acc +4
jmp -74
acc -16
acc +37
jmp +119
acc -13
acc +44
acc +21
acc +38
jmp +92
acc +30
jmp +444
jmp +35
acc +3
acc +11
acc +31
jmp -104
acc -10
acc +5
acc +8
acc +31
jmp +127
nop +168
acc +16
acc +6
acc +0
jmp +455
acc +15
acc +0
acc +22
acc -1
jmp +191
acc +16
jmp +56
acc -12
acc +40
nop -140
acc +44
jmp +138
acc +44
jmp +237
acc +15
acc +40
jmp +360
acc +14
acc +14
jmp +185
nop +211
acc +27
acc -8
acc +17
jmp +247
acc +50
acc -2
jmp -49
acc +37
jmp +330
acc +14
acc +44
acc +15
nop -43
jmp +382
jmp -45
acc +46
acc -11
acc +47
jmp +61
nop +252
acc +44
acc -13
jmp +292
acc -6
jmp +199
acc +44
acc +28
acc +17
acc +31
jmp -158
acc -8
jmp +338
acc +0
acc -2
nop +306
jmp -78
acc +11
acc +33
acc +40
acc +33
jmp -169
jmp +273
acc +8
jmp -135
acc +20
acc -14
acc -15
nop +370
jmp +20
nop +51
acc -4
acc -10
jmp -215
acc +22
acc +22
jmp +209
acc +40
acc -18
jmp -158
jmp -130
acc +13
jmp -169
nop +225
acc +7
jmp -23
acc +21
acc +0
jmp +273
jmp +293
acc +39
jmp -71
acc +20
jmp +49
acc +6
jmp -60
acc +35
jmp +84
acc +14
jmp +266
acc +47
jmp -247
acc -3
acc +47
acc +23
acc +30
jmp +105
acc +18
jmp +109
jmp -188
nop -70
acc -2
acc +0
jmp +195
acc +15
jmp +246
acc +49
acc +28
jmp -18
nop +120
jmp +91
acc -15
acc +15
acc +30
jmp +39
acc +46
nop +250
acc +49
jmp -250
acc -10
acc +0
acc +39
jmp -254
nop +55
acc -4
acc -3
jmp +88
jmp +35
acc +47
nop -154
acc -16
jmp +271
nop +253
jmp -199
acc +5
acc +35
jmp +1
acc +49
jmp +234
acc +27
acc +33
acc -3
jmp -138
jmp -107
acc -11
acc +47
acc +14
jmp -288
jmp -205
acc +0
jmp +191
acc -15
jmp -116
acc +35
nop +121
acc +2
acc -14
jmp +223
acc +33
acc -10
acc +24
jmp +73
acc +39
jmp +255
acc +19
jmp -16
nop +1
jmp -177
nop +107
nop -194
jmp +260
acc -16
acc -12
jmp -148
acc +11
acc +18
acc +33
jmp +84
acc +27
acc -13
acc +36
acc +26
jmp +100
nop -110
jmp -98
acc -2
acc +29
acc +25
acc -8
jmp +128
acc +16
acc +1
acc +7
jmp -290
acc +18
nop -235
acc +0
jmp -127
acc -18
acc +38
jmp -297
acc +19
acc -8
acc +20
acc +3
jmp -230
jmp -67
jmp +124
acc -15
acc +26
acc -19
jmp +120
jmp +173
jmp -338
acc -15
jmp -309
acc +19
acc +26
acc +18
acc +8
jmp -6
acc -7
acc +10
jmp -375
acc +5
acc -16
acc +18
acc +46
jmp -309
acc +48
acc +40
nop -227
jmp -380
jmp -290
acc +46
acc +5
jmp -154
acc -9
acc +15
jmp -187
acc -10
acc +0
acc +28
acc +30
jmp -284
acc +43
acc +25
acc +14
jmp -205
acc -13
acc +1
nop -340
jmp -326
jmp +1
acc +9
acc +17
acc +1
jmp -346
jmp -158
acc +23
jmp -26
nop -257
jmp +140
acc +11
acc +10
acc +29
acc +48
jmp +177
acc +28
acc -12
acc -19
acc +37
jmp +79
acc -14
jmp -184
nop +153
jmp -170
acc -17
acc +10
acc -6
nop -174
jmp -391
jmp +148
acc +50
acc -8
jmp -426
jmp +1
acc +16
jmp +20
jmp +1
jmp -217
nop +84
jmp +71
acc +16
acc -7
acc +23
acc +24
jmp -329
acc +9
acc -7
acc -4
nop +117
jmp -16
acc +30
nop -222
acc +32
acc +9
jmp -175
acc +18
acc +15
acc +41
jmp -192
acc -3
acc +8
acc -13
acc +24
jmp -210
acc +17
acc -7
acc -19
jmp +76
acc +26
acc +2
acc +4
jmp +27
jmp -104
acc +38
acc +46
nop -67
nop +37
jmp -186
jmp +5
acc +37
acc +8
acc +30
jmp -409
acc +44
acc +4
jmp +109
nop -8
jmp -395
acc +20
acc +12
acc +16
acc +9
jmp -87
nop -406
acc -8
jmp -209
jmp -137
jmp -179
acc +44
jmp -399
nop -141
jmp +18
jmp +1
nop +55
jmp +39
acc +20
acc +40
acc +44
acc +45
jmp +74
acc -16
jmp -170
jmp -48
jmp -537
acc -9
acc +6
nop -101
acc +2
jmp -418
jmp -81
jmp +1
jmp -338
nop +43
acc +20
jmp -109
acc -1
jmp -343
acc +29
acc +11
nop -439
jmp -310
jmp -374
acc +33
nop +25
acc -16
nop -333
jmp -14
jmp -5
jmp -162
nop -432
acc +16
acc +17
jmp -87
acc -16
nop -265
acc +20
jmp -356
acc +0
jmp +5
acc +39
acc -15
jmp -325
jmp -39
nop -376
nop -116
acc +38
jmp -175
jmp -450
jmp +1
acc +19
jmp -58
nop -39
acc +40
acc +42
jmp -232
acc -14
jmp -17
acc +4
acc -9
acc +45
jmp -229
jmp -18
acc +13
acc +17
jmp -591
jmp -604
jmp -356
acc +1
acc +18
nop -52
acc +39
jmp -361
jmp -303
acc +8
nop -477
acc +3
acc -8
jmp -404
acc +24
acc +5
jmp -88
acc +27
jmp -54
jmp -18
acc +31
acc +40
acc +18
acc -16
jmp +1
""".trimIndent()