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
    private val onItemClicked: (Pokemon) -> Unit,
    ) : ListAdapter<Pokemon, PokemonViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(

            binding = ItemPokemonBinding.inflate(layoutInflater, parent, false)
        )

    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item,onItemClicked)
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class PokemonViewHolder(
    private val binding: ItemPokemonBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Pokemon, onItemClicked: (Pokemon) -> Unit) {
        binding.imagePokemon.load(item.image)
        binding.pokemonName.text = item.name
        itemView.setOnClickListener {
            onItemClicked(item)
        }
    }
}
