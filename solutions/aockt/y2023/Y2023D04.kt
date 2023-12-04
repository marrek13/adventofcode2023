package aockt.y2023

import io.github.jadarma.aockt.core.Solution

class Y2023D04 : Solution {
    private fun parseInput(input: String) =
        input
            .split("\n")
            .map { line ->
                val (_, numbers) = line.split(":")
                numbers.split("|").map {
                    it.trim().split(" ").mapNotNull(String::toIntOrNull)
                }
            }


    override fun partOne(input: String) = parseInput(input)
        .map { (winningNumbers, myNumbers) ->
            myNumbers.intersect(winningNumbers.toSet()).size
        }
        .sumOf {
            if (it == 0) 0
            else {
                (2..it).fold(1) { acc, i -> acc * 2 }.toInt()
            }
        }


    override fun partTwo(input: String) = parseInput(input)
}


