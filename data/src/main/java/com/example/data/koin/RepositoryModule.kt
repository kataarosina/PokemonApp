package com.example.data.koin

import com.example.data.repository.PokemonRemoteRepositoryImpl
import com.example.data.repository.PokemonLocalRepositoryImpl
import com.example.domain.repository.PokemonLocalRepository
import com.example.domain.repository.PokemonRemoteRepository
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal val pokemonRemoteRepositoryModule = module {
    singleOf(::PokemonRemoteRepositoryImpl){bind<PokemonRemoteRepository>() }
}


internal val pokemonLocalRepositoryModule = module {
    singleOf(::PokemonLocalRepositoryImpl){bind<PokemonLocalRepository>() }
}

