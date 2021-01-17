package com.yanbin.aoc2020

fun main() {
    val joltageRatings = input
        .split("\n")
        .map { it.toInt() }

    puzzle19(joltageRatings)
    puzzle20(joltageRatings)
}

private fun puzzle19(joltageRatings: List<Int>) {
    val sortedRatings = joltageRatings.sorted()
    val lowerRatings = listOf(0) + sortedRatings
    val higherRatings = sortedRatings + listOf(sortedRatings.last() + 3)

    val differenceMap = higherRatings.zip(lowerRatings)
        .fold(mapOf<Int, Int>()) { map, (high, low) ->
            val diff = high - low
            val currentCount = map[diff]
            val diffCount = if (currentCount != null) {
                currentCount + 1
            } else {
                1
            }
            map.addOrReplace(diff, diffCount)
        }

    val answer = (differenceMap[1] ?: 0) * (differenceMap[3] ?: 0)

    println("puzzle19: $answer")
}

private fun <K, V> Map<K, V>.addOrReplace(key: K, value: V): Map<K, V> {
    return HashMap(this).also { it[key] = value }
}

private fun puzzle20(joltageRatings: List<Int>) {
    val sortedRatings = joltageRatings.sortedDescending()
    val totalRatings = listOf(sortedRatings.last() + 3) + sortedRatings + listOf(0)
    val arrangementMap = mapOf<Int, Int>()
    val result = totalRatings.fold(arrangementMap) { acc, rating ->
        val diff3 = acc[rating + 3] ?: 0
        val diff2 = acc[rating + 2] ?: 0
        val diff1 = acc[rating + 1] ?: 0

        val arrangements = diff1 + diff2 + diff3
        acc.addOrReplace(rating, if (arrangements == 0) 1 else arrangements)
    }

    val (_, u) = result.minBy { it.key } ?: throw RuntimeException("Length most great than 1")
    println("puzzle19: $u")

}

private val sampleInput = """
16
10
15
5
1
11
7
19
6
12
4
""".trimIndent()

private val sampleInput2 = """
28
33
18
42
31
14
46
20
48
47
24
23
49
45
19
38
39
11
1
32
25
35
8
17
7
9
4
2
34
10
3
""".trimIndent()

private val input = """
70
102
148
9
99
63
40
52
91
39
55
28
54
22
95
61
118
35
14
21
129
82
137
45
7
87
81
25
3
108
41
11
145
18
65
80
115
29
136
42
97
104
117
141
62
121
23
96
24
128
48
1
112
8
34
144
134
116
58
147
51
84
17
126
64
68
135
10
77
105
127
73
111
90
16
103
109
98
146
123
130
69
133
110
30
122
15
74
33
38
83
92
2
53
140
4
""".trimIndent()