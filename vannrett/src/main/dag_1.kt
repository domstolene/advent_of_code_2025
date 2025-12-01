package main

fun main() {
    var value = 50
    var numZeroes = 0

    assert(dial(0, "R10") == 10)
    value = dial(value, "L68")
    assert(value == 82)
    value = dial(value, "L30")
    assert(value == 52)
    value = dial(value, "R48")
    assert(value == 0)
    value = dial(value, "L5")
    assert(value == 95)
    assert(dial(value, "R60") == 55)
    value = dial(value, "R60")
    assert(dial(value, "L55") == 0)
    value = dial(value, "L55")
    assert(dial(value, "L1") == 99)
    value = dial(value, "L1")
    assert(dial(value, "L99") == 0)
    value = dial(value, "L99")
    assert(dial(value, "R14") == 14)
    value = dial(value, "R14")
    assert(dial(value, "L82") == 32)

    value = 50
    readInputFile("/input_dag_1.txt")
        .lines()
        .forEach {
            value = dial(value, it)
            if (value == 0) {
                numZeroes++
            }
        }

    println(numZeroes)
}

fun readInputFile(filename: String): String {
    val resource = object {}.javaClass
        .getResource(filename)

    require(resource != null) { "File not found: $filename" }

    return resource.readText().trim()
}

fun dial(value: Int, op: String): Int {
    val leftRight = op.substring(0, 1)
    var newValue = value
    newValue += (if (leftRight == "L") { -1 } else { 1 }) * op.substring(1).toInt()
    return Math.floorMod(newValue, 100)
}