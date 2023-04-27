package com.example.data.repository


import com.example.data.api.PokemonApi
import com.example.data.mapper.toDomainModel
import com.example.domain.model.Pokemon
import com.example.domain.repository.PokemonRemoteRepository

internal class PokemonRemoteRepositoryImpl(
    private val pokemonService: PokemonApi,
) : PokemonRemoteRepository {

    override suspend fun getPokemon(id: Int): Result<Pokemon> { // Function to get a single Pokemon by its ID
        return runCatching {
            val pokemon = pokemonService.getPokemon(id) // Fetch Pokemon details using PokemonAPI service
            val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png" // URL for the Pokemon image
            pokemon.toDomainModel(imageUrl) // Convert the data to domain model
        }
    }

    override suspend fun getPokemons(page: Int, pageSize: Int): Result<List<Pokemon>> { // Function to get a list of Pokemon
        return runCatching {
            pokemonService.getPokemons((page - 1) * pageSize, pageSize)
                .results.map { result ->
                    val id = result.url.split("/").let { it[it.size - 2].toInt() }
                    val details = pokemonService.getPokemon(id) // Fetch Pokemon details using PokemonAPI service
                    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png" // URL for the Pokemon image
                    details.toDomainModel(imageUrl)
                }
        }
    }
}
