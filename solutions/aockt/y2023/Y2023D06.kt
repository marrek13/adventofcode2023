package aockt.y2023

import io.github.jadarma.aockt.core.Solution

class Y2023D06 : Solution {
    data class Race(val time: Long, val distance: Long) {
        fun getNonZeroRange() = 1..<time

        fun calculateDistance(pressTime: Long) = (time - pressTime) * pressTime

        fun findRecordTimes(): Int {
            val timeRange = getNonZeroRange()
            val middleOfRange = (timeRange.first + timeRange.last) / 2 + 1

            var recordTimesCounter = 1

            ((middleOfRange - 1) downTo timeRange.first).forEach {
                val x = calculateDistance(it)
                if (x <= distance) return@forEach
                recordTimesCounter++
            }

            ((middleOfRange + 1)..<timeRange.last).forEach {
                val x = calculateDistance(it)
                if (x <= distance) return@forEach
                recordTimesCounter++
            }

            return recordTimesCounter
        }
    }

    private fun parseInput(input: String) =
        input
            .split("\n")

    override fun partOne(input: String) = parseInput(input)
        .let { lines ->
            val times = Regex("[0-9]+")
                .findAll(lines[0])
                .map { it.value.toLong() }
                .toList()

            val distances = Regex("[0-9]+")
                .findAll(lines[1])
                .map { it.value.toLong() }
                .toList()

            times.zip(distances).map {
                Race(it.first, it.second)
            }
        }
        .map(Race::findRecordTimes)
        .reduce(Int::times)

    override fun partTwo(input: String) = parseInput(input)
        .let { lines ->
            lines
                .map { it.replace(" ", "").split(":").last().toLong() }
                .let { (time, distance) ->
                    Race(time, distance)
                }
        }
        .let(Race::findRecordTimes)
}
