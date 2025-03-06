package adventofcode2021

import java.io.BufferedReader
import java.io.File
import java.util.Stack

/**
 * [Typ lösningen](https://introcs.cs.princeton.edu/java/45graph/AllPaths.java.html)
 *
 * [Typ också lösningen](https://www.quora.com/How-do-you-find-all-paths-of-an-undirected-graph)
 */

fun main() {
    val caves = readDay12()
//    caves.values.forEach { cave -> println("${cave.name} -> ${cave.neighbors.joinToString { it.name }}") }; println()
//    crawl(caves).forEach { println(it.joinToString(" -> ")) }; println()
    println("Part 1: ${crawl(caves, true).size}")
    println("Part 2: ${crawl(caves, false).size}")
}

fun crawl(caves: Map<String, Cave>, part1: Boolean) : Stack<List<String>> {
    val allPaths = Stack<List<String>>()
    dfs(caves,
        caves["start"]!!,
        caves["end"]!!,
        Stack(),
        allPaths,
        part1
        )
    return allPaths
}

fun dfs(caves: Map<String, Cave>,
        cave: Cave,
        target: Cave,
        path: Stack<String>,
        allPaths: Stack<List<String>>,
        part1: Boolean) {

    path.push(cave.name)

    val smallCaves = path.filter { it != "start" && it != "end" && it.all { ch -> ch.isLowerCase() } }
    val visitedTwice = smallCaves
        .groupingBy { it }
        .eachCount()
        .values
        .any { it > 1 }
//    if (cave.name == target.name) {
//        println("$visitedTwice --> $path")
//    }
    if (cave != target) {
        for (neighbor in cave.neighbors) {
            if (part1 && (!path.contains(neighbor.name) || neighbor.name.all { it.isUpperCase() })) {
                dfs(caves, neighbor, target, path, allPaths, true)

            } else if (!part1 && (neighbor.name != "start" && (!path.contains(neighbor.name) || !visitedTwice || neighbor.name.all { it.isUpperCase() }))) {
                dfs(caves, neighbor, target, path, allPaths, false)
            }
        }
    } else {
        allPaths.push(path.toList())
    }
    path.pop()
}

fun readDay12(): Map<String, Cave> {
    val br: BufferedReader = File("inputs/day12.txt").bufferedReader()
    val caves = mutableMapOf<String, Cave>()

    while (br.ready()) {
        val (name1, name2) = br.readLine().split("-")

        val cave1 = caves.getOrPut(name1) { Cave(name1) }
        val cave2 = caves.getOrPut(name2) { Cave(name2) }

        cave1.neighbors.add(cave2)
        cave2.neighbors.add(cave1)
    }

    return caves
}

data class Cave(val name: String,
                val neighbors: MutableSet<Cave> = HashSet()
) {

    override fun equals(other: Any?): Boolean {
        return other is Cave && this.name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}