package com.example.data.database


import androidx.room.*
import com.example.data.model.PokemonEntity

@Dao
internal interface PokemonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pokemons: List<PokemonEntity>)

    @Update
    suspend fun update(pokemon: PokemonEntity)

    @Delete
    suspend fun delete(pokemon: PokemonEntity)

    @Query("SELECT * from pokemons_table WHERE id = :id")
    suspend fun getPokemon(id: Int): PokemonEntity

   @Query("SELECT * from pokemons_table")
    suspend fun getPokemons(): List<PokemonEntity>

}