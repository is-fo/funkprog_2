package aoc2021

import java.io.BufferedReader
import java.io.File
import java.util.Stack

/**
 * [Liknande Leetcode problem](https://leetcode.com/problems/valid-parentheses/description/)
 */

fun main() {
    val input = readDay10()
    val points = hashMapOf(Pair(')', 3), Pair(']', 57), Pair('}', 1197), Pair('>', 25137))
    //Part 1
    println(findCorrupt(input).sumOf { points[it] ?: 0 })
    //Part 2
    println(median(complete(input)))
}
//Part2
fun median(scores: List<Long>) : Long {
    return scores.sorted()[scores.size / 2] //scores storlek alltid udda
}

fun complete(rows: List<String>) : List<Long> {
    val points = hashMapOf(
        '(' to 1,
        '[' to 2,
        '{' to 3,
        '<' to 4
    )
    val scores = ArrayList<Long>()
    for (str in rows) {
        val stack = Stack<Char>()
        var score : Long = 0
        var corrupt = false
        for (c in str) {
            when (c) {
                '(', '[', '{', '<' -> stack.push(c)
                ')' -> if (stack.peek() == '(') stack.removeLast() else { corrupt = true; break }
                ']' -> if (stack.peek() == '[') stack.removeLast() else { corrupt = true; break }
                '}' -> if (stack.peek() == '{') stack.removeLast() else { corrupt = true; break }
                '>' -> if (stack.peek() == '<') stack.removeLast() else { corrupt = true; break }
            }
        }
        if (!corrupt) {
            for (i in stack.reversed()) {
                score = (score * 5) + (points[i] ?: 0)
            }
            scores.add(score)
        }
    }
    return scores
}

//Part 1
fun findCorrupt(rows: List<String>) : ArrayList<Char> {
    val result = ArrayList<Char>()

    for (str in rows) {
        val stack = ArrayDeque<Char>()
        for (c in str) {
            if (c == '(' || c == '[' || c == '{' || c == '<') {
                stack.add(c)
            } else if (c == ')') {
                if (stack[stack.lastIndex] == '(') {
                    stack.removeLast()
                } else {
                    result.add(c)
                    break
                }
            } else if (c == ']') {
                if (stack[stack.lastIndex] == '[') {
                    stack.removeLast()
                } else {
                    result.add(c)
                    break
                }
            } else if (c == '}') {
                if (stack[stack.lastIndex] == '{') {
                    stack.removeLast()
                } else {
                    result.add(c)
                    break
                }
            } else if (c == '>') {
                if (stack[stack.lastIndex] == '<') {
                    stack.removeLast()
                } else {
                    result.add(c)
                    break
                }
            }
        }
    }
    return result
}

fun readDay10() : ArrayList<String> {
    val br: BufferedReader = File("inputs/day10.txt").bufferedReader()
    val input = ArrayList<String>()
    while (br.ready()) {
        input.add(br.readLine())
    }
    return input
}