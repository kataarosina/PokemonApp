package com.example.pokemon_app.ui.pokemonList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.LceState
import com.example.domain.model.Pokemon
import com.example.domain.usecase.GetPokemonListUseCase
import com.example.domain.usecase.GetPokemonsDaoUseCase
import com.example.domain.usecase.SavePokemonUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch

class ListPokemonViewModel(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val getPokemonsDaoUseCase: GetPokemonsDaoUseCase,
    private val savePokemonUseCase: SavePokemonUseCase,
) : ViewModel() {

    private var currentPage = 1
    private var isLoading = false

    private val loadPokemonsFlow = MutableSharedFlow<Unit>(
        replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    // Mutable state flow that emits the current LCE state of the list of pokemons
    private val mutableState = MutableStateFlow<LceState<List<Pokemon>>>(LceState.Loading)
    val state: StateFlow<LceState<List<Pokemon>>>
        get() = mutableState

    val pokemonsFlow = pokemonDataFlow()
        .shareIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            replay = 1,
        )

    fun onLoadPokemons() {
        viewModelScope.launch {
            try {
                loadPokemonsFlow.emit(Unit)
            } catch (e: Exception) {
                mutableState.value = LceState.Error(e)
            }
        }
    }

    private fun pokemonDataFlow(): Flow<List<Pokemon>> {
        return loadPokemonsFlow
            .onEach {
                isLoading = true
                mutableState.value = LceState.Loading
            }
            .map {
                // Load the next page of pokemons and update the state accordingly
                getPokemonListUseCase.invoke(currentPage)
                    .apply { isLoading = false }
                    .fold(
                        onSuccess = {
                            savePokemonUseCase.invoke(it)
                            currentPage++
                            mutableState.value = LceState.Content(it)
                            it
                        },
                        onFailure = {
                            mutableState.value = LceState.Error(it)
                            emptyList()
                        }
                    )
            }
            // Start by loading data from the database and then continue with new data from the API
            .onStart {
                onLoadPokemons()
                emit(getPokemonsDaoUseCase.invoke())
            }
            // Deduplicate the list of pokemons by ID to avoid duplicates when combining the old and new data
            .scan(emptyList<Pokemon>()) { accumulator, value ->
                val newElements = value.filter { newValue ->
                    !accumulator.any { it.id == newValue.id }
                }
                accumulator + newElements
            }
            // Only emit new data if it's different from the previous data
            .distinctUntilChanged()
    }

}
