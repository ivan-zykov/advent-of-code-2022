fun main() {

    fun part1(input: List<String>): Int =
        buildInputMatrix(input)
            .maxOfOrNull { it.sum() } ?: 0

    fun part2(input: List<String>): Int =
        buildInputMatrix(input)
            .map { it.sum() }
            .sortedDescending()
            .take(3)
            .sum()

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 333)
    testInput.println()

    val input = readInput("Day01")
    part1(input).println() // Correct: 65912
    part2(input).println() // Correct: 195625
}

fun buildInputMatrix(input: List<String>): List<List<Int>> {
    val matrix: MutableList<List<Int>> = mutableListOf()
    val row = mutableListOf<Int>()

    input.forEach { num ->
        if (num != "") {
            row.add(num.toInt())
        } else {
            matrix.add(row.toList())
            row.clear()
        }
        // Add last row
        if (num == input.last()) {
            matrix.add(row.toList())
        }
    }

    return matrix.toList()
}
