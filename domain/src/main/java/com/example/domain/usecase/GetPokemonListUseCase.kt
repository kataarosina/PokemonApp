package com.example.domain.usecase

import com.example.domain.model.Pokemon
import com.example.domain.repository.PokemonRemoteRepository

class GetPokemonListUseCase(
    private val pokemonRemoteRepository: PokemonRemoteRepository,
) {
    private var pageSize = 50
    // Fetches a list of Pokemon from the remote repository for the specified page
    // Returns a Result object containing either the list of Pokemon or an exception if an error occurred
    suspend operator fun invoke(currentPage: Int): Result<List<Pokemon>> {
        return pokemonRemoteRepository.getPokemons(currentPage, pageSize)
    }
}
