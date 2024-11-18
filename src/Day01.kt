import java.io.File

fun main() {

    fun part1Alt(input: String): Int =
        input.toMatrixAlt()
            .maxOf { it.sum() }

    fun part1(input: List<String>): Int =
        input.toMatrix()
            .maxOf { it.sum() }

    fun part2(input: List<String>): Int =
        input.toMatrix()
            .map { it.sum() }
            .sortedDescending()
            .take(3)
            .sum()

    // test if implementation meets criteria from the description, like:
    val testInputAlt: String = File("src/Day01_test.txt").readText()
    check(part1Alt(testInputAlt) == 24000)

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 24000)

    val inputAlt: String = File("src/Day01.txt").readText()
    check(part1Alt(inputAlt) == 65912) // Correct: 65912

    val input = readInput("Day01")
    check(part1(input) == 65912) // Correct: 65912
    check(part2(input) == 195625) // Correct: 195625
}

fun String.toMatrixAlt() = split("\n\n")
    .map { elf ->
        elf.lines()
            .filter { it.isNotBlank() }
            .map { itemString -> itemString.toInt() }
    }

fun List<String>.toMatrix(): List<List<Int>> {
    val matrix: MutableList<List<Int>> = mutableListOf()
    val row = mutableListOf<Int>()

    forEach { num ->
        if (num != "") {
            row.add(num.toInt())
        } else {
            matrix.add(row.toList())
            row.clear()
        }
        // Add last row
        if (num == last()) {
            matrix.add(row.toList())
        }
    }

    return matrix.toList()
}
