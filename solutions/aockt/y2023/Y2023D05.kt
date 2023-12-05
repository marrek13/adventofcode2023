package aockt.y2023

import io.github.jadarma.aockt.core.Solution
import kotlin.math.max
import kotlin.math.min

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
            .also {
                maps.clear()
                seeds.clear()
            }
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
        seeds.minOf { seed ->
            maps.fold(seed) { acc, map ->
                map.firstOrNull { it.isDesinationFor(acc) }?.getDestinationFor(acc) ?: acc
            }
        }
    }


    override fun partTwo(input: String) = parseInput(input).let { (seeds, maps) ->
        seeds
            .asSequence()
            .chunked(2)
            .map { (start, range) -> start to range }
            .map { seedRange ->
                maps.fold(listOf(seedRange)) { ranges, mappings ->
                    val rangesToMap = ranges.toMutableList()
                    val mappedRanges = mutableListOf<Pair<Long, Long>>()

                    while(rangesToMap.isNotEmpty()) {
                        val range = rangesToMap.removeFirst()
                        val start = range.first
                        val end = start + range.second

                        val mapping = mappings.find {
                            (_, mapStart, mapRange) ->
                            val mapEnd = mapStart + mapRange
                            start < mapEnd && end > mapStart
                        }

                        if(mapping == null) {
                            mappedRanges += range
                            continue
                        }

                        val (dest, mapStart, mapRange) = mapping
                        val mapEnd = mapStart + mapRange
                        mappedRanges += (dest + (max(start, mapStart) - mapStart)) to (min(end, mapEnd) - max(start, mapStart))

                        if(start < mapStart) {
                            rangesToMap += start to (mapStart - start)
                        }

                        if(end > mapEnd) {
                            rangesToMap += mapEnd to (end - mapEnd)
                        }
                    }

                    mappedRanges
                }
            }
            .flatten()
            .minOf { it.first }

    }
}
