package com.example.domain.usecase

import com.example.domain.model.Pokemon
import com.example.domain.repository.PokemonRemoteRepository

class GetPokemonListUseCase(
    private val pokemonRemoteRepository: PokemonRemoteRepository,
) {
    private var pageSize = 50

    suspend operator fun invoke( currentPage: Int ): Result<List<Pokemon>> {
        return pokemonRemoteRepository.getPokemons(currentPage, pageSize)
    }
}