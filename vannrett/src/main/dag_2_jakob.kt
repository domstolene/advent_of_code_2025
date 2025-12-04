package main

import kotlin.math.ceil

fun main() {
    val startMillis = System.currentTimeMillis()
    var parsedList = ""
        .split(",").map {
            arrayOf(it.split("-")[0], it.split("-")[1])
        }


    // Ugyldig e at det e noko som repetere seg, altså førte halvdel lik andre halvdel
    // Ugyldig e leading zeroes.

    // Ingen tall kjem te å ha leading zeroes uansett. Det betyr basicly at vi slepp å tenk på leading zeroes.
    // Altså må vi bare sjå på om det finnes tall i rangen som kopiere starten av tallet, for ranges som e oddetall.

    var invalidSum = 0.toBigInteger()

    fun getPrimeFactors(number: Int): MutableList<Int> {
        val primeFactors = mutableListOf<Int>()
        for (i in 1..number / 2) {
            if (number % i == 0) {
                primeFactors.add(i)
            }
        }
        return primeFactors
    }

    val primeFactors = (1..20).map { getPrimeFactors(it).toList() }.toList()

    // Fakk, det kjem te å vær duplikata med denne måten å gjør det på.
    // Fordi 1111 funke både som 11 + 11 og som 1 + 1 + 1 + 1
    // Kan man detekter om dette e noko som kan underdeles ytterligar?

    // Finnes det eksempel som ikke e additive?
    // 232 + 232 går ikkje med 23 23 23, det e forskjellige tall.

    val dag2 = true
    if (dag2) {
        // Trur det kan lønn seg å del in ranges videre så alle ranges e samme siffer
        parsedList = parsedList.flatMap {
            if (it[0].length == it[1].length) {
                listOf(it)
            } else {
                listOf(
                    arrayOf(it[0], "9".repeat(it[0].length)),
                    arrayOf("1" + "0".repeat(it[1].length - 1), it[1])
                )
            }
        }

        for (range in parsedList) {
            println("Ser på: " + range[0] + ":" + range[1])
            for (primeFactor in primeFactors[range[0].length]) {
                outerLoop@ for (leadingDigits in range[0].substring(0, primeFactor).toInt()..range[1].substring(0, primeFactor).toInt()) {
                    val candidate = leadingDigits.toString()
                    println("Kandidat: " + candidate)

                    // Stryk kandidaten om den igjen e repetert
                    for (subPrimeFactor in primeFactors[candidate.length]) {
                        if (candidate.substring(0, subPrimeFactor).repeat(candidate.length / subPrimeFactor) == candidate) {
                            println("Skipping candidate: $candidate")
                            continue@outerLoop
                        }
                    }

                    val fullCandidate = candidate.repeat(range[0].length / primeFactor)
                    if (range[0] <= fullCandidate && fullCandidate <= range[1]) {
                        println("    YAY for: $fullCandidate")
                        invalidSum += fullCandidate.toBigInteger()
                    }
                }
            }
        }
    } else {
        for (range in parsedList) {
            // Begynn med å skip ranges som bare har oddetall input
            if (range[0].length % 2 == 1 && range[0].length == range[1].length) {
                println("Skipping")
                continue
            }

            println("\nRanges: " + range[0] + " : " + range[1])
            //        println(range[0].length.toString() + ":" + range[1].length.toString())

            val startLeadingRange = if (range[0].length > 1) {
                range[0].substring(0, range[0].length.floorDiv(2)).toInt()
            } else {
                1
            }
            val endLeadingRange = range[1].substring(0, ceil(range[1].length / 2F).toInt()).toInt()

            println("LeadingRange: $startLeadingRange : $endLeadingRange")

            for (leadingDigitRange in startLeadingRange..endLeadingRange) {
                if (leadingDigitRange.toString().length * 2 < range[0].length || range[1].length < leadingDigitRange.toString().length * 2) {
                    continue
                }

                //            println("Trying: " + leadingDigitRange)

                val repeatedNumber = leadingDigitRange.toString().repeat(2).toBigInteger()
                if (range[0].toBigInteger() <= repeatedNumber && repeatedNumber <= range[1].toBigInteger()) {
                    //                println("Adding: $leadingDigitRange")
                    invalidSum += repeatedNumber
                }
            }

            // Tell oppover med leading halve digits, og sjekk om resultatet finnes i rangen.
        }
    }

    println(invalidSum)
    println("Finished in " + (System.currentTimeMillis() - startMillis) + "ms")
}

