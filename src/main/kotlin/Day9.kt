package aoc2021

import java.io.BufferedReader
import java.io.File

fun main() {
    val grid = readDay9()
    println(findLowpoints(grid).sum())
    println(basinFill(grid, findLowpoints2(grid)))
}

//Part 2
fun basinFill(grid: ArrayList<ArrayList<Int>>, lowpoints: List<Pos>) : Int {
    val basins = ArrayList<ArrayList<Int>>()
    for (p in lowpoints) {
        val basin = ArrayList<Int>()
        dfs(grid, p.x, p.y, basin)
        basins.add(basin)
    }
    return basins.sortedByDescending { it.size }.take(3).map { it.size }.reduce{ acc, size -> acc * size}
}

fun dfs(grid: ArrayList<ArrayList<Int>>, x: Int, y: Int, basin: ArrayList<Int>) {
    if (x !in grid.indices
        || y !in grid.indices
        || grid[x][y] == 9) {
        return
    }

    basin.add(grid[x][y])
    grid[x][y] = 9

    dfs(grid, x + 1, y, basin)
    dfs(grid, x - 1, y, basin)
    dfs(grid, x, y + 1, basin)
    dfs(grid, x, y - 1, basin)
}

fun findLowpoints2(grid: List<List<Int>>): ArrayList<Pos>  {
    val lowpoints = ArrayList<Pos>()
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            if (isLowPoint(Pos(i, j), grid)) {
                lowpoints.add(Pos(i, j))
            }
        }
    }
    return lowpoints
}

//Part 1
fun findLowpoints(grid : List<List<Int>>) : List<Int> {
    val lowpoints = ArrayList<Int>()
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            if (isLowPoint(Pos(i, j), grid)) {
                lowpoints.add(grid[i][j] + 1)
            }
        }
    }
    return lowpoints
}

fun isLowPoint(p: Pos, grid: List<List<Int>>): Boolean {
    val current = grid[p.x][p.y]

    return (p.x - 1 < 0 || current < grid[p.x - 1][p.y]) &&
            (p.x + 1 >= grid.size || current < grid[p.x + 1][p.y]) &&
            (p.y - 1 < 0 || current < grid[p.x][p.y - 1]) &&
            (p.y + 1 >= grid[p.x].size || current < grid[p.x][p.y + 1])
}

fun readDay9() : ArrayList<ArrayList<Int>> {
    val br: BufferedReader = File("inputs/day9.txt").bufferedReader()
    val input = ArrayList<ArrayList<Int>>()
    while (br.ready()) {
        val row = br.readLine()
        input.add(row.map { it.digitToInt() } as ArrayList<Int>)
    }
    return input
}

data class Pos(val x : Int, val y : Int)