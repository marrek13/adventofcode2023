package aockt.y2023

import io.github.jadarma.aockt.core.Solution

class Y2023D07 : Solution {
    enum class TYPE(val value: Int, val check: (List<Char>) -> Boolean) {
        FIVE_OF_A_KIND(6, { it.all { c -> c == it[0] } }),
        FOUR_OF_A_KIND(5, { hand -> hand.groupBy { c -> c }.values.any { it.size == 4 } }),
        FULL_HOUSE(4, { hand ->
            val groupedCard: Map<Char, List<Char>> = hand.groupBy { c -> c }
            val triple = groupedCard.values.firstOrNull { it.size == 3 }?.first()
            triple != null && groupedCard.any { it.value.size == 2 && it.key != triple }.also { println(it) }
        }),
        THREE_OF_A_KIND(3, { hand -> hand.groupBy { c -> c }.values.any { it.size == 3 } }),
        TWO_PAIR(2, { hand -> hand.groupBy { c -> c }.values.count { it.size == 2 } == 2 }),
        PAIR(1, { hand -> hand.groupBy { c -> c }.values.any { it.size == 2 } }),
        HIGH_CARD(0, { true }),
    }

    data class Game(val hand: String, val bid: Int): Comparable<Game> {
        private val type: TYPE = TYPE.entries.first { it.check(hand.toCharArray().toList()) }

        companion object {
            private val cards = listOf('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A').reversed()
        }

        override fun compareTo(other: Game): Int {
            val typeCompare = type.compareTo(other.type)
            if (typeCompare != 0) return typeCompare

            val hand1 = hand.toCharArray().toList()
            val hand2 = other.hand.toCharArray().toList()

            for (i in hand1.indices) {
                val card1 = hand1[i]
                val card2 = hand2[i]
                if (card1 != card2) return cards.indexOf(card1) - cards.indexOf(card2)
            }

            return 0
        }
    }

    private fun parseInput(input: String) =
        input
            .split("\n")
            .map {
                val (hand, bid) = it.split(" ")
                Game(hand, bid.toInt())
            }

    override fun partOne(input: String) = parseInput(input)
        .sortedDescending()
        .foldIndexed(0) { index, acc, game -> acc + game.bid * (index + 1) }
        .also {
            println(it)
        }


    override fun partTwo(input: String) = parseInput(input)
}
