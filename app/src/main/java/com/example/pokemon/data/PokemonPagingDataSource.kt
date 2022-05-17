package com.example.pokemon.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pokemon.data.model.PokemonResult
import com.example.pokemon.data.network.PokemonApi

class PokemonPagingDataSource (private val pokemonApi: PokemonApi) :
    PagingSource<Int, PokemonResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PokemonResult> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = pokemonApi.getPokemon(params.loadSize, page)
            LoadResult.Page(
                data = response.results,
                prevKey = if (page == STARTING_PAGE_INDEX) null else page.minus(params.loadSize),
                nextKey = if (response.results.isEmpty()) null else page.plus(params.loadSize)
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PokemonResult>): Int? {
        return state.anchorPosition
    }

    companion object {
        private const val STARTING_PAGE_INDEX = 0
    }
}