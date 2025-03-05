package adventofcode2021

import java.io.BufferedReader
import java.io.File
import kotlin.math.abs

fun main() {
    val crabs = readCrabPositions()
    println("Part 1: ${calcClosestHorizontalPos(crabs)}")
    println("Part 2: ${optimalPos(crabs)}")
}

//Part 2
fun optimalPos(crabs: ArrayList<Int>) : Long {
    var min = 0x7fffffffffffffff

    val pos = ArrayList<Int>()
    for (i in crabs.min()..crabs.max()) {
        pos.add(0)
    }
    for (i in crabs.min()..crabs.max()) {
        for (c in crabs) {
            pos[i] += fuelConsumption(abs(c - i))
        }
    }
    for (p in pos) {
        min = min.coerceAtMost(p.toLong())
    }
    return min
}

fun fuelConsumption(dist: Int): Int {
    return (dist * (dist + 1)) / 2
}

//Part 1
fun calcClosestHorizontalPos(crabs: ArrayList<Int>) : Long {
    var min = 0x7fffffffffffffff
    for (i in 0..<crabs.size) {
        var distance: Long = 0
        for (j in 0..<crabs.size) {
            distance += abs(crabs[i] - crabs[j])
        }
        min = min.coerceAtMost(distance)
    }
    return min
}

fun readCrabPositions() : ArrayList<Int> {
    val br: BufferedReader = File("inputs/day7.txt").bufferedReader()
    val input = ArrayList<Int>()
    var sum = 0
    while (br.ready()) {
        val byte = br.read()
        if (byte.toChar() == ',') {
            input.add(sum)
            sum = 0
            continue
        }
        if (byte.toChar() == '\n') {
            break
        }
        sum = (sum * 10) + (byte - 0x30)
    }
    input.add(sum)
    return input
}