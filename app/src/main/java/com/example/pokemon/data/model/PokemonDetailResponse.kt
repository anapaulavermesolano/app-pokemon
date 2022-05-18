package com.example.pokemon.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonDetailResponse(
    val sprites: Sprites,
    val stats: List<Stats>,
    val height: Int,
    val weight: Int
) : Parcelable

@Parcelize
data class Sprites(
    val backDefault: String,
    val backShiny: String,
    val frontDefault: String,
    val frontShiny: String
) : Parcelable

@Parcelize
data class Stats(
    val baseStat: Int,
    val effort: Int,
    val stat: Stat
) : Parcelable

@Parcelize
data class Stat(
    val name: String,
    val url: String
) : Parcelable
