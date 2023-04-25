package com.example.domain.usecase

import com.example.domain.model.Pokemon
import com.example.domain.repository.PokemonLocalRepository

class SavePokemonUseCase(private val pokemonLocalRepository: PokemonLocalRepository) {
    suspend operator fun invoke(listPokemons:List<Pokemon> ){
        pokemonLocalRepository.insertPokemons(listPokemons)
    }
}