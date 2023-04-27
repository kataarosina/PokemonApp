package com.example.pokemon_app.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.domain.model.Pokemon
import com.example.pokemon_app.databinding.ItemPokemonBinding


class PokemonAdapter(
    context: Context,
    // Callback to be invoked when a pokemon item is clicked
    private val onItemClicked: (Pokemon) -> Unit
) : ListAdapter<Pokemon, PokemonViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    // Inflates the item view and creates a PokemonViewHolder instance
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            binding = ItemPokemonBinding.inflate(layoutInflater, parent, false)
        )
    }

    // Binds a pokemon item to a view holder and sets click listener
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,onItemClicked)
    }

    // Companion object that defines the DIFF_UTIL
    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Pokemon>() {
            // Compares item ids
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }

            // Compares item contents
            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class PokemonViewHolder(
    private val binding: ItemPokemonBinding
) : RecyclerView.ViewHolder(binding.root) {
    // Binds a pokemon item to the view holder's views and sets click listener
    fun bind(item: Pokemon, onItemClicked: (Pokemon) -> Unit) {
        binding.imagePokemon.load(item.image)
        binding.pokemonName.text = item.name
        itemView.setOnClickListener {
            onItemClicked(item)
        }
    }
}