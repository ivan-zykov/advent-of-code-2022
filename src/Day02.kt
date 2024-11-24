import Outcome.*
import Shape.*

fun main() {

    fun part1(input: List<String>): Int =
        input.map { line -> line.toShapesPartOne() }
            .map { shapes -> shapes.toOutcomeAndMyShape() }
            .sumOf { outcomeToShape -> outcomeToShape.toTotalScore() }

    fun part2(input: List<String>): Int {
        return input.map { line -> line.toShapesPartTwo() }
            .map { shapes -> shapes.toOutcomeAndMyShape() }
            .sumOf { outcomeToShape -> outcomeToShape.toTotalScore() }
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInputAsListOfLinesFrom("Day02_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 12)

    // Read the input from the `src/Day01.txt` file.
    val input = readInputAsListOfLinesFrom("Day02")
    check(part1(input) == 10404)
    check(part2(input) == 10334)
}

private fun String.toShapesPartOne(): Pair<Shape, Shape> {
    val opponentsShape = this.getOpponentsShape()
    val myShape =
        when (val myChar = this.last()) {
            'X' -> ROCK
            'Y' -> PAPER
            'Z' -> SCISSORS
            else -> throw IllegalArgumentException("Unknown my shape $myChar")
        }
    return opponentsShape to myShape
}

private fun String.getOpponentsShape() =
    when (val opponentsChar = this.first()) {
        'A' -> ROCK
        'B' -> PAPER
        'C' -> SCISSORS
        else -> throw IllegalArgumentException("Unknown opponent's shape $opponentsChar")
    }

private fun Pair<Shape, Shape>.toOutcomeAndMyShape(): Pair<Outcome, Shape> {
    val opponentsShape = this.first
    val myShape = this.second
    val outcome = when {
        opponentsShape == myShape -> DRAW
        opponentsShape == ROCK && myShape == PAPER -> WIN
        opponentsShape == ROCK && myShape == SCISSORS -> LOSE
        opponentsShape == SCISSORS && myShape == ROCK -> WIN
        opponentsShape == SCISSORS && myShape == PAPER -> LOSE
        opponentsShape == PAPER && myShape == ROCK -> LOSE
        opponentsShape == PAPER && myShape == SCISSORS -> WIN
        else -> throw IllegalArgumentException("Outcome of opponent's $opponentsShape and my $myShape is unknown")
    }
    return outcome to myShape
}

private fun Pair<Outcome, Shape>.toTotalScore(): Int =
    when (this.first) {
        WIN -> 6
        DRAW -> 3
        LOSE -> 0
    } + when (this.second) {
        ROCK -> 1
        PAPER -> 2
        SCISSORS -> 3
    }

private fun String.toShapesPartTwo(): Pair<Shape, Shape> {
    val opponentsShape = this.getOpponentsShape()
    val myChar = this.last()
    val myShape = when (opponentsShape) {
        ROCK -> when (myChar) {
            'X' -> SCISSORS
            'Y' -> ROCK
            'Z' -> PAPER
            else -> failChoosingMyShape(myChar)
        }

        PAPER -> when (myChar) {
            'X' -> ROCK
            'Y' -> PAPER
            'Z' -> SCISSORS
            else -> failChoosingMyShape(myChar)
        }

        SCISSORS -> when (myChar) {
            'X' -> PAPER
            'Y' -> SCISSORS
            'Z' -> ROCK
            else -> failChoosingMyShape(myChar)
        }
    }
    return opponentsShape to myShape
}

private fun failChoosingMyShape(char: Char): Nothing {
    throw IllegalArgumentException("Unknown my char $char")
}

private enum class Shape {
    ROCK, PAPER, SCISSORS
}

private enum class Outcome {
    WIN, LOSE, DRAW
}
