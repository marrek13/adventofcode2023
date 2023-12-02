package aockt.y2023

import io.github.jadarma.aockt.core.Solution
import kotlin.math.max

class Y2023D02 : Solution {
    private fun parseInput(input: String): List<Pair<Int, Map<String, Int>>> =
        input
            .split("\n")
            .map {
                it.split(": ")
            }
            .map { (game, rounds) ->
                game.split(" ").last().toInt() to
                        rounds.split("; ")
                            .map { round ->
                                round.split(", ")
                                    .map { it.split(" ") }
                                    .associate { it.last() to it.first().toInt() }
                            }
                            .fold(mutableMapOf<String, Int>()) { acc, map ->
                                map.forEach { (k, v) -> acc[k] = max(acc.getOrDefault(k, 0), v) }
                                acc
                            }.toMap()
            }

    override fun partOne(input: String) = parseInput(input)
        .filter { (_, balls) ->
            balls.getOrDefault("red", 0) <= 12
                    && balls.getOrDefault("green", 0) <= 13
                    && balls.getOrDefault("blue", 0) <= 14
        }.sumOf { (game, _) ->
            game
        }

    override fun partTwo(input: String) = parseInput(input)
        .sumOf { (_, balls) ->
            balls.getOrDefault("red", 0) * balls.getOrDefault("green", 0) * balls.getOrDefault("blue", 0)
        }
}
