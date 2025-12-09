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


    // println(lines)
    val parsedData = parseData(lines)
    for (rad in 0..parsedData.lastIndex) {
        for (j in 0..parsedData[0].lastIndex) {
            val num = numberOfAdjacentRolls(parsedData, rad, j)
            println(parsedData[rad][j])
        }
    }
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
            println("Out of bounds: ($x, $y)")
        }
    }
    return neighbours
}

private fun data(): List<String> {
    val testlines = testdata().split("\n").filter { it.isNotEmpty() }
    readInputFile("/input_dag_4.txt").split("\n")
    return testlines
}


private fun parseData(lines: List<String>): List<List<Boolean>> {
    return lines.map {
        it.map { it == '@' }
        /*
        it.chars().map {
            when (it) {

            }
        }
         */
    }
}


private fun readInputFile(filename: String): String {
    val resource = object {}.javaClass
        .getResource(filename)

    require(resource != null) { "File not found: $filename" }

    return resource.readText().trim()
}
