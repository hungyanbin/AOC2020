package com.yanbin.aoc2020

fun main() {
    val bags = parseLuggageInput(input)
    puzzle13(bags)
    puzzle14(bags)
}

private fun puzzle14(bags: List<Bag>) {
    val bagMap: Map<String, List<Pair<String, Int>>> = bags.map { it.color to it.capacity.toList() }
            .toMap()

    val countContainingBags = countContainingBags(bagMap, "shiny gold")
    println("puzzle13: $countContainingBags")
}

private fun countContainingBags(bagMap: Map<String, List<Pair<String, Int>>>, color: String): Int {
    val containingBags = bagMap.getValue(color)
    val sum = containingBags.sumBy { it.second }
    return if (containingBags.isEmpty()) {
        sum
    } else {
        sum + containingBags.map { (color, count) ->
            countContainingBags(bagMap, color) * count
        }.sum()
    }
}

private fun puzzle13(bags: List<Bag>) {
    val colors = listBagColors(bags, listOf("shiny gold"))
            .distinct()
    println("puzzle13: ${colors.size}")
}

private fun listBagColors(bags: List<Bag>, colors: List<String>): List<String> {
    val newColors = findReferencingBagColors(bags, colors)
    return if (newColors.isEmpty()) {
        emptyList()
    } else {
        listBagColors(bags, newColors) + newColors
    }
}

private fun findReferencingBagColors(bags: List<Bag>, colors: List<String>): List<String> {
    return bags.flatMap { bag ->
        colors.mapNotNull { color ->
            if (bag.capacity.containsKey(color)){
                bag.color
            } else {
                null
            }
        }
    }
}

private fun parseLuggageInput(input: String): List<Bag> {
    val rawBags = input.split("\n")
    return rawBags.map { rawBag ->
        val begColorAndCapacity = rawBag.split(" contain ")
        val begColor = begColorAndCapacity[0].removeSuffix("bags").trim()
        val capacity = parseCapacity(begColorAndCapacity[1])
        Bag(begColor, capacity)
    }
}

fun parseCapacity(rawCapacity: String): Map<String, Int> {
    return if (rawCapacity.startsWith("no")) {
        emptyMap()
    } else {
        val containingBags = rawCapacity.split(", ")
        containingBags.map { containingBag ->
            val number = containingBag.replace(Regex("[^0-9]"), "").trim().toInt()
            val color = containingBag.replace(Regex("\\d|bags|bag|\\."), "").trim()
            color to number
        }.toMap()
    }
}

private val sampleInput = """
light red bags contain 1 bright white bag, 2 muted yellow bags.
dark orange bags contain 3 bright white bags, 4 muted yellow bags.
bright white bags contain 1 shiny gold bag.
muted yellow bags contain 2 shiny gold bags, 9 faded blue bags.
shiny gold bags contain 1 dark olive bag, 2 vibrant plum bags.
dark olive bags contain 3 faded blue bags, 4 dotted black bags.
vibrant plum bags contain 5 faded blue bags, 6 dotted black bags.
faded blue bags contain no other bags.
dotted black bags contain no other bags.    
""".trimIndent()

private val sampleInput2 = """
shiny gold bags contain 2 dark red bags.
dark red bags contain 2 dark orange bags.
dark orange bags contain 2 dark yellow bags.
dark yellow bags contain 2 dark green bags.
dark green bags contain 2 dark blue bags.
dark blue bags contain 2 dark violet bags.
dark violet bags contain no other bags.   
""".trimIndent()

