package com.example.pokemon.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.pokemon.data.PokemonPagingDataSource
import com.example.pokemon.data.model.PokemonDetailResponse
import com.example.pokemon.data.network.PokemonApi
import com.example.pokemon.util.ResultType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
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

    suspend fun getPokemonDetail(id: Int): ResultType<PokemonDetailResponse> {
        return withContext(Dispatchers.IO) {
            try {
                val pokemon = pokemonApi.getPokemonDetail(id)
                ResultType.Success(pokemon)
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        ResultType.Error(
                            false,
                            throwable.response()?.errorBody()
                        )
                    }
                    else -> {
                        ResultType.Error(true, null)
                    }
                }
            }
        }
    }

    companion object {
        const val NETWORK_PAGE_SIZE = 20
    }

}
