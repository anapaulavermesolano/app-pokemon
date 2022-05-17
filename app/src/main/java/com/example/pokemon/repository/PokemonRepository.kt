package com.example.pokemon.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.pokemon.data.PokemonPagingDataSource
import com.example.pokemon.data.network.PokemonApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(private val pokemonApi: PokemonApi) {

    fun getPokemon() = Pager(
        config = PagingConfig(
            enablePlaceholders = false,
            pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = {
                PokemonPagingDataSource(pokemonApi)
            }
    ).flow

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

}