private val input = """
light salmon bags contain 5 dark brown bags, 2 dotted coral bags, 5 mirrored turquoise bags.
drab magenta bags contain 1 vibrant purple bag, 5 dark lime bags, 2 clear silver bags.
striped coral bags contain 2 dim lime bags.
drab cyan bags contain 1 dark lime bag.
bright purple bags contain 5 posh salmon bags, 1 posh blue bag, 1 shiny lavender bag.
dull tomato bags contain 2 shiny maroon bags, 1 posh salmon bag, 4 posh teal bags, 5 dark orange bags.
faded aqua bags contain 3 striped salmon bags, 5 dim purple bags, 3 shiny gold bags, 4 drab indigo bags.
drab maroon bags contain 2 dark gray bags, 3 mirrored tan bags, 4 pale blue bags, 4 dim black bags.
posh blue bags contain 1 mirrored maroon bag, 4 striped lavender bags.
dull orange bags contain 2 light aqua bags, 2 striped chartreuse bags, 4 dim bronze bags.
vibrant fuchsia bags contain 1 clear orange bag, 1 plaid turquoise bag, 3 posh maroon bags, 4 light silver bags.
posh violet bags contain 5 plaid violet bags, 5 vibrant turquoise bags, 1 pale orange bag.
light gray bags contain 5 vibrant tan bags, 4 shiny tomato bags, 5 muted olive bags.
striped teal bags contain 2 drab cyan bags, 3 dull tomato bags, 1 light gold bag, 1 dark beige bag.
muted orange bags contain 3 pale blue bags, 1 muted coral bag.
wavy magenta bags contain 1 dotted crimson bag.
dark fuchsia bags contain 3 plaid yellow bags, 4 dim green bags.
drab beige bags contain 4 dull brown bags.
light olive bags contain 2 posh magenta bags, 4 dim crimson bags.
wavy chartreuse bags contain 3 plaid magenta bags.
dim cyan bags contain 4 light lime bags, 3 dim crimson bags, 1 striped green bag.
vibrant violet bags contain 2 dark teal bags, 5 vibrant turquoise bags.
drab tomato bags contain 2 vibrant salmon bags, 5 bright salmon bags.
mirrored lime bags contain 5 striped teal bags, 3 dim lavender bags, 4 dim cyan bags, 2 dotted green bags.
wavy plum bags contain 4 plaid magenta bags.
dim brown bags contain 1 dull yellow bag, 2 plaid beige bags.
posh magenta bags contain 4 vibrant turquoise bags.
posh maroon bags contain 3 dark teal bags, 2 striped plum bags.
pale magenta bags contain 4 dim chartreuse bags, 3 mirrored salmon bags.
shiny blue bags contain 2 shiny violet bags, 3 pale red bags, 5 pale salmon bags.
clear tan bags contain 3 wavy lavender bags.
pale bronze bags contain 3 muted chartreuse bags.
pale tomato bags contain 1 dotted brown bag, 1 posh lavender bag, 2 striped violet bags.
dotted lime bags contain 3 light magenta bags, 2 clear yellow bags, 3 faded olive bags.
shiny tan bags contain 2 dotted fuchsia bags, 5 dull crimson bags, 5 clear orange bags.
light lavender bags contain 3 shiny tomato bags, 5 drab beige bags, 3 mirrored yellow bags.
wavy bronze bags contain 2 muted green bags, 3 dotted bronze bags.
faded brown bags contain 3 drab gold bags, 3 dotted red bags, 1 dim white bag.
muted cyan bags contain 4 striped turquoise bags.
faded tan bags contain 1 muted red bag, 1 mirrored tan bag, 4 shiny purple bags.
dark violet bags contain 4 posh tomato bags.
posh purple bags contain 3 shiny violet bags, 4 clear indigo bags.
vibrant orange bags contain 1 dim salmon bag, 1 vibrant olive bag, 4 drab fuchsia bags.
dull blue bags contain 5 pale lavender bags, 3 dark lavender bags, 1 mirrored yellow bag.
muted white bags contain 3 dark salmon bags, 4 posh bronze bags, 5 pale plum bags.
striped lavender bags contain 4 faded plum bags, 2 dark tomato bags, 2 plaid teal bags.
dark gold bags contain 4 dark lime bags.
dull magenta bags contain 2 muted chartreuse bags.
striped cyan bags contain 4 dotted red bags, 3 drab lime bags.
dotted bronze bags contain 1 clear magenta bag, 2 bright salmon bags.
clear lavender bags contain 4 dim lime bags.
plaid salmon bags contain 2 dark orange bags.
posh silver bags contain 5 muted fuchsia bags, 4 muted crimson bags.
bright plum bags contain 3 vibrant salmon bags, 3 clear tan bags.
posh beige bags contain 3 mirrored lavender bags, 4 pale olive bags, 2 dotted black bags.
bright yellow bags contain 3 mirrored violet bags.
plaid green bags contain 5 striped black bags, 5 posh bronze bags, 5 mirrored teal bags.
plaid coral bags contain 5 plaid beige bags, 5 drab chartreuse bags.
dotted maroon bags contain 4 dotted indigo bags, 4 dotted coral bags.
dotted chartreuse bags contain 2 wavy turquoise bags, 2 posh indigo bags, 3 plaid silver bags, 2 mirrored salmon bags.
dark yellow bags contain 1 posh brown bag, 4 light tan bags, 2 clear fuchsia bags, 5 dotted black bags.
dull lavender bags contain 4 plaid green bags, 1 striped plum bag, 3 drab teal bags.
bright maroon bags contain 1 dotted tan bag, 4 light olive bags, 5 posh cyan bags.
vibrant gold bags contain 2 faded gold bags.
dim lavender bags contain 3 dark silver bags, 1 striped violet bag, 2 dotted bronze bags, 1 striped black bag.
light tomato bags contain 2 striped chartreuse bags.
dull yellow bags contain 5 drab tomato bags.
dotted coral bags contain 1 dark blue bag, 1 mirrored teal bag, 1 drab tomato bag.
pale lime bags contain 3 clear cyan bags, 5 pale silver bags, 4 wavy olive bags.
wavy red bags contain 3 vibrant gray bags, 4 shiny teal bags, 4 clear maroon bags.
pale beige bags contain 2 shiny teal bags, 2 posh fuchsia bags, 4 dark aqua bags, 4 posh orange bags.
striped tomato bags contain 2 light coral bags.
shiny violet bags contain 4 vibrant chartreuse bags, 3 wavy lavender bags.
plaid teal bags contain 3 mirrored teal bags.
clear black bags contain 2 faded beige bags, 4 faded tan bags, 1 light salmon bag, 5 wavy white bags.
clear yellow bags contain 2 dull tan bags, 5 plaid gold bags.
faded green bags contain 5 light brown bags.
bright coral bags contain 5 dotted teal bags, 2 mirrored gray bags, 3 wavy lime bags.
muted bronze bags contain 3 dim maroon bags, 3 shiny beige bags, 4 light bronze bags.
vibrant crimson bags contain 4 dim brown bags, 3 dotted maroon bags, 4 vibrant gold bags, 5 dull tomato bags.
pale green bags contain 4 plaid beige bags.
dull green bags contain 2 clear salmon bags, 2 dark bronze bags.
dotted tan bags contain 1 dim tan bag.
muted plum bags contain 3 faded crimson bags, 5 muted violet bags, 5 striped violet bags, 2 drab silver bags.
faded coral bags contain 2 dark plum bags, 1 muted cyan bag, 3 vibrant beige bags.
vibrant brown bags contain 4 striped maroon bags, 4 bright cyan bags.
drab coral bags contain 3 vibrant coral bags, 3 dark aqua bags.
striped chartreuse bags contain 2 vibrant salmon bags, 2 posh bronze bags, 3 drab teal bags.
bright indigo bags contain 4 faded plum bags.
clear olive bags contain 4 muted turquoise bags, 1 dotted olive bag.
drab yellow bags contain 1 dim brown bag, 4 plaid green bags, 2 posh red bags.
striped purple bags contain 2 mirrored orange bags, 4 vibrant chartreuse bags, 1 pale orange bag, 2 shiny green bags.
mirrored olive bags contain 1 faded gold bag, 1 vibrant chartreuse bag, 5 faded brown bags, 3 wavy indigo bags.
dull aqua bags contain 3 muted black bags, 2 muted orange bags, 2 pale lime bags, 4 dark plum bags.
light lime bags contain 5 plaid beige bags, 5 shiny gold bags, 3 plaid plum bags.
vibrant beige bags contain 2 posh maroon bags, 3 dotted plum bags.
mirrored yellow bags contain 4 dotted crimson bags, 3 drab olive bags, 1 faded bronze bag, 2 striped violet bags.
mirrored plum bags contain 4 dotted gray bags, 1 striped fuchsia bag, 3 dim yellow bags.
faded beige bags contain 5 striped chartreuse bags, 3 faded bronze bags, 3 dark lime bags.
drab crimson bags contain 2 dull green bags, 1 muted teal bag.
shiny salmon bags contain 3 wavy olive bags, 1 faded crimson bag, 1 clear chartreuse bag.
vibrant turquoise bags contain 4 drab black bags.
dim yellow bags contain 5 shiny blue bags.
wavy maroon bags contain 5 pale brown bags.
dark coral bags contain 2 bright teal bags, 3 bright green bags, 3 drab coral bags.
bright beige bags contain 3 faded salmon bags, 5 plaid coral bags.
posh gray bags contain 5 bright silver bags, 1 pale brown bag.
plaid orange bags contain 3 faded olive bags, 4 muted fuchsia bags, 5 vibrant turquoise bags.
faded indigo bags contain 1 muted silver bag, 3 dim green bags, 3 faded purple bags, 1 plaid beige bag.
drab lavender bags contain 5 light blue bags.
light coral bags contain 3 pale orange bags, 2 plaid teal bags.
posh indigo bags contain 2 drab olive bags, 2 dim cyan bags, 2 light teal bags.
muted fuchsia bags contain 2 wavy white bags, 1 dark tan bag.
muted brown bags contain 5 drab coral bags, 4 dull brown bags, 4 mirrored black bags.
bright aqua bags contain 3 clear orange bags.
faded magenta bags contain 1 striped violet bag, 1 dull lavender bag.
clear brown bags contain 4 mirrored green bags, 1 clear fuchsia bag, 2 vibrant violet bags, 1 shiny aqua bag.
clear green bags contain 3 dotted brown bags, 2 faded plum bags, 3 dotted beige bags, 2 striped brown bags.
dull violet bags contain 2 vibrant violet bags, 4 light coral bags, 3 clear black bags.
light gold bags contain no other bags.
striped silver bags contain 4 clear bronze bags, 1 striped plum bag, 3 dull yellow bags.
dull indigo bags contain 5 wavy olive bags, 3 shiny tomato bags, 3 vibrant lime bags, 4 dark beige bags.
dark teal bags contain 2 dark blue bags, 4 mirrored teal bags, 4 posh bronze bags, 1 muted gold bag.
light cyan bags contain 3 drab olive bags, 2 plaid teal bags, 5 vibrant indigo bags.
clear turquoise bags contain 3 bright teal bags.
clear white bags contain 4 faded black bags.
plaid olive bags contain 3 vibrant blue bags, 5 posh gold bags.
drab turquoise bags contain 3 striped black bags, 1 dull brown bag, 5 dark lime bags.
plaid tomato bags contain 1 dim orange bag.
wavy lime bags contain 3 muted crimson bags, 2 shiny green bags.
dim salmon bags contain 4 faded black bags, 4 mirrored teal bags, 4 drab tomato bags, 2 vibrant green bags.
pale lavender bags contain 2 striped violet bags.
pale violet bags contain 5 dim magenta bags, 1 muted cyan bag, 5 clear red bags, 2 pale fuchsia bags.
dark purple bags contain 2 light red bags.
drab blue bags contain 2 dark blue bags, 2 dotted bronze bags, 4 bright teal bags.
wavy white bags contain 3 dim red bags, 1 vibrant chartreuse bag, 3 muted gray bags.
muted beige bags contain 1 shiny purple bag.
dim aqua bags contain 1 muted yellow bag, 3 drab green bags, 2 plaid magenta bags.
pale orange bags contain 5 mirrored teal bags, 4 dark aqua bags, 5 shiny gold bags.
clear coral bags contain 5 dark white bags, 5 light blue bags.
shiny orange bags contain 3 vibrant black bags, 2 pale purple bags, 2 drab salmon bags, 5 light blue bags.
wavy gray bags contain 3 bright red bags, 3 plaid yellow bags, 1 muted cyan bag, 1 plaid maroon bag.
plaid chartreuse bags contain 3 dotted brown bags, 3 pale lime bags.
shiny green bags contain 1 muted blue bag, 4 dull blue bags, 5 mirrored lavender bags, 1 posh coral bag.
faded black bags contain 4 posh bronze bags, 5 vibrant coral bags, 1 plaid teal bag.
wavy yellow bags contain 5 mirrored indigo bags, 4 clear salmon bags, 2 bright tomato bags.
plaid gold bags contain 5 vibrant violet bags, 4 faded violet bags.
striped maroon bags contain 2 dark aqua bags, 3 mirrored teal bags.
plaid white bags contain 3 mirrored green bags.
dim gray bags contain 1 drab cyan bag, 1 plaid plum bag.
light aqua bags contain 4 pale olive bags, 1 dark olive bag, 1 wavy black bag, 3 drab lime bags.
dim tan bags contain 4 dark teal bags.
mirrored fuchsia bags contain 4 posh lavender bags, 1 drab coral bag, 1 faded white bag.
bright tomato bags contain 4 faded beige bags.
dim plum bags contain 3 striped yellow bags, 1 mirrored maroon bag, 4 shiny gray bags, 5 bright plum bags.
bright white bags contain 5 shiny lavender bags, 1 drab salmon bag.
pale gold bags contain 2 faded olive bags.
plaid red bags contain 4 plaid aqua bags, 3 pale brown bags.
striped violet bags contain no other bags.
pale white bags contain 5 faded silver bags, 3 drab silver bags, 1 mirrored white bag, 1 muted violet bag.
posh bronze bags contain 4 pale brown bags.
dull black bags contain 5 vibrant chartreuse bags, 3 striped indigo bags, 2 drab fuchsia bags, 1 vibrant gold bag.
light indigo bags contain 1 light green bag, 3 striped silver bags.
mirrored indigo bags contain 5 pale tan bags, 5 plaid plum bags, 3 wavy aqua bags, 5 drab violet bags.
clear cyan bags contain 5 drab teal bags.
clear orange bags contain 5 mirrored purple bags.
clear gray bags contain 1 wavy brown bag, 5 drab teal bags, 5 muted coral bags.
striped green bags contain no other bags.
plaid black bags contain 5 dim yellow bags, 4 mirrored blue bags, 5 plaid plum bags, 1 striped aqua bag.
wavy violet bags contain 2 clear brown bags, 5 mirrored silver bags, 5 vibrant silver bags.
dotted olive bags contain 1 posh bronze bag, 5 striped violet bags.
drab green bags contain 1 posh orange bag, 1 clear chartreuse bag.
dotted red bags contain 3 striped plum bags, 2 bright crimson bags.
dim turquoise bags contain 4 striped bronze bags, 3 light plum bags.
wavy green bags contain 3 drab fuchsia bags, 3 shiny brown bags.
wavy gold bags contain 4 mirrored violet bags.
dark magenta bags contain 3 faded silver bags.
shiny silver bags contain 2 muted orange bags, 2 dull indigo bags, 1 vibrant magenta bag, 3 shiny aqua bags.
pale purple bags contain 1 dark cyan bag, 2 pale brown bags, 3 light silver bags, 1 plaid aqua bag.
pale silver bags contain 2 pale red bags, 2 striped chartreuse bags, 3 faded bronze bags.
vibrant olive bags contain 5 drab turquoise bags, 4 wavy olive bags, 5 plaid beige bags.
wavy coral bags contain 1 clear orange bag.
faded gold bags contain 4 mirrored turquoise bags, 5 dim gray bags, 1 faded bronze bag.
vibrant gray bags contain 3 light tan bags, 5 plaid teal bags, 5 dotted olive bags.
drab aqua bags contain 1 dotted olive bag, 5 dark indigo bags, 3 pale fuchsia bags.
vibrant indigo bags contain 4 pale coral bags, 1 clear green bag, 1 dim chartreuse bag, 5 mirrored turquoise bags.
dotted teal bags contain 5 clear indigo bags, 1 faded bronze bag.
muted salmon bags contain 1 light teal bag, 1 light cyan bag, 3 muted crimson bags.
shiny fuchsia bags contain 1 wavy brown bag, 3 plaid green bags, 2 shiny silver bags.
dark tan bags contain 5 dotted plum bags, 3 striped orange bags, 4 faded gold bags, 4 mirrored turquoise bags.
bright crimson bags contain 5 bright salmon bags, 4 clear salmon bags, 2 faded bronze bags.
clear silver bags contain 3 striped purple bags.
faded yellow bags contain 2 striped turquoise bags, 4 pale purple bags, 5 wavy aqua bags, 3 posh gold bags.
muted gray bags contain no other bags.
light purple bags contain 2 wavy indigo bags, 1 dark white bag, 1 faded beige bag.
clear fuchsia bags contain 2 drab violet bags, 5 drab black bags, 4 shiny tomato bags.
wavy blue bags contain 3 faded beige bags, 2 plaid coral bags.
faded crimson bags contain 4 dark lime bags, 1 striped chartreuse bag.
bright lavender bags contain 5 plaid green bags, 3 striped turquoise bags, 2 dotted black bags.
faded lavender bags contain 5 plaid magenta bags.
dull turquoise bags contain 5 posh bronze bags, 4 dark white bags.
mirrored tan bags contain 4 vibrant white bags.
pale teal bags contain 4 pale aqua bags, 1 shiny maroon bag, 5 vibrant silver bags.
wavy black bags contain 3 dotted teal bags, 1 pale green bag, 5 drab blue bags, 5 dotted coral bags.
shiny turquoise bags contain 2 muted gray bags, 4 dark white bags.
vibrant white bags contain 4 shiny tomato bags.
pale tan bags contain 5 plaid coral bags.
striped white bags contain 3 light cyan bags, 4 mirrored orange bags, 2 dotted brown bags.
dark lime bags contain 1 light gold bag, 1 dotted green bag, 4 dotted brown bags, 1 shiny gold bag.
dim coral bags contain 1 posh black bag, 3 faded coral bags.
shiny crimson bags contain 1 mirrored tan bag.
drab teal bags contain 2 mirrored turquoise bags, 4 vibrant chartreuse bags, 4 mirrored teal bags, 4 light gold bags.
dotted plum bags contain 5 faded bronze bags, 5 vibrant blue bags.
faded tomato bags contain 1 dark aqua bag, 1 striped green bag.
faded red bags contain 2 bright gold bags, 1 clear gray bag.
faded olive bags contain 3 dotted plum bags, 1 dotted coral bag, 3 dim indigo bags, 2 dull tan bags.
dotted black bags contain 1 drab fuchsia bag, 3 dotted bronze bags, 1 pale orange bag, 5 dull salmon bags.
plaid turquoise bags contain 5 drab turquoise bags, 5 plaid violet bags, 4 posh maroon bags.
pale plum bags contain 5 bright chartreuse bags, 5 shiny brown bags.
dull gray bags contain 5 light yellow bags.
dark tomato bags contain 4 mirrored teal bags, 4 clear magenta bags, 1 striped green bag, 1 muted gray bag.
light bronze bags contain 5 muted tan bags, 5 drab maroon bags, 3 dim crimson bags, 2 dull turquoise bags.
dull silver bags contain 1 muted gray bag, 2 posh purple bags.
shiny gray bags contain 2 faded plum bags, 2 striped turquoise bags.
vibrant black bags contain 3 light salmon bags, 3 drab maroon bags.
mirrored silver bags contain 1 plaid beige bag, 3 dim lime bags, 4 dotted brown bags.
posh tomato bags contain 4 plaid white bags, 2 plaid plum bags.
mirrored brown bags contain 5 faded white bags, 2 posh white bags, 5 dark plum bags.
muted yellow bags contain 2 pale yellow bags, 5 posh salmon bags, 1 bright red bag, 1 plaid beige bag.
dull coral bags contain 5 drab white bags, 2 wavy silver bags, 2 light olive bags.
shiny brown bags contain 5 dotted coral bags, 2 dotted brown bags, 2 drab chartreuse bags.
light fuchsia bags contain 3 striped plum bags, 4 dark brown bags, 4 dim lime bags.
bright cyan bags contain 2 shiny violet bags, 1 dark white bag, 2 drab chartreuse bags, 2 dim fuchsia bags.
dotted orange bags contain 4 mirrored black bags, 2 wavy maroon bags, 3 vibrant violet bags.
pale blue bags contain 5 drab cyan bags, 3 plaid green bags, 3 pale red bags, 2 dim crimson bags.
dim red bags contain 2 dark blue bags, 3 muted red bags, 1 striped violet bag, 2 dotted coral bags.
posh plum bags contain 1 wavy silver bag, 3 dark beige bags, 1 dotted gray bag.
drab orange bags contain 5 muted salmon bags, 1 clear crimson bag.
dotted purple bags contain 1 dull yellow bag, 1 vibrant green bag, 4 clear bronze bags.
shiny chartreuse bags contain 4 shiny tomato bags.
shiny maroon bags contain 3 dull lavender bags.
clear magenta bags contain no other bags.
pale cyan bags contain no other bags.
dim olive bags contain 1 striped plum bag, 2 light maroon bags, 5 plaid olive bags, 4 dim violet bags.
faded chartreuse bags contain 5 wavy salmon bags, 5 dull bronze bags, 1 dull silver bag.
posh coral bags contain 3 faded white bags.
dark beige bags contain 3 dim indigo bags, 4 dull tomato bags, 2 muted crimson bags, 2 drab chartreuse bags.
wavy salmon bags contain 4 pale red bags, 3 dull fuchsia bags.
dim magenta bags contain 1 muted fuchsia bag, 5 vibrant violet bags.
faded gray bags contain 5 faded tomato bags, 4 posh violet bags, 2 shiny white bags, 2 dark tomato bags.
light turquoise bags contain 2 dark magenta bags.
dark maroon bags contain 2 clear magenta bags, 4 clear indigo bags.
faded turquoise bags contain 2 vibrant turquoise bags, 1 dim turquoise bag.
dull bronze bags contain 4 mirrored salmon bags, 5 bright plum bags.
posh yellow bags contain 2 vibrant coral bags, 5 dotted coral bags.
drab olive bags contain 5 clear salmon bags.
clear plum bags contain 5 shiny beige bags, 3 vibrant tan bags, 1 clear green bag, 1 dull orange bag.
shiny white bags contain 2 striped lime bags, 2 wavy turquoise bags, 4 pale purple bags, 3 vibrant gray bags.
drab gray bags contain 2 dim chartreuse bags.
light silver bags contain 1 faded fuchsia bag, 1 pale aqua bag, 4 dim green bags.
wavy beige bags contain 4 light tan bags, 5 dark brown bags.
light red bags contain 1 striped plum bag, 3 dark coral bags, 5 striped crimson bags, 5 pale indigo bags.
clear blue bags contain 3 dotted bronze bags, 2 striped chartreuse bags.
dull plum bags contain 3 mirrored coral bags, 1 shiny violet bag, 3 wavy magenta bags, 1 bright crimson bag.
drab fuchsia bags contain 1 dotted bronze bag, 2 vibrant salmon bags, 1 pale turquoise bag, 4 shiny gold bags.
muted gold bags contain 5 dark blue bags, 4 light gold bags.
posh black bags contain 2 wavy beige bags, 1 bright plum bag.
drab purple bags contain 5 muted white bags.
light teal bags contain 5 dull yellow bags, 1 light gold bag.
mirrored teal bags contain no other bags.
mirrored maroon bags contain 5 faded yellow bags, 5 faded gold bags.
dull olive bags contain 4 posh brown bags, 4 muted purple bags, 2 dim beige bags, 5 wavy black bags.
wavy tomato bags contain 1 drab beige bag, 3 vibrant tan bags.
vibrant silver bags contain 3 striped bronze bags, 2 dotted green bags, 4 dull brown bags, 2 muted gray bags.
dark lavender bags contain 2 plaid maroon bags, 1 vibrant blue bag, 5 striped black bags, 1 light teal bag.
mirrored white bags contain 3 posh magenta bags.
wavy aqua bags contain 4 pale silver bags.
wavy orange bags contain 3 drab bronze bags, 3 muted olive bags.
drab violet bags contain 1 vibrant turquoise bag, 5 dull yellow bags, 1 faded white bag.
light crimson bags contain 1 light tan bag, 3 bright teal bags, 5 bright crimson bags.
muted black bags contain 3 drab salmon bags, 1 clear blue bag, 5 wavy lavender bags.
faded salmon bags contain 1 vibrant magenta bag.
wavy brown bags contain 2 posh maroon bags.
posh salmon bags contain 5 wavy brown bags, 5 shiny brown bags, 2 dark lime bags.
posh chartreuse bags contain 3 wavy green bags, 3 drab maroon bags, 5 wavy violet bags, 3 clear turquoise bags.
muted coral bags contain 3 vibrant tan bags, 1 bright tomato bag, 1 vibrant salmon bag, 5 light green bags.
light black bags contain 4 bright olive bags.
light white bags contain 5 pale lavender bags.
striped aqua bags contain 5 dim gray bags.
dark brown bags contain 1 dim lavender bag, 5 wavy maroon bags.
drab tan bags contain 5 striped gray bags, 2 mirrored fuchsia bags, 5 dark maroon bags.
dim violet bags contain 2 bright olive bags, 3 bright indigo bags, 2 faded bronze bags, 4 drab blue bags.
light tan bags contain 2 wavy green bags, 3 posh maroon bags, 2 striped black bags.
posh brown bags contain 1 bright cyan bag, 4 wavy gray bags.
dotted white bags contain 1 faded black bag, 2 clear green bags.
shiny tomato bags contain 3 wavy lavender bags, 2 dotted brown bags, 5 plaid plum bags, 1 striped orange bag.
dotted silver bags contain 5 dotted tan bags, 3 wavy magenta bags, 2 pale plum bags.
shiny lime bags contain 2 drab white bags, 5 posh cyan bags.
pale fuchsia bags contain 2 wavy yellow bags, 1 wavy magenta bag, 4 dull black bags, 2 dotted tan bags.
dotted crimson bags contain 5 dark blue bags.
bright salmon bags contain 3 light gold bags, 4 clear magenta bags, 4 mirrored turquoise bags.
striped brown bags contain 3 mirrored turquoise bags, 4 striped green bags.
clear crimson bags contain 4 light violet bags, 3 muted yellow bags, 2 muted salmon bags, 1 dull brown bag.
plaid silver bags contain 5 bright teal bags.
striped black bags contain 4 dark silver bags, 2 clear salmon bags, 2 dotted green bags, 5 muted gold bags.
muted lime bags contain 5 dotted indigo bags, 4 striped lime bags, 2 dark lavender bags.
bright red bags contain 2 pale turquoise bags, 2 posh purple bags.
dotted violet bags contain 4 posh indigo bags, 5 light aqua bags, 5 dark plum bags.
wavy crimson bags contain 3 dim white bags, 2 vibrant green bags, 3 mirrored green bags, 5 muted magenta bags.
bright brown bags contain 2 light tan bags, 5 dull coral bags, 1 muted tan bag, 2 striped purple bags.
muted violet bags contain 2 vibrant coral bags, 4 dotted green bags.
dim indigo bags contain 1 bright crimson bag, 3 dotted brown bags.
bright tan bags contain 1 muted chartreuse bag, 5 dull chartreuse bags, 2 muted cyan bags.
drab brown bags contain 1 drab teal bag, 1 pale lavender bag, 3 clear violet bags, 4 muted blue bags.
dark plum bags contain 5 dotted bronze bags, 1 plaid maroon bag, 4 faded gold bags.
drab indigo bags contain 1 wavy brown bag, 5 light lime bags, 3 drab blue bags.
light beige bags contain 5 dim crimson bags, 3 dull black bags, 5 muted black bags.
mirrored black bags contain 4 drab olive bags, 3 dark lavender bags.
clear beige bags contain 3 dark lavender bags, 3 muted fuchsia bags, 5 dull blue bags.
dark bronze bags contain 3 dim lavender bags, 2 vibrant magenta bags, 1 striped silver bag, 5 faded beige bags.
mirrored gold bags contain 5 pale gold bags, 5 dotted green bags, 4 posh bronze bags.
bright turquoise bags contain 1 striped indigo bag, 2 muted purple bags, 5 shiny tomato bags, 3 dotted brown bags.
dark red bags contain 2 striped purple bags, 4 clear brown bags, 2 striped cyan bags.
muted aqua bags contain 2 dark tan bags, 5 clear orange bags, 5 clear fuchsia bags.
shiny plum bags contain 2 dark tan bags.
faded plum bags contain 4 clear blue bags, 1 dim lime bag, 2 striped brown bags.
light brown bags contain 3 posh gold bags, 5 muted turquoise bags.
mirrored blue bags contain 2 muted lime bags, 3 plaid blue bags, 2 plaid magenta bags, 4 dim gold bags.
dark cyan bags contain 5 wavy olive bags, 5 plaid teal bags.
dark turquoise bags contain 5 muted lime bags, 3 muted gold bags, 3 light lime bags, 4 vibrant beige bags.
posh cyan bags contain 4 clear indigo bags, 4 faded tomato bags, 2 plaid teal bags, 3 muted red bags.
plaid purple bags contain 3 dark teal bags, 3 drab coral bags, 2 posh lavender bags.
faded fuchsia bags contain 4 muted cyan bags, 1 vibrant violet bag, 4 bright red bags.
bright silver bags contain 3 mirrored black bags.
muted tomato bags contain 2 posh olive bags.
clear salmon bags contain 5 dark aqua bags.
posh lime bags contain 2 dim crimson bags, 4 clear aqua bags.
dotted turquoise bags contain 4 dotted lime bags, 4 dotted red bags, 3 dark coral bags, 4 striped gold bags.
posh olive bags contain 3 plaid green bags.
dotted cyan bags contain 1 wavy silver bag, 2 pale red bags.
mirrored violet bags contain 4 dark salmon bags, 4 plaid magenta bags.
wavy lavender bags contain 1 clear chartreuse bag.
shiny yellow bags contain 4 dotted indigo bags.
posh lavender bags contain 2 striped chartreuse bags.
pale maroon bags contain 5 posh lavender bags, 4 plaid teal bags, 3 posh turquoise bags, 2 plaid green bags.
plaid maroon bags contain 4 pale cyan bags, 2 bright teal bags, 1 striped plum bag, 2 plaid teal bags.
faded orange bags contain 5 drab turquoise bags, 5 dull bronze bags.
clear gold bags contain 5 wavy lime bags, 4 light maroon bags, 2 muted fuchsia bags, 4 dark salmon bags.
dark green bags contain 1 striped violet bag, 1 dim crimson bag.
mirrored tomato bags contain 1 clear red bag, 4 dull indigo bags.
striped magenta bags contain 5 shiny plum bags, 4 mirrored crimson bags, 4 dull plum bags.
plaid fuchsia bags contain 1 striped indigo bag, 5 drab gray bags.
clear violet bags contain 1 wavy green bag, 4 dull lavender bags, 2 drab black bags, 5 dim salmon bags.
bright chartreuse bags contain 3 clear salmon bags, 5 dim bronze bags, 3 dark lime bags, 3 pale cyan bags.
faded purple bags contain 4 dark lavender bags, 4 pale fuchsia bags.
mirrored aqua bags contain 4 faded black bags, 5 dim gold bags, 1 mirrored gray bag.
dotted blue bags contain 2 bright salmon bags, 5 drab fuchsia bags, 2 shiny gold bags.
shiny gold bags contain 4 clear magenta bags, 3 mirrored turquoise bags, 2 plaid maroon bags, 5 bright crimson bags.
pale olive bags contain 3 striped green bags.
vibrant red bags contain 1 faded violet bag, 4 shiny yellow bags, 4 wavy coral bags.
plaid indigo bags contain 4 striped salmon bags, 4 clear turquoise bags, 2 dotted tomato bags.
posh green bags contain 1 dark brown bag, 4 dotted black bags.
shiny beige bags contain 4 dotted teal bags.
faded white bags contain 5 muted violet bags, 4 dotted blue bags, 2 dull lavender bags, 4 bright teal bags.
plaid yellow bags contain 1 mirrored yellow bag, 3 striped violet bags, 2 striped silver bags.
drab plum bags contain 4 dim indigo bags, 5 clear cyan bags.
muted purple bags contain 3 dark white bags.
plaid blue bags contain 1 striped chartreuse bag, 1 light white bag.
plaid tan bags contain 1 wavy chartreuse bag.
muted magenta bags contain 4 striped turquoise bags, 5 clear lime bags.
striped olive bags contain 3 drab black bags.
faded lime bags contain 4 pale black bags.
faded silver bags contain 2 pale magenta bags, 2 posh olive bags, 2 muted fuchsia bags.
bright orange bags contain 5 muted gray bags, 4 clear black bags.
wavy indigo bags contain 3 dull white bags, 3 pale brown bags, 3 dotted brown bags.
pale indigo bags contain 1 vibrant chartreuse bag, 5 dotted red bags.
dark olive bags contain 5 faded gold bags.
pale gray bags contain 3 striped chartreuse bags, 3 striped plum bags.
dull maroon bags contain 1 plaid yellow bag, 3 muted red bags.
mirrored turquoise bags contain no other bags.
dull lime bags contain 3 muted red bags.
posh aqua bags contain 4 pale beige bags.
mirrored lavender bags contain 1 striped maroon bag, 4 dark plum bags, 1 mirrored black bag, 2 shiny tomato bags.
muted teal bags contain 5 faded silver bags.
muted olive bags contain 4 pale tomato bags.
vibrant teal bags contain 3 posh gold bags, 1 striped orange bag, 4 vibrant aqua bags, 5 wavy tan bags.
bright black bags contain 2 striped lavender bags, 5 drab coral bags, 5 clear gray bags.
plaid brown bags contain 3 striped plum bags.
vibrant tan bags contain 2 posh maroon bags, 3 plaid yellow bags, 1 muted green bag.
dark salmon bags contain 4 plaid violet bags.
plaid violet bags contain 1 mirrored teal bag.
striped beige bags contain 5 mirrored blue bags.
vibrant maroon bags contain 5 drab olive bags, 2 plaid green bags, 3 striped plum bags, 5 mirrored coral bags.
clear indigo bags contain 1 dim fuchsia bag, 3 dull turquoise bags.
clear teal bags contain 5 muted violet bags.
bright fuchsia bags contain 3 vibrant indigo bags, 3 drab silver bags, 5 pale tan bags.
dark chartreuse bags contain 2 posh maroon bags, 1 dotted beige bag, 3 dotted silver bags.
drab white bags contain 1 vibrant coral bag, 3 striped orange bags, 4 striped green bags, 1 dim salmon bag.
clear lime bags contain 5 dotted coral bags, 2 dim bronze bags, 2 dotted bronze bags.
shiny bronze bags contain 4 muted violet bags.
vibrant coral bags contain 4 striped chartreuse bags.
drab bronze bags contain 3 posh maroon bags, 2 vibrant white bags, 5 pale lavender bags.
posh teal bags contain 5 dark tomato bags, 1 clear magenta bag, 5 posh salmon bags, 1 shiny tomato bag.
mirrored bronze bags contain 1 mirrored maroon bag.
dim orange bags contain 1 dim beige bag, 4 striped maroon bags.
muted silver bags contain 3 shiny black bags, 4 drab yellow bags.
bright lime bags contain 2 clear fuchsia bags, 2 striped maroon bags.
vibrant bronze bags contain 4 drab olive bags, 3 plaid fuchsia bags, 5 pale gold bags, 4 mirrored orange bags.
bright violet bags contain 1 bright red bag, 3 dull lime bags.
mirrored orange bags contain 1 dotted indigo bag, 3 wavy lavender bags, 2 light teal bags, 1 drab chartreuse bag.
dull tan bags contain 2 muted green bags.
dotted tomato bags contain 2 dull magenta bags, 2 plaid cyan bags.
faded violet bags contain 1 dark cyan bag, 2 light tomato bags, 2 vibrant green bags, 3 posh gray bags.
shiny cyan bags contain 2 faded red bags, 2 striped beige bags, 5 mirrored coral bags, 2 shiny tomato bags.
plaid gray bags contain 1 posh teal bag, 5 drab turquoise bags.
dotted gold bags contain 1 faded violet bag, 3 vibrant lavender bags.
faded maroon bags contain 1 posh red bag, 2 plaid violet bags, 1 vibrant magenta bag.
dim tomato bags contain 3 vibrant yellow bags.
muted red bags contain 4 striped violet bags, 3 plaid turquoise bags.
striped turquoise bags contain 1 drab coral bag, 4 plaid maroon bags, 4 drab violet bags, 5 drab silver bags.
clear bronze bags contain no other bags.
drab gold bags contain 5 clear indigo bags, 2 light purple bags, 1 faded gold bag, 1 muted gray bag.
dotted beige bags contain 1 striped bronze bag, 4 plaid maroon bags, 3 dotted green bags.
posh orange bags contain 3 mirrored fuchsia bags, 5 wavy olive bags, 5 shiny tomato bags, 4 pale orange bags.
striped orange bags contain 1 pale brown bag.
wavy olive bags contain 3 striped bronze bags.
vibrant lime bags contain 1 dotted red bag, 1 vibrant tan bag, 2 striped turquoise bags, 1 mirrored plum bag.
wavy turquoise bags contain 1 muted cyan bag.
bright bronze bags contain 5 bright blue bags, 1 pale red bag, 5 shiny blue bags.
bright green bags contain 4 dim brown bags, 5 dotted blue bags.
light orange bags contain 4 dark blue bags.
shiny coral bags contain 1 mirrored gray bag, 2 drab fuchsia bags.
mirrored magenta bags contain 2 muted gray bags, 3 shiny tomato bags, 2 drab plum bags.
mirrored purple bags contain 3 dull turquoise bags, 1 dotted coral bag, 4 plaid violet bags, 1 drab fuchsia bag.
vibrant blue bags contain 1 faded tomato bag, 3 dark blue bags.
vibrant salmon bags contain 1 mirrored teal bag, 4 dark blue bags, 1 vibrant chartreuse bag.
vibrant lavender bags contain 5 dotted beige bags, 1 striped silver bag, 2 dull tan bags.
dark gray bags contain 5 wavy maroon bags, 5 bright teal bags.
pale crimson bags contain 1 plaid beige bag, 4 posh gold bags.
shiny indigo bags contain 4 dull green bags.
shiny aqua bags contain 3 posh magenta bags.
pale chartreuse bags contain 4 muted crimson bags.
mirrored coral bags contain 5 bright salmon bags, 3 striped bronze bags.
dotted fuchsia bags contain 5 dark black bags.
vibrant magenta bags contain 3 striped black bags, 5 drab tomato bags, 3 light lime bags.
plaid lavender bags contain 1 drab salmon bag.
muted lavender bags contain 2 faded gold bags.
dim lime bags contain 2 faded beige bags.
dim white bags contain 5 shiny violet bags.
striped bronze bags contain 4 dark aqua bags, 4 clear salmon bags.
pale aqua bags contain 5 muted chartreuse bags, 2 dull brown bags.
plaid aqua bags contain 2 striped turquoise bags, 3 mirrored fuchsia bags.
mirrored crimson bags contain 3 pale brown bags, 4 mirrored teal bags, 4 wavy maroon bags.
dotted indigo bags contain 3 drab tomato bags.
dim crimson bags contain 1 striped chartreuse bag.
mirrored cyan bags contain 4 striped fuchsia bags, 5 drab gold bags, 2 mirrored tan bags, 2 clear magenta bags.
drab black bags contain 5 dark blue bags, 4 striped green bags.
striped blue bags contain 3 clear white bags, 4 vibrant yellow bags, 4 faded blue bags.
mirrored salmon bags contain 4 dotted maroon bags, 3 dotted green bags, 2 dim lavender bags.
striped red bags contain 3 bright fuchsia bags, 3 dull black bags, 4 pale indigo bags, 3 vibrant gold bags.
pale red bags contain 1 plaid plum bag, 5 dark silver bags, 3 dull lavender bags.
dim purple bags contain 2 vibrant olive bags.
dotted aqua bags contain 5 muted cyan bags.
dull red bags contain 5 dull salmon bags, 5 dotted brown bags, 4 plaid coral bags, 2 wavy green bags.
clear chartreuse bags contain 5 wavy maroon bags, 4 dotted purple bags, 3 dark gray bags, 5 pale brown bags.
shiny red bags contain 3 dim green bags.
light violet bags contain 3 dull coral bags.
muted tan bags contain 1 dull turquoise bag.
dull gold bags contain 3 drab fuchsia bags, 5 pale orange bags.
dull teal bags contain 2 posh olive bags, 3 dark yellow bags, 5 dull magenta bags, 2 dotted tan bags.
plaid crimson bags contain 4 vibrant crimson bags, 1 pale tomato bag, 1 posh silver bag.
clear tomato bags contain 4 striped silver bags, 3 wavy gray bags.
dull fuchsia bags contain 4 vibrant violet bags, 1 dim bronze bag, 2 dim cyan bags.
pale brown bags contain 1 mirrored teal bag, 2 mirrored turquoise bags, 5 striped green bags.
plaid cyan bags contain 5 pale maroon bags, 4 drab salmon bags, 3 pale magenta bags.
drab lime bags contain 4 muted tan bags, 1 faded crimson bag, 4 wavy maroon bags, 1 vibrant green bag.
bright olive bags contain 2 vibrant brown bags, 3 pale silver bags.
posh crimson bags contain 5 mirrored turquoise bags, 1 pale gray bag, 4 bright silver bags.
mirrored green bags contain 4 dotted indigo bags, 5 muted magenta bags, 2 muted blue bags, 4 drab turquoise bags.
striped tan bags contain 4 dull turquoise bags, 3 bright indigo bags, 5 pale red bags.
dim chartreuse bags contain 2 mirrored turquoise bags, 3 dotted beige bags.
posh fuchsia bags contain 1 drab salmon bag, 4 striped orange bags, 4 dark olive bags.
faded bronze bags contain 5 bright salmon bags.
dotted green bags contain 3 dark blue bags.
dim silver bags contain 2 bright beige bags, 3 pale coral bags.
muted green bags contain 3 dim brown bags, 4 clear bronze bags, 2 bright teal bags.
clear maroon bags contain 4 posh salmon bags.
vibrant chartreuse bags contain 2 dark blue bags.
posh gold bags contain 4 shiny gold bags, 5 dark teal bags.
light chartreuse bags contain 4 faded silver bags, 2 light yellow bags.
faded cyan bags contain 3 muted silver bags, 4 vibrant bronze bags, 1 dotted orange bag.
dotted salmon bags contain 2 mirrored purple bags, 2 light teal bags, 2 mirrored lavender bags.
plaid plum bags contain 3 dotted bronze bags, 5 bright salmon bags, 2 light gold bags, 4 drab teal bags.
dull salmon bags contain 4 muted green bags, 4 dim indigo bags, 1 faded tomato bag, 3 faded gold bags.
muted maroon bags contain 4 faded bronze bags, 3 dull salmon bags, 4 dull turquoise bags.
light maroon bags contain 5 plaid blue bags, 2 bright gray bags, 4 vibrant chartreuse bags, 3 dark indigo bags.
dark white bags contain 4 striped bronze bags.
posh turquoise bags contain 4 plaid gray bags, 1 dim indigo bag.
dark indigo bags contain 3 dull chartreuse bags, 2 shiny salmon bags, 1 muted magenta bag, 1 dotted indigo bag.
wavy silver bags contain 1 dotted salmon bag, 1 pale gray bag, 5 plaid turquoise bags.
pale black bags contain 5 dark olive bags.
mirrored beige bags contain 4 dim purple bags.
shiny black bags contain 2 mirrored turquoise bags, 5 pale silver bags, 2 dotted red bags, 4 dark aqua bags.
dotted lavender bags contain 4 bright beige bags, 1 clear silver bag, 3 bright orange bags.
dotted gray bags contain 3 posh red bags, 1 dotted brown bag, 4 muted red bags, 4 faded coral bags.
plaid magenta bags contain 4 vibrant blue bags, 3 dark tomato bags, 1 vibrant coral bag, 5 dim salmon bags.
muted indigo bags contain 2 drab fuchsia bags, 5 dim teal bags.
light plum bags contain 5 dark aqua bags, 4 muted purple bags, 5 wavy lavender bags, 2 drab beige bags.
dark silver bags contain no other bags.
bright gold bags contain 2 wavy maroon bags, 2 clear tan bags, 5 striped chartreuse bags.
pale coral bags contain 4 posh magenta bags, 1 posh lavender bag.
clear aqua bags contain 4 wavy maroon bags, 5 light green bags, 4 dark olive bags.
vibrant plum bags contain 4 mirrored indigo bags, 4 vibrant silver bags.
dotted yellow bags contain 1 pale teal bag.
vibrant cyan bags contain 4 dotted maroon bags, 4 dark aqua bags, 2 posh fuchsia bags.
shiny lavender bags contain 5 light gold bags.
dull beige bags contain 2 faded coral bags, 4 clear magenta bags.
bright magenta bags contain 4 faded white bags, 3 drab black bags.
drab salmon bags contain 2 bright green bags, 1 dim fuchsia bag, 1 shiny black bag.
striped plum bags contain 4 posh bronze bags, 4 dotted green bags, 2 dark aqua bags, 3 vibrant chartreuse bags.
striped gray bags contain 1 dim black bag, 2 striped tan bags, 5 light lime bags, 3 striped black bags.
dark blue bags contain 1 mirrored teal bag, 3 striped violet bags.
pale yellow bags contain 5 posh teal bags, 5 clear magenta bags, 3 plaid violet bags, 5 light green bags.
dark crimson bags contain 3 shiny tomato bags, 5 striped yellow bags, 2 shiny violet bags, 1 striped fuchsia bag.
clear purple bags contain 5 vibrant tan bags, 2 mirrored green bags, 3 faded black bags.
vibrant green bags contain 4 clear salmon bags, 1 pale turquoise bag, 2 mirrored turquoise bags.
faded teal bags contain 3 plaid beige bags.
pale turquoise bags contain 2 bright crimson bags, 4 pale cyan bags, 4 mirrored turquoise bags, 3 dotted coral bags.
striped crimson bags contain 5 pale lavender bags, 3 striped tomato bags, 1 plaid aqua bag.
muted crimson bags contain 1 dotted bronze bag, 5 striped black bags, 4 dark blue bags.
dull cyan bags contain 2 shiny coral bags, 2 plaid turquoise bags, 5 dim green bags, 2 plaid violet bags.
clear red bags contain 4 shiny maroon bags.
dim teal bags contain 3 bright red bags, 5 dim tan bags.
plaid lime bags contain 5 bright white bags, 3 light indigo bags, 1 striped black bag, 5 vibrant beige bags.
dim gold bags contain 5 dark silver bags.
dotted brown bags contain 5 striped bronze bags, 4 drab tomato bags, 2 dim lavender bags.
dim blue bags contain 2 posh gray bags, 1 dim gray bag.
muted chartreuse bags contain 5 mirrored turquoise bags, 5 dull brown bags, 4 muted brown bags, 1 striped silver bag.
dim fuchsia bags contain 5 plaid beige bags, 1 drab fuchsia bag, 5 shiny gold bags.
mirrored gray bags contain 1 pale lavender bag, 5 shiny violet bags, 2 dull lime bags.
striped gold bags contain 5 clear beige bags.
light blue bags contain 3 mirrored turquoise bags, 1 posh teal bag, 2 pale brown bags, 3 shiny maroon bags.
drab red bags contain 4 striped tan bags.
dull chartreuse bags contain 2 striped bronze bags, 2 plaid bronze bags.
pale salmon bags contain 4 bright teal bags, 3 drab silver bags, 4 dotted bronze bags, 2 mirrored silver bags.
posh white bags contain 5 dark beige bags, 5 striped turquoise bags, 5 pale purple bags, 3 pale salmon bags.
shiny olive bags contain 4 bright tomato bags, 1 light teal bag, 5 pale cyan bags.
striped lime bags contain 4 muted turquoise bags, 5 dark white bags, 4 dark fuchsia bags, 3 faded black bags.
dull purple bags contain 5 dotted indigo bags, 3 pale coral bags, 3 pale beige bags, 5 shiny green bags.
dull brown bags contain 4 dim lavender bags, 2 striped black bags, 2 pale lavender bags.
muted blue bags contain 4 drab blue bags, 1 muted red bag, 3 light white bags.
wavy cyan bags contain 1 pale purple bag, 1 muted lavender bag, 5 vibrant magenta bags, 1 plaid yellow bag.
shiny purple bags contain 2 dark bronze bags.
plaid bronze bags contain 3 wavy blue bags.
posh tan bags contain 4 clear bronze bags, 4 bright cyan bags.
dim green bags contain 4 dim beige bags.
dark black bags contain 4 dim chartreuse bags, 1 muted blue bag, 2 pale silver bags.
drab chartreuse bags contain 2 vibrant blue bags, 5 dark tomato bags, 5 dark brown bags.
vibrant aqua bags contain 1 muted white bag, 5 posh teal bags.
striped salmon bags contain 5 plaid coral bags, 1 drab black bag, 4 bright salmon bags.
plaid beige bags contain 3 dotted bronze bags.
bright gray bags contain 3 dim plum bags, 1 dull white bag, 5 plaid white bags, 3 drab brown bags.
dull white bags contain 2 dotted coral bags, 1 dotted purple bag, 2 striped brown bags.
dim bronze bags contain 5 striped green bags, 1 dotted bronze bag, 5 striped violet bags, 1 muted gray bag.
striped indigo bags contain 5 shiny maroon bags, 3 clear chartreuse bags, 3 plaid gray bags, 5 muted gold bags.
vibrant tomato bags contain 2 posh gray bags, 2 plaid beige bags, 4 wavy olive bags.
posh red bags contain 2 pale cyan bags, 4 dim salmon bags.
wavy fuchsia bags contain 2 shiny lime bags.
bright teal bags contain 2 striped black bags, 5 light gold bags, 3 mirrored teal bags, 2 bright salmon bags.
shiny teal bags contain 5 faded purple bags, 4 wavy silver bags.
bright blue bags contain 4 drab maroon bags, 1 dark fuchsia bag.
dull crimson bags contain 1 dark tomato bag, 4 striped green bags.
shiny magenta bags contain 4 shiny coral bags, 4 bright gray bags, 4 shiny purple bags, 5 plaid coral bags.
faded blue bags contain 4 striped tan bags.
vibrant yellow bags contain 3 dim crimson bags.
vibrant purple bags contain 2 dotted tan bags, 1 wavy fuchsia bag, 5 plaid lime bags.
dark orange bags contain 3 faded tomato bags, 1 mirrored salmon bag, 5 pale tomato bags.
light magenta bags contain 2 clear blue bags.
dim beige bags contain 2 mirrored red bags, 2 faded white bags, 4 plaid beige bags, 4 drab olive bags.
dotted magenta bags contain 2 vibrant lime bags.
light yellow bags contain 3 wavy chartreuse bags, 5 drab turquoise bags, 5 dull black bags.
dark aqua bags contain 1 dark tomato bag.
mirrored chartreuse bags contain 3 posh gray bags, 2 wavy bronze bags, 5 plaid teal bags.
mirrored red bags contain 3 faded black bags.
muted turquoise bags contain 5 plaid teal bags.
dim maroon bags contain 3 vibrant white bags, 1 muted turquoise bag, 5 drab red bags, 5 muted teal bags.
drab silver bags contain 5 mirrored red bags.
wavy tan bags contain 2 dark salmon bags, 2 posh bronze bags, 2 dark tan bags, 1 drab turquoise bag.
dim black bags contain 3 dark olive bags.
striped yellow bags contain 2 clear magenta bags, 3 dim indigo bags, 1 muted green bag.
wavy purple bags contain 1 vibrant lime bag, 1 clear bronze bag, 4 dark white bags.
light green bags contain 3 wavy lavender bags, 5 dim cyan bags.
striped fuchsia bags contain 3 dotted green bags, 2 plaid maroon bags.
wavy teal bags contain 4 muted olive bags, 2 muted purple bags.    
""".trimIndent()

private data class Bag(val color: String, val capacity: Map<String, Int>)