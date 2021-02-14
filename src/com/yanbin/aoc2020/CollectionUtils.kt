package com.yanbin.aoc2020

fun <K, V> Map<K, V>.addOrReplace(key: K, value: V): Map<K, V> {
    return HashMap(this).also { it[key] = value }
}