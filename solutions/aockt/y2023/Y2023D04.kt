package aockt.y2023

import io.github.jadarma.aockt.core.Solution

class Y2023D04 : Solution {
    val calculatedScores = mutableMapOf<Int, Int>()

    private fun parseInput(input: String) =
        input
            .split("\n")
            .associate { line ->
                val (card, numbers) = line.split(":")
                card.split(" ").last().toInt() to
                        numbers.split("|")
                            .map { it.trim().split(" ").mapNotNull(String::toIntOrNull) }
                            .let { (winningNumbers, myNumbers) -> winningNumbers to myNumbers }
            }


    override fun partOne(input: String) = parseInput(input)
        .values
        .map { (winningNumbers, myNumbers) ->
            myNumbers.intersect(winningNumbers.toSet()).size
        }
        .sumOf {
            if (it == 0) 0
            else {
                (2..it).fold(1) { acc, _ -> acc * 2 }.toInt()
            }
        }


    override fun partTwo(input: String) = parseInput(input).let { rows ->
        val rowWins: Map<Int, Int> = rows
            .mapNotNull { row ->
                val (winningNumbers, myNumbers) = row.value
                val matches = myNumbers.intersect(winningNumbers.toSet()).size

                row.key to matches
            }
            .toMap()

        calculatedScores.putAll(rowWins.mapValues { 0 })

        rowWins.keys.forEach { row ->
            calculateScore(row, rowWins)
        }

        calculatedScores.values.sum()
    }

    private fun calculateScore(row: Int, rows: Map<Int, Int>) {
        calculatedScores[row] = calculatedScores[row]!! + 1

        val wins = rows[row]!!
        if (wins == 0) return

        ((row + 1)..<(row + 1 + wins)).forEach { nextRow ->
            calculateScore(nextRow, rows)
        }
    }
}


