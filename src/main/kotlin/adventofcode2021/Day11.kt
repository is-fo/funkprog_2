package adventofcode2021

import java.io.BufferedReader
import java.io.File

fun main() {
    val octigrid = readDay11()
    println("Part 1: ${step(octigrid, 100)}")
}
//Part 1 & 2
fun step(octigrid: List<List<Octopus>>, steps: Int) : Int {
    var flashes = 0
    var i = 0
    while (true) {
        increaseAll(octigrid)
        FLASH(octigrid)
        val flashed = resetFlashers(octigrid)
        if (++i <= steps) {
            flashes += flashed
        }
        if (flashed == 100) {
            println("Part 2: $i")
            break
        }
//        octigrid.forEach { row -> row.forEach { print(it.energy) }; println() }
//        println()
    }
    return flashes
}

fun increaseAll(octigrid: List<List<Octopus>>) {
    for (octi in octigrid) {
        for (octo in octi) {
            octo.energy++
        }
    }
}

fun resetFlashers(octigrid: List<List<Octopus>>) : Int {
    var flashes = 0
    for (octi in octigrid) {
        for (octo in octi) {
            if (octo.flashed) {
                octo.energy = 0
                flashes++
            }
            octo.flashed = false
        }
    }
    return flashes
}

fun FLASH(octigrid: List<List<Octopus>>) {
    octigrid.forEach { row -> row.forEach { if (!it.flashed && it.energy > 9) increaseAdjacent(it, octigrid) } }
}

fun increaseAdjacent(octopus: Octopus, octigrid: List<List<Octopus>>) {
    octopus.flashed = true

    val directions = listOf(
        Pair(-1, 0),
        1 to 0,
        0 to -1,
        0 to 1,
        -1 to -1,
        1 to -1,
        -1 to 1,
        1 to 1
    )

    for ((dx, dy) in directions) {
        val nx = octopus.x + dx
        val ny = octopus.y + dy

        if (ny in octigrid.indices && nx in octigrid[ny].indices) {
            val neighbor = octigrid[ny][nx]

            if (!neighbor.flashed && ++neighbor.energy > 9) {
                increaseAdjacent(neighbor, octigrid)
            }
        }
    }
}

fun readDay11() : List<List<Octopus>> {
    val br: BufferedReader = File("inputs/day11.txt").bufferedReader()
    val input = ArrayList<List<Octopus>>()
    var y = 0
    while (br.ready()) {
        var x = 0
        val line = br.readLine()
        input.add(line.map { Octopus(it.digitToInt(), false, x++, y) } as ArrayList<Octopus>)
        y++
    }
    return input
}

data class Octopus(var energy: Int, var flashed: Boolean, val x: Int, val y: Int)