package com.example.data.repository
import com.example.data.database.PokemonDatabase
import com.example.data.mapper.toDomainModels
import com.example.domain.model.Pokemon
import com.example.domain.repository.PokemonLocalRepository



internal class PokemonLocalRepositoryImpl(
    private val pokemonDatabase: PokemonDatabase,
) : PokemonLocalRepository {

    // Retrieves a single Pokemon from the database by ID and converts it to a domain model
    override suspend fun getPokemonFromDao(id: Int): Pokemon {
        return pokemonDatabase.pokemonDao().getPokemon(id).toDomainModels()
    }

    // Inserts a list of Pokemons into the database after converting them to domain models
    override suspend fun insertPokemons(pokemons: List<Pokemon>) {
        pokemonDatabase.pokemonDao().insert(pokemons.map { it.toDomainModels() })
    }

    // Retrieves a list of  Pokemons from the database and converts them to domain models
    override suspend fun getPokemonsFromDao(): List<Pokemon> {
        return pokemonDatabase.pokemonDao().getPokemons().map { it.toDomainModels()  }
    }


}