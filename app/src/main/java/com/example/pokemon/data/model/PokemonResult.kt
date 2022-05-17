package com.example.pokemon.data.model

import android.os.Parcelable
import com.example.pokemon.util.extractId
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonResult(
    val name: String,
    val url: String
): Parcelable {
    fun getPokemonImageUrl(): String {
        val id = url.extractId()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
    }
}
