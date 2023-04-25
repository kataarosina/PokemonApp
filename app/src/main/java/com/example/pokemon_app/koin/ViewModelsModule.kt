package com.example.pokemon_app.koin

import com.example.pokemon_app.ui.pokemonDetail.DetailPokemonViewModel
import com.example.pokemon_app.ui.pokemonList.ListPokemonViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module




val viewModelsModule = module {
    viewModelOf(::ListPokemonViewModel)
    viewModelOf(::DetailPokemonViewModel)

}