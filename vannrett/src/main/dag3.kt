import main.readInputFile
import kotlin.streams.toList
import kotlin.text.indexOf

fun main() = Dag3Del1.main()

object Dag3Del1 {
    fun main() {
        val input = testInput()
        val testCases = input.split("\n")
        // assertEquals(getHighestDigit(testCases[0]), "9")
        // assertEquals(findMaxJoltage(testCases[0]), "98")
/*
        // val vinput = readInputFile("inputdag3.txt")
        / assertEquals(solve3(input.lines()), 357)
        println("Part 1: ${solve3(vinput.lines())}") */
        assertEquals(findMaxJoltage(12, testCases[0]), "987654321111")
  /*      assertEquals(maxDigitForInput(12, testCases[0]), "9")
        assertEquals(maxDigitForInput(7, "12345678910"), "9")
        assertEquals(maxDigitForInput(4, "12345678910"), "9")*/
        // assertEquals(findMaxJoltageFinalv2(input), )
    }
    fun findMaxJoltageFinalv2(s: String) {

    }
/*
    fun maxDigitForInput(n: Int, s: String): String {
        maxDigitForInput(s.substring(0, s.length - n))
    }*/

    fun findMaxJoltage(n: Int, s: String): String {
        if (n == 0) {
            return ""
        }
        val digit = getHighestDigit(s.substring(0, s.length - n -1))
        val index = s.indexOfFirst { it.digitToInt().toString() == digit }
        return digit + findMaxJoltage(n - 1, s.substring(index + 1))
    }
/*
    fun iterativeSolution(s: String): Int {
        var remainingString = s
        var solution = ""
        while (solution.length < 12) {
            val currDigits = s.substring(12 - remainingString.length)

        }
    }*/

    fun solve3(input: List<String>): Int {
        return input.map { findMaxJoltage(it).toInt() }.sum()
    }

    fun findMaxJoltage(s: String): String {
        // s.substring()
        val firstDigit = getHighestDigit(s.substring(0, s.length - 1))
        val substring = s.substring(s.indexOfFirst {
            it.digitToInt().toString() == firstDigit
        } + 1)
        val secondDigit = getHighestDigit(substring)
        return firstDigit + secondDigit
    }

    fun getHighestDigit(s: String): String {
        return s.map { it ->
            it.digitToInt()
        }.reduce { acc, i ->
            acc.coerceAtLeast(i)
        }.toString()
    }
/*
    fun getHighestDigit2(s: String): Int {
        for (i in 9..1) {
            val res = s.substring(0, s.length - 1).indexOf(i)
            if (res != -1) {
                return res;
            }
        }
    }
*/

    fun readInputFile(filename: String): String {
        val resource = object {}.javaClass
            .getResource(filename)

        require(resource != null) { "File not found: $filename" }

        return resource.readText().trim()
    }

    fun testInput(): String = "987654321111111\n" +
            "811111111111119\n" +
            "234234234234278\n" +
            "818181911112111"


}

