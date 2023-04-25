package com.example.data.repository
import com.example.data.database.PokemonDatabase
import com.example.data.mapper.toDomainModels
import com.example.domain.model.Pokemon
import com.example.domain.repository.PokemonLocalRepository



internal class PokemonLocalRepositoryImpl(
    private val pokemonDatabase: PokemonDatabase,
) : PokemonLocalRepository {

    override suspend fun getPokemonFromDao(id: Int): Pokemon {
        return pokemonDatabase.pokemonDao().getPokemon(id).toDomainModels()
    }

    override suspend fun insertPokemons(pokemons: List<Pokemon>) {
        pokemonDatabase.pokemonDao().insert(pokemons.map { it.toDomainModels() })
    }

    override suspend fun getPokemonsFromDao(): List<Pokemon> {
        return pokemonDatabase.pokemonDao().getPokemons().map { it.toDomainModels()  }
    }


}