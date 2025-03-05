package aoc2021

import java.io.BufferedReader
import java.io.File

fun main() {
    val coords = readInput()
    val map = drawLines(coords)
    println(map.filter { m -> m.value >= 2 }.size)
}

fun drawLines(coords: ArrayList<Coord>) : HashMap<Point, Int> {
    val map = HashMap<Point, Int>()
    for (c in coords) {
        if (c.p1.x == c.p2.x) {
            val startY = minOf(c.p1.y, c.p2.y)
            val endY = maxOf(c.p1.y, c.p2.y)
            for (i in startY..endY) {
                map[Point(c.p1.x, i)] = map.getOrDefault(Point(c.p1.x, i), 0) + 1
            }
        } else if (c.p1.y == c.p2.y) {
            val startX = minOf(c.p1.x, c.p2.x)
            val endX = maxOf(c.p1.x, c.p2.x)
            for (i in startX..endX) {
                map[Point(i, c.p1.y)] = map.getOrDefault(Point(i, c.p1.y), 0) + 1
            }
        } else {
            val max = Math.abs(c.p1.x -  c.p2.x)
            var i = 0
            var x = c.p1.x
            var y = c.p1.y
            while (i++ <= max) {
                map[Point(x, y)] = map.getOrDefault(Point(x, y), 0) + 1
                if (x < c.p2.x) x++ else x--
                if (y < c.p2.y) y++ else y--
            }
        }
    }
    return map
}

fun readInput() : ArrayList<Coord> {
    val crds = ArrayList<Coord>(16)
    val br: BufferedReader = File("inputs/day5.txt").bufferedReader()
    while (br.ready()) {
        val input = br.readLine()
        crds.add(Coord(Point(Integer.parseInt(input.substring(0, input.indexOf(','))),
                    Integer.parseInt(input.substring(input.indexOf(',') + 1, input.indexOf(' ')))),
                    Point(Integer.parseInt(input.substring(input.lastIndexOf(' ') + 1, input.lastIndexOf(','))),
                    Integer.parseInt(input.substring(input.lastIndexOf(',') + 1, input.length)))))

    }
    return crds
}

data class Coord(val p1: Point, val p2: Point)

data class Point(val x: Int, val y: Int)