package aockt.y2023

import io.github.jadarma.aockt.core.Solution

class Y2023D03 : Solution {
    private fun parseInput(input: String): Pair<List<Point.Number>, List<Point.Symbol>> =
        input
            .split("\n")
            .mapIndexed { rowNumber, row ->
                val numbers =
                    Regex("[0-9]+").findAll(row).map { Point.Number(it.value, rowNumber, it.range.first) }.toList()
                val symbols =
                    Regex("[^0-9.]+").findAll(row).map { Point.Symbol(it.value, rowNumber, it.range.first) }.toList()

                numbers to symbols
            }
            .reduce { (numbers1, symbols1), (numbers2, symbols2) ->
                numbers1 + numbers2 to symbols1 + symbols2
            }

    override fun partOne(input: String) = parseInput(input)
        .let { (numbers, symbols) ->
            val symbolsGrouped = symbols.groupBy { it.row }

            numbers.filter { number ->
                (
                        symbolsGrouped.getOrDefault(number.row, emptyList())
                                + symbolsGrouped.getOrDefault(number.row + 1, emptyList())
                                + symbolsGrouped.getOrDefault(number.row - 1, emptyList()))
                    .any { symbol ->
                        number.isAdjacentTo(symbol)
                    }
            }.sumOf { it.value.toInt() }
        }

    override fun partTwo(input: String) = parseInput(input)
        .let { (numbers, symbols) ->
            val numbersGrouped = numbers.groupBy { it.row }

            symbols
                .filter { it.value == "*" }
                .sumOf { symbol ->
                    (numbersGrouped.getOrDefault(symbol.row, emptyList())
                            + numbersGrouped.getOrDefault(symbol.row + 1, emptyList())
                            + numbersGrouped.getOrDefault(symbol.row - 1, emptyList()))
                        .filter { number -> symbol.isAdjacentTo(number) }
                        .takeIf { it.size == 2 }
                        ?.map { it.value.toInt() }
                        ?.let { it.reduce { acc, number -> acc * number } } ?: 0
                }
        }

    sealed class Point {
        abstract val row: Int
        abstract val position: Int
        abstract val value: String

        data class Number(override val value: String, override val row: Int, override val position: Int) : Point() {
            fun isAdjacentTo(symbol: Symbol) =
                symbol.row in (row - 1)..(row + 1) && symbol.position in (position - 1)..(position + value.length)
        }

        data class Symbol(override val value: String, override val row: Int, override val position: Int) : Point() {
            fun isAdjacentTo(number: Number) =
                number.row in (row - 1)..(row + 1) && position in (number.position - 1)..(number.position + number.value.length)
        }
    }
}


