package com.example.domain.usecase

import com.example.domain.model.LceState
import com.example.domain.model.Pokemon
import com.example.domain.repository.PokemonLocalRepository
import com.example.domain.repository.PokemonRemoteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetPokemonUseCase (
    private val pokemonRemoteRepository: PokemonRemoteRepository,
    private val pokemonLocalRepository: PokemonLocalRepository,) {

    operator fun invoke(pokemonId :Int): Flow<LceState<Pokemon>> = flow {
        val pokemon = pokemonRemoteRepository.getPokemon(pokemonId)
            .fold(
                onSuccess = { pokemon ->
                    LceState.Content(pokemon)
                },
                onFailure = { err ->
                    LceState.Error(err)
                }
            )
        emit(pokemon)

    }.onStart {
        emit(LceState.Content(pokemonLocalRepository.getPokemonFromDao(pokemonId)))
    }

}
