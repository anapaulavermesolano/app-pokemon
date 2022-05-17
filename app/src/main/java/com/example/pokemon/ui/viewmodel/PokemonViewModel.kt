package com.example.pokemon.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pokemon.data.model.PokemonResult
import com.example.pokemon.repository.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PokemonViewModel@Inject constructor(
    private val pokemonRepository: PokemonRepository
) : ViewModel() {

    private var currentResult: Flow<PagingData<PokemonResult>>? = null
    fun getPokemon(): Flow<PagingData<PokemonResult>> {
        val newResult: Flow<PagingData<PokemonResult>> =
            pokemonRepository.getPokemon().cachedIn(viewModelScope)
        currentResult = newResult
        return newResult
    }
}