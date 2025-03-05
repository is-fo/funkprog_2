package aoc2021

import java.io.BufferedReader
import java.io.File

fun main() {
    val input = readDay6()
    //max = 80 -> 349549
//    val max = 80
//    var i = 0
//    while (i++ < max) {
//        decrease(input)
//    }
    println(input.length)
    val map = initMap(StringBuilder(input))
    println(map)
    decrease(map, 256)
    println(map)
}

fun decrease(stim: HashMap<Int,Long>, days: Int): Unit {
    if (days <= 0) {
        println("Stim size: ${stim.values.sum()}")
        return
    }
    val birthers = stim.getOrDefault(0, 0)
    for (i in 0..8) {
        if (i == 6) {
            stim[6] = stim.getOrDefault(7, 0) + birthers
            continue
        }
        if (i == 8) {
            stim[8] = birthers
            continue
        }
        stim[i] = stim.getOrDefault(i + 1, 0)
    }
//    stim[0] = stim.getOrDefault(1, 0)
//    stim[1] = stim.getOrDefault(2, 0)
//    stim[2] = stim.getOrDefault(3, 0)
//    stim[3] = stim.getOrDefault(4, 0)
//    stim[4] = stim.getOrDefault(5, 0)
//    stim[5] = stim.getOrDefault(6, 0)
//    stim[6] = stim.getOrDefault(7, 0) + birthers
//    stim[7] = stim.getOrDefault(8, 0)
//    stim[8] = birthers

    return decrease(stim, days - 1,)
}

fun initMap(sb: StringBuilder) : HashMap<Int,Long> {
    val stim = HashMap<Int,Long>()
    for (c in sb) {
        stim[(c - '0')] = stim.getOrDefault(c.digitToInt(), 0) + 1
    }
    return stim
}

fun decrease(stim: StringBuilder) {
    val len = stim.length
    for (i in 0..<len) {
        stim[i] = stim[i] - 1
        if (stim[i] == '0' - 1) {
            stim[i] = '6'
            stim.append('8')
        }
    }
}

fun readDay6() : StringBuilder {
    val br: BufferedReader = File("inputs/day6.txt").bufferedReader()
    val input = StringBuilder()
    while (br.ready()) {
        input.append(br.read() - 0x30)
        br.skip(1)
    }
    return input
}