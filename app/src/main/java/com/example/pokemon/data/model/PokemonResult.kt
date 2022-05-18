package com.example.pokemon.data.model

import android.os.Parcelable
import com.example.pokemon.util.getId
import kotlinx.parcelize.Parcelize

@Parcelize
data class PokemonResult(
    val name: String,
    val url: String
): Parcelable {
    fun getPokemonImageUrl(): String {
        val id = url.getId()
        return "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png"
    }
}
