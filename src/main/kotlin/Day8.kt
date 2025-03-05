package aoc2021

import java.io.BufferedReader
import java.io.File

fun main() {
    val input = readDisplays()
    println("Part 1: ${count1478(occurrences(input))}")
    println("Part: 2: ${decipher(input).sum()}");
}

//Part 2
fun decipher(input: ArrayList<String>) : ArrayList<Int> {
    val result = ArrayList<Int>(20)
    for (str in input) {
        val map = HashMap<Int, String>()
        val arr = str.split(" | ")
        val signal = arr[0].split(" ")

        mapDigits(map, signal)

        decode(arr, map, result)
    }
    return result
}

/**
 *  https://imgur.com/a/LIS2zZr
 */
private fun mapDigits(
    map: HashMap<Int, String>,
    signal: List<String>
) {
    while (map.size < 10) {
        for (s in signal) {
            try {
                when (s.length) {
                    2 -> map[1] = s
                    3 -> map[7] = s
                    4 -> map[4] = s
                    7 -> map[8] = s
                    5 -> {
                        if (map[7]!!.toSet().all { it in s }) {
                            map[3] = s
                        } else {
                            if (map[4]!!.toSet().intersect(s.toSet()).size == 3) {
                                map[5] = s
                            } else {
                                map[2] = s
                            }
                        }
                    }

                    6 -> {
                        if (map[4]!!.toSet().all { it in s }) {
                            map[9] = s
                        } else {
                            if (map[7]!!.toSet().all { it in s }) {
                                map[0] = s
                            } else {
                                map[6] = s
                            }
                        }
                    }
                }
            } catch (npe: NullPointerException) {
                continue
            }
        }
    }
}

private fun decode(
    arr: List<String>,
    map: HashMap<Int, String>,
    result: ArrayList<Int>
) {
    val strSum = StringBuilder("")
    val digits = arr[1].split(" ")
    for (d in digits) {
        when (d.toSet()) {
            map[0]?.toSet() -> strSum.append('0')
            map[1]?.toSet() -> strSum.append('1')
            map[2]?.toSet() -> strSum.append('2')
            map[3]?.toSet() -> strSum.append('3')
            map[4]?.toSet() -> strSum.append('4')
            map[5]?.toSet() -> strSum.append('5')
            map[6]?.toSet() -> strSum.append('6')
            map[7]?.toSet() -> strSum.append('7')
            map[8]?.toSet() -> strSum.append('8')
            map[9]?.toSet() -> strSum.append('9')
            else -> println("MISS!!!!! MISS!!!!!! for $d")
        }
    }
    result.add(Integer.parseInt(strSum.toString()))
}

//Part 1
fun occurrences(input: ArrayList<String>): HashMap<Int, Int> {
    val oMap = HashMap<Int, Int>()
    for (i in input.indices) {
        val line = input[i].split(" | ")[1]
        var len = 0
        for (c in line.indices) {
            if (line[c] == ' ') {
                oMap[len] = oMap.getOrDefault(len, 0) + 1
                len = 0
            } else {
                len++
            }
        }
        if (len > 0) {
            oMap[len] = oMap.getOrDefault(len, 0) + 1
        }
    }

    println(oMap)
    return oMap
}

fun count1478(occurences: HashMap<Int, Int>) : Int {
    var sum = 0
    for (v in occurences) {
        if (v.key == 2) {
            sum += v.value
        } else if (v.key == 4) {
            sum += v.value
        } else if (v.key == 3) {
            sum += v.value
        } else if (v.key == 7) {
            sum += v.value
        }
    }
    return sum
}

fun readDisplays(): ArrayList<String> {
    val br: BufferedReader = File("inputs/day8.txt").bufferedReader()
    val input = ArrayList<String>()
    while (br.ready()) {
        input.add(br.readLine())
    }
    return input
}