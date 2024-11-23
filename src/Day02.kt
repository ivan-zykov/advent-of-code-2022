import Outcome.*
import Shape.*

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { line -> Round(line) }
            .sumOf { round -> round.myShapeScore + round.myOutcomeScore }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInputAsListOfLinesFrom("Day02_test")
    check(part1(testInput) == 15)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputAsListOfLinesFrom("Day02")
    part1(input).println() // Correct: 10404
//    part2(input).println()
}

private class Round(
    private val input: String,
) {
    private val opponentsShape: Shape = input.toOpponentsShape()
    private val myShape: Shape = input.toMyShape()
    private val outcome: Outcome = compareShapes(opponentsShape, myShape)
    val myShapeScore: Int get() = myShape.toScore()
    val myOutcomeScore: Int get() = outcome.toScore()
}

private fun Outcome.toScore(): Int =
    when (this) {
        WIN -> 6
        DRAW -> 3
        LOSE -> 0
    }

private fun Shape.toScore(): Int =
    when (this) {
        ROCK -> 1
        PAPER -> 2
        SCISSORS -> 3
    }

private enum class Shape {
    ROCK, PAPER, SCISSORS
}

private enum class Outcome {
    WIN, LOSE, DRAW
}

private fun String.toOpponentsShape(): Shape =
    when (this.first()) {
        'A' -> ROCK
        'B' -> PAPER
        'C' -> SCISSORS
        else -> throw IllegalArgumentException("Unknown opponent's shape: $this")
    }

private fun String.toMyShape(): Shape =
    when (this.last()) {
        'X' -> ROCK
        'Y' -> PAPER
        'Z' -> SCISSORS
        else -> throw IllegalArgumentException("Unknown opponent's shape: $this")
    }

private fun compareShapes(opponent: Shape, me: Shape): Outcome =
    when {
        opponent == me -> DRAW
        opponent == ROCK && me == PAPER -> WIN
        opponent == ROCK && me == SCISSORS -> LOSE
        opponent == SCISSORS && me == ROCK -> WIN
        opponent == SCISSORS && me == PAPER -> LOSE
        opponent == PAPER && me == ROCK -> LOSE
        opponent == PAPER && me == SCISSORS -> WIN
        else -> throw IllegalArgumentException("Outcome of opponent's $opponent and my $me is unknown")
    }
