package com.example.pokemon_app.ui.pokemonDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import coil.load
import com.example.domain.model.LceState
import com.example.domain.model.Pokemon
import com.example.pokemon_app.databinding.FragmentDetailPokemonBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf


class DetailPokemonFragment : Fragment() {

    private var _binding: FragmentDetailPokemonBinding? = null
    private val binding
        get() = requireNotNull(_binding) {
            "View was destroyed"
        }


    private val viewModel by viewModel<DetailPokemonViewModel> {
        parametersOf(args.id)
    }

    private val args by navArgs<DetailPokemonFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return FragmentDetailPokemonBinding.inflate(inflater, container, false)
            .also { _binding = it }
            .root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            toolbar.setupWithNavController(findNavController())

            lifecycleScope.launch {
                viewModel.pokemonFlow.collect { lce ->
                    when (lce) {
                        is LceState.Content -> {
                            isVisibleProgressBar(false)
                            bind(lce.data)
                        }

                        is LceState.Error -> {
                            isVisibleProgressBar(false)
                            Toast.makeText(
                                requireContext(),
                                lce.throwable.message ?: "", Toast.LENGTH_SHORT
                            ).show()
                        }

                        LceState.Loading -> {
                            isVisibleProgressBar(true)
                        }
                    }
                }
            }
        }
    }

    private fun bind(pokemon: Pokemon) {
        with(binding) {
            imageView.load(pokemon.image)
            name.text = pokemon.name
            type.text = "type: "+ pokemon.type
            weight.text="weight: " + pokemon.weight.toString()
            height.text="heiht: " + pokemon.height.toString()
        }
    }

        private fun isVisibleProgressBar(visible: Boolean) {
            binding.paginationProgressBar.isVisible = visible
        }

        companion object {
        }

        override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }

    }
