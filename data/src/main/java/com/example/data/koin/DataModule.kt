package com.example.data.koin
import org.koin.dsl.module

val dataModule = module {
    includes(
        pokemonDatabaseModule,
        pokemonRemoteRepositoryModule,
        pokemonLocalRepositoryModule,
        networkModule,
        useCaseModule,

    )
}