package aoc2021

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
//    crawl(caves).forEach { println(it.joinToString(" -> ")) }
    println("Part 1: ${crawl(caves).size}")
}

fun crawl(caves: Map<String, Cave>) : Stack<List<String>> {
    val allPaths = Stack<List<String>>()
    dfs(caves,
        caves["start"]!!,
        caves["end"]!!,
        ArrayList(),
        allPaths
        )
    return allPaths
}

fun dfs(caves: Map<String,
        Cave>, cave: Cave,
        target: Cave,
        path: ArrayList<String>,
        allPaths: Stack<List<String>>) {

    path.add(cave.name)
    if (cave != target) {
        for (neighbor in cave.neighbors) {
            if (!path.contains(neighbor.name) || neighbor.isBig) {
                dfs(caves, neighbor, target, path, allPaths)
            }
        }
    } else {
        allPaths.push(path.toList())
    }
    path.removeLast()
}

fun readDay12(): Map<String, Cave> {
    val br: BufferedReader = File("inputs/day12.txt").bufferedReader()
    val caves = mutableMapOf<String, Cave>()

    while (br.ready()) {
        val (name1, name2) = br.readLine().split("-")

        val cave1 = caves.getOrPut(name1) { Cave(name1, name1.first().isUpperCase()) }
        val cave2 = caves.getOrPut(name2) { Cave(name2, name2.first().isUpperCase()) }

        cave1.neighbors.add(cave2)
        cave2.neighbors.add(cave1)
    }

    return caves
}

data class Cave(val name: String,
                var isBig: Boolean,
                val neighbors: MutableSet<Cave> = HashSet()
) {

    override fun equals(other: Any?): Boolean {
        return other is Cave && this.name == other.name
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }
}