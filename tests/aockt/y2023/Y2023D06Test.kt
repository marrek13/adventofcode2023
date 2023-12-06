package aockt.y2023

import io.github.jadarma.aockt.test.AdventDay
import io.github.jadarma.aockt.test.AdventSpec


@AdventDay(2023, 6, "Wait For It")
class Y2023D06Test : AdventSpec<Y2023D06>({
    val exampleInput = """
            Time:      7  15   30
            Distance:  9  40  200
        """

    partOne {
        exampleInput.trimIndent() shouldOutput 288
    }

    partTwo(enabled = true) {
        exampleInput.trimIndent() shouldOutput 71503
    }
})
