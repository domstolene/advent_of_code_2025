import main.readInputFile

fun main() {
    println(solve(readInputFile("/input_dag_2.txt")))

    assertEquals(isValidId(1122), true)
    assertEquals(isValidId(11), false)
    assertEquals(isValidId(22), false)
    assertEquals(isValidId(100), true)
    assertEquals(isValidId(1188511885), false)
    assertEquals(isValidId(38593859), false)

    assertEquals(invalidNumbersInRange(11L..22), listOf(11L, 22L))
    assertEquals(invalidNumbersInRange(1188511880L..1188511890), listOf(1188511885L))

    assertEquals(solve(testInput()), 1227775554L)
}

fun solve(input: String): Long {
    return input.split(",")
        .map { toRange(it) }
        .map { invalidNumbersInRange(it) }
        .flatten()
        .sum()
}

fun toRange(range: String): LongRange =
    range.split("-")
        .map { it.toLong() }
        .let { it[0]..it[1] }

fun invalidNumbersInRange(range: LongProgression): List<Long> =
    range.filter(
        { !isValidId(it) }
    )

fun assertEquals(actual: Any, expected: Any) {
    require(actual == expected) { "Expected: '$expected', got: '$actual'" }
}

fun isValidId(id: Long): Boolean {
    val id = id.toString()

    if (id.length % 2 == 1) {
        return true
    }

    val first = id.slice(0 until id.length / 2)
    val last = id.slice(id.length / 2 until id.length)

    return first != last
}

fun testInput(): String = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"