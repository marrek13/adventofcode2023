package aockt.y2023

import io.github.jadarma.aockt.core.Solution

class Y2023D05 : Solution {
    data class Range(val destinationRangeStart: Long, val sourceRangeStart: Long, val rangeLength: Long) {
        fun isDesinationFor(source: Long): Boolean {
            return source in sourceRangeStart..<(sourceRangeStart + rangeLength)
        }

        fun getDestinationFor(source: Long): Long {
            return destinationRangeStart + (source - sourceRangeStart)
        }
    }

    private val seeds = mutableListOf<Long>()
    private val maps = mutableListOf<MutableList<Range>>()

    private fun parseInput(input: String) =
        input
            .split("\n")
            .let { lines ->
                Regex("[0-9]+")
                    .findAll(lines.first())
                    .map { it.value.toLong() }
                    .let { seeds.addAll(it) }

                lines.subList(1, lines.size)
            }
            .filter { it.isNotBlank() }
            .forEach { line ->
                if (!line.first().isDigit()) {
                    maps.add(mutableListOf())
                } else {
                    Regex("[0-9]+")
                        .findAll(line)
                        .map { it.value.toLong() }
                        .toList()
                        .let { maps.last().add(Range(it[0], it[1], it[2])) }
                }
            }
            .let { seeds to maps }


    override fun partOne(input: String) = parseInput(input).let { (seeds, maps) ->
        seeds.map { seed ->
            maps.fold(listOf(seed)) { acc, map ->
                map
                    .firstOrNull { it.isDesinationFor(acc.last()) }
                    ?.let { acc + listOf(it.getDestinationFor(acc.last())) }
                    ?: (acc + acc.last())
            }
        }
            .minOf { it.last() }
    }


    override fun partTwo(input: String) = parseInput(input)
}
