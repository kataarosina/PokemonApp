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
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.domain.model.LceState
import com.example.pokemon_app.adapter.PokemonAdapter
import com.example.pokemon_app.databinding.FragmentListPokemonBinding
import com.example.pokemon_app.extension.addPaginationScrollListener
import com.example.pokemon_app.extension.addPokemonDecoration
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel


class ListPokemonFragment : Fragment() {

    private var _binding: FragmentListPokemonBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }


    private val viewModel by viewModel<ListPokemonViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentListPokemonBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            toolbar.setupWithNavController(findNavController())

            val adapter =
                PokemonAdapter(requireContext()) {
                    findNavController().navigate(ListPokemonFragmentDirections.actionListPokemonFragmentToDetailPokemonFragment(
                        it.id))
                }
            swipeLayout.setOnRefreshListener {
                swipeLayout.isRefreshing = false
            }
            val layoutManager = LinearLayoutManager(requireContext())
            recyclerView.adapter = adapter
            recyclerView.layoutManager = layoutManager
            recyclerView.addPokemonDecoration(SPACE_SIZE)
            recyclerView.addPaginationScrollListener(layoutManager, ITEM_TO_LOAD) {
                viewModel.onLoadPokemons()
            }



            viewModel.state.onEach { lce ->
                when (lce) {
                    is LceState.Content -> {
                        isVisibleProgressBar(false)
                    }
                    is LceState.Error -> {
                        isVisibleProgressBar(false)
                        Toast.makeText(requireContext(),
                            lce.throwable.message ?: "", Toast.LENGTH_SHORT).show()
                    }
                    LceState.Loading -> {
                        isVisibleProgressBar(true)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)

            viewModel.pokemonsFlow.onEach {
                adapter.submitList(it)
            }.launchIn(viewLifecycleOwner.lifecycleScope)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun isVisibleProgressBar(visible:Boolean) {
        binding.paginationProgressBar.isVisible = visible
    }

    companion object {
        private const val SPACE_SIZE = 25
        private const val ITEM_TO_LOAD = 20
    }

}