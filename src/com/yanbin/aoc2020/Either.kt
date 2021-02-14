package com.yanbin.aoc2020

sealed class Either<A, B>() {

    class Left<A, B>(val value: A): Either<A, B>()
    class Right<A, B>(val value: B): Either<A, B>()

    fun <R> fold(left: (A) -> R, right: (B) -> R): R {
        return when (this) {
            is Left -> {
                left(this.value)
            }
            is Right -> {
                right(this.value)
            }
        }
    }
    //TODO implement mapLeft, mapRight, fold
}
