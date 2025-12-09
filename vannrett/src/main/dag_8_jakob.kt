package main

import kotlin.math.pow
import kotlin.math.sqrt

fun doDist(c1: List<Double>, c2: List<Double>): Double {
    return sqrt((c1[0] - c2[0]).pow(2) + (c1[1] - c2[1]).pow(2) + (c1[2] - c2[2]).pow(2))
}

fun main() {
    val startMillis = System.currentTimeMillis()
    val parsedList = """""".split("\n").map { it.split("," ).map { it.toDouble() } }

    // E trur utfordringa her e at vi ska koble sammen i rekkefølge av dem som e nærmast hverandre.
    // Så e trur i første omgang vi bare prøv å bygg en oversikt av avstanden mellom alle. Altså en O(n**2) operasjon.
    // Deretter må vi legg dem inn i et map med set, rekkefølge av minste distanser, med logikk for sammenlåing av
    // lister. Det bli knot, men jaja.

    val toMerge = 1000 // 10
    val part2 = true

    val distances = List(parsedList.size) { MutableList(parsedList.size) { Double.MAX_VALUE } }

    for ((c1Index, c1) in parsedList.withIndex()) {
        for ((c2Index, c2) in parsedList.withIndex()) {
            if (c1Index >= c2Index) continue
            distances[c1Index][c2Index] = doDist(c1, c2)
        }
    }

//    println(distances.map { it.size })

    val shortestDistances = distances.mapIndexed { index1, list ->
        list.mapIndexed { index2, distance ->
            listOf(distance, index1.toDouble(), index2.toDouble())
        }
    }
        .flatten().sortedBy { it[0] }
        .let { if (!part2) { it.slice(0..toMerge-1) } else { it } }
        .map { listOf(it[1].toInt(), it[2].toInt()) }.toMutableList()

    shortestDistances.add(listOf(0, 2))

//    println(shortestDistances)

    // TODO: Koss bygge vi dette? Må ha en liste av sett vi slår sammen gradvis?

    val groupings = (0..parsedList.lastIndex).map { mutableSetOf(it) }.toMutableList()
//        ArrayList<MutableSet<Int>>()

    for ((i, indexes) in shortestDistances.withIndex()) {
        println("Connecting " + parsedList[indexes[0]].map {it.toInt()} + " with " +  parsedList[indexes[1]].map {it.toInt()})

        // 3 cases vi må bry oss om:
        // - Ingen finnes
        // - En finnes i en
        // - Begge finnes i ulike
        // - Begge finnes i samme (ikke gjør nå)
        val index0Grouping = groupings.indexOfFirst { it.contains(indexes[0]) }
        val index1Grouping = groupings.indexOfFirst { it.contains(indexes[1]) }

        if (index0Grouping == -1 && index1Grouping == -1) {
            // Finns ikkje
            groupings.add(mutableSetOf(indexes[0], indexes[1]))
        } else if ((index0Grouping == -1) != (index1Grouping == -1)) {
            // En av dem finns
            val groupToExpand = if (index0Grouping == -1) { index1Grouping } else { index0Grouping }
            groupings[groupToExpand].add(indexes[0])
            groupings[groupToExpand].add(indexes[1])
        } else if (index0Grouping == index1Grouping) {
            // Dem finns i samme gruppe
            groupings[index0Grouping].add(indexes[0])
            groupings[index0Grouping].add(indexes[1])
        } else {
            // Finns i forskjellige gruppa: Slå sammen gruppan
            groupings[index0Grouping].addAll(groupings.removeAt(index1Grouping))
        }

        if (part2) {
            if (groupings.size == 1) {
                println("Answer: " + parsedList[indexes[0]][0].toInt() * parsedList[indexes[1]][0].toInt())
                break
            }
        }
    }

    if (!part2) {
        val groupings2 = groupings.map { it.sorted() }

        println(groupings2)

        val groupings3 = groupings.map { it.size }.sortedByDescending { it }
        println(groupings3)
        println("Result: " + (groupings3[0] * groupings3[1] * groupings3[2]))
    }

    println("Finished in " + (System.currentTimeMillis() - startMillis) + "ms")
}
