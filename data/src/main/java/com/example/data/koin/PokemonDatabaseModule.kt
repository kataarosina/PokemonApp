package com.example.data.koin

import androidx.room.Room
import com.example.data.database.PokemonDatabase
import org.koin.dsl.module

internal val pokemonDatabaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            PokemonDatabase::class.java,
            "pokemon_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}