package com.example.domain.usecase

import com.example.domain.model.Pokemon
import com.example.domain.repository.PokemonLocalRepository



class GetPokemonsDaoUseCase(private val pokemonLocalRepository: PokemonLocalRepository) {
    suspend operator fun invoke(): List<Pokemon> {
        return pokemonLocalRepository.getPokemonsFromDao()
    }
}