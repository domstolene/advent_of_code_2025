package main

fun testdata(): String = "..@@.@@@@.\n" +
        "@@@.@.@.@@\n" +
        "@@@@@.@.@@\n" +
        "@.@@@@..@.\n" +
        "@@.@@@@.@@\n" +
        ".@@@@@@@.@\n" +
        ".@.@.@.@@@\n" +
        "@.@@@.@@@@\n" +
        ".@@@@@@@@.\n" +
        "@.@.@@@.@."

fun main() {
    val lines = data()

    var totalNumRolls = 0
    var previousNumRolls = 99
    var rollGrid = parseData(lines)
    while (previousNumRolls > 0) {
        previousNumRolls = 0
        // println(lines)
        for (rad in 0..rollGrid.lastIndex) {
            for (kolonne in 0..rollGrid[0].lastIndex) {
                val num = numberOfAdjacentRolls(rollGrid, rad, kolonne)
                val isOnARoll = rollGrid[kolonne][rad]
                if (isOnARoll && num < 4) {
                    previousNumRolls++
                    println("x: $kolonne, y: $rad -> $num")
                    // Oppdater matrise
                    rollGrid[kolonne][rad] = false
                }
//            println(parsedData[rad][kolonne])
            }
        }
        println(previousNumRolls)
        totalNumRolls += previousNumRolls
    }
    println(totalNumRolls)
}

private fun numberOfAdjacentRolls(coordinates: List<List<Boolean>>, i: Int, j: Int): Int {
    val surroundingCoords = listOf(listOf(-1, -1), listOf(-1, 0), listOf(-1, 1),listOf(0, -1), listOf(0, 1), listOf(1, -1), listOf(1, 0), listOf(1, 1) )
    var neighbours = 0
    for (coord in surroundingCoords) {
        val x = i + coord[0]
        val y = j + coord[1]
        try {
            if (coordinates[y][x]) {
                neighbours++
            }
        } catch (e: IndexOutOfBoundsException) {
//            println("Out of bounds: ($x, $y)")
        }
    }
    return neighbours
}

private fun data(): List<String> {
    val testlines = testdata().split("\n").filter { it.isNotEmpty() }
    return readInputFile("/inputdag4.txt").split("\n")
//    return testlines
}

private fun parseData(lines: List<String>): MutableList<MutableList<Boolean>> {
    return lines.map {
        it.map { it == '@' }.toMutableList()
        /*
        it.chars().map {
            when (it) {

            }
        }
         */
    }.toMutableList()
}
