package com.example.domain.repository
import com.example.domain.model.Pokemon


interface PokemonLocalRepository {
    suspend fun getPokemonFromDao(id: Int): Pokemon

    suspend fun insertPokemons(pokemons: List<Pokemon>)

    suspend fun getPokemonsFromDao():List<Pokemon>

}