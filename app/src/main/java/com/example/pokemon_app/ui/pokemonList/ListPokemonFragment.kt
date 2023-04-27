package com.example.pokemon_app.ui.pokemonList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.LceState
import com.example.pokemon_app.adapter.PokemonAdapter
import com.example.pokemon_app.databinding.FragmentListPokemonBinding
import com.example.pokemon_app.extension.addPaginationScrollListener
import com.example.pokemon_app.extension.addPokemonDecoration
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListPokemonFragment : Fragment() {


    private var _binding: FragmentListPokemonBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }

    private val viewModel by viewModel<ListPokemonViewModel>()

    // Inflates the fragment's view and returns the root view
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentListPokemonBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    // Sets up the fragment's views and observes the view model's data
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val adapter = PokemonAdapter(requireContext()) { pokemon ->
                findNavController().navigate(
                    ListPokemonFragmentDirections.actionListPokemonFragmentToDetailPokemonFragment(pokemon.id)
                )
            }
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.addPokemonDecoration(SPACE_SIZE)
            recyclerView.addPaginationScrollListener(
                layoutManager = recyclerView.layoutManager as LinearLayoutManager,
                itemsToLoad = ITEM_TO_LOAD
            ) {
                viewModel.onLoadPokemons()
            }

            // Observe the view model's data and update the views accordingly

            lifecycleScope.launch {
                viewModel.state.collect { lce ->
                    when (lce) {
                        is LceState.Content -> {
                            isVisibleProgressBar(false)
                        }
                        is LceState.Error -> {
                            isVisibleProgressBar(false)
                            Toast.makeText(
                                requireContext(),
                                lce.throwable.message ?: "",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                        LceState.Loading -> {
                            isVisibleProgressBar(true)
                        }
                    }
                }
            }

            // Observe the view model's pokemonsFlow and submit the list to the adapter
            lifecycleScope.launch {
                viewModel.pokemonsFlow.collect { pokemons ->
                    adapter.submitList(pokemons)
                }
            }


            swipeLayout.setOnRefreshListener {
                swipeLayout.isRefreshing = false
            }
        }
    }

    private fun isVisibleProgressBar(visible: Boolean) {
        binding.paginationProgressBar.isVisible = visible
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val SPACE_SIZE = 25
        private const val ITEM_TO_LOAD = 20
    }
}
