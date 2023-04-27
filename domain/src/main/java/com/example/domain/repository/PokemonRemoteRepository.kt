package com.example.domain.repository

import com.example.domain.model.Pokemon

interface PokemonRemoteRepository {
    suspend fun getPokemon(id: Int): Result<Pokemon>

    suspend fun getPokemons(page: Int, pageSize: Int):Result<List<Pokemon>>
}

