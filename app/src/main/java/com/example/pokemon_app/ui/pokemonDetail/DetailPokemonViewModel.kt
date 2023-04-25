package com.example.pokemon_app.ui.pokemonDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.LceState
import com.example.domain.usecase.GetPokemonUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class DetailPokemonViewModel(
    getPokemonUseCase: GetPokemonUseCase,
    pokemonId: Int,
) : ViewModel() {

    val pokemonFlow = getPokemonUseCase.invoke(pokemonId).stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = LceState.Loading
    )

}