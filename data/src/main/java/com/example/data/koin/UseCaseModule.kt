package com.example.data.koin

import com.example.domain.usecase.GetPokemonListUseCase
import com.example.domain.usecase.GetPokemonUseCase
import com.example.domain.usecase.SavePokemonUseCase
import com.example.domain.usecase.GetPokemonsDaoUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module


internal val useCaseModule = module {
    factoryOf(::GetPokemonUseCase)
    factoryOf(::GetPokemonListUseCase)
    factoryOf(::GetPokemonsDaoUseCase)
    factoryOf(::SavePokemonUseCase)

}