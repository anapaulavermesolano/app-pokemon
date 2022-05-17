package com.example.pokemon.util

import android.view.View

fun String.extractId() = this.split("/".toRegex()).dropLast(1).last()

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}