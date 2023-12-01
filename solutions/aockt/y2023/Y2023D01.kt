package aockt.y2023

import io.github.jadarma.aockt.core.Solution

class Y2023D01 : Solution {
    private fun parseInput(input: String): List<String> =
        input
            .split("\n")

    override fun partOne(input: String) = parseInput(input)
        .map { line ->
            line.mapNotNull { it.digitToIntOrNull() }
        }
        .sumOf {
            "${it.first()}${it.last()}".toInt()
        }

    override fun partTwo(input: String) = parseInput(input)
        .map { line ->
            line
                .windowed(5, 1, true) {
                    when {
                        it.first().isDigit() -> it.first().toString()
                        it.startsWith("one") -> "1"
                        it.startsWith("two") -> "2"
                        it.startsWith("three") -> "3"
                        it.startsWith("four") -> "4"
                        it.startsWith("five") -> "5"
                        it.startsWith("six") -> "6"
                        it.startsWith("seven") -> "7"
                        it.startsWith("eight") -> "8"
                        it.startsWith("nine") -> "9"
                        else -> ""
                    }
                }.joinToString("")
        }
        .filter { it.isNotEmpty() }
        .sumOf {
            "${it.first()}${it.last()}".toInt()
        }

}
