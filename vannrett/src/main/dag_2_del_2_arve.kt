import main.readInputFile

fun main() = Dag2Del2.main()

object Dag2Del2 {
    fun main() {
        println(solve(readInputFile("/input_dag_2.txt")))

        assertEquals(createRepeatedNumber(10, 3), 101010)

        assertEquals(isValidId(1122), true)
        assertEquals(isValidId(11), false)
        assertEquals(isValidId(22), false)
        assertEquals(isValidId(100), true)
        assertEquals(isValidId(1188511885), false)
        assertEquals(isValidId(38593859), false)
        assertEquals(isValidId(111), false)
        assertEquals(isValidId(1212121212), false)
        assertEquals(isValidId(123123123), false)
        assertEquals(isValidId(1111111), false)

        assertEquals(invalidNumbersInRange(11L..22), listOf(11L, 22))
        assertEquals(invalidNumbersInRange(95L..115), listOf(99L, 111))
        assertEquals(invalidNumbersInRange(1188511880L..1188511890), listOf(1188511885L))

        assertEquals(solve(testInput()), 4174379265)
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

    fun <T> assertEquals(actual: T, expected: T) {
        require(actual == expected) { "Expected: '$expected', got: '$actual'" }
    }

    fun isValidId(id: Long): Boolean {
        val digits = id.countDigits()
        if (digits < 2) {
            return true
        }

        for (i in 1 .. digits / 2) {
            // går ikke opp, slik som mønster av 3 mot tall på 7 siffer
            if ((digits % i) != 0) {
                continue
            }

            val lastDigits = id.getLastDigits(i)
            val repeatedNTimes = digits / i
            val expectedInvalidNumber = createRepeatedNumber(lastDigits, repeatedNTimes)

            if (id == expectedInvalidNumber) {
                return false
            }
        }

        return true
    }

    fun createRepeatedNumber(digits: Long, repeatedNTimes: Int): Long {
        var n = repeatedNTimes
        var value = ""
        while (n != 0) {
            value += digits.toString()
            n--
        }
        return value.toLong()
    }

    fun testInput(): String = "11-22,95-115,998-1012,1188511880-1188511890,222220-222224,1698522-1698528,446443-446449,38593856-38593862,565653-565659,824824821-824824827,2121212118-2121212124"
}

fun Long.countDigits(): Int = if ((this / 10L) == 0L) {
    1
} else {
    (1 + (this / 10L).countDigits())
}

fun Long.getLastDigits(numberOfDigits: Int): Long {
    val number = this.toString()
    return number.slice(number.length - numberOfDigits until number.length).toLong()
}