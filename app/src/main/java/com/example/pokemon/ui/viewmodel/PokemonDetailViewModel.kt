package com.example.pokemon.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.pokemon.repository.PokemonRepository
import com.example.pokemon.util.ResultType
import com.example.pokemon.util.getId
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val pokemonRepository: PokemonRepository) : ViewModel() {

    suspend fun getPokemonDetail(url: String) = flow {
        emit(ResultType.Loading)
        emit(pokemonRepository.getPokemonDetail(url.getId().toInt()))
    }

}

