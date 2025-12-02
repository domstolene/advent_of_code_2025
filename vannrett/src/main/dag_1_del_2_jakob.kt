package main

import kotlin.math.abs

fun main() {
    val input = readInputFile("/input_dag_1.txt")
    var zeroes = 0

    val day2 = true

    var position = 50

    for (line in input.lines()) {
        val rotation = if (line[0] == 'R') { 1 } else { -1 } * line.substring((line.length - 2).coerceAtLeast(1)).toInt()
        val oldPosition = position
        position += rotation

        if (day2) {
            // Håndter leading digits separat
            val leadingDigits = line.slice(1..line.length-3)
            if (!leadingDigits.isEmpty()) {
//                println("Zero 1 at $line")
                zeroes += leadingDigits.toInt()
            }

            // Legg til en på zeroes om den rulle over.
            if (position < 0 && oldPosition != 0) {
//                println("Zero 2 at $line")
                zeroes ++
            } else if (position > 100) {
//                println("Zero 3 at $line")
                zeroes ++
            }
        }

        position = Math.floorMod(position, 100)
        if (position == 0) {
//            println("Zero 4 at $line")
            zeroes++
        }
    }
    println(zeroes)
}

// Okei, ikkje 206 523
// Trur det telle med både når det krysse og stoppe på null.
// Nope, e glømt å del på 100 haha

// okei, working guess e 6923 (nice), men e trur det e feil også, fordi når e rulle opp på 100
// burda det ikke behandles annerledes enn når e rulle ned på 0.