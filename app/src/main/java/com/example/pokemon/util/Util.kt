package com.example.pokemon.util

import android.view.View

fun String.getId() = this.split("/".toRegex()).dropLast(1).last()

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun Int.getDiv(): String{
    return this.toDouble().div(NUMBER_TEN).toString()
}