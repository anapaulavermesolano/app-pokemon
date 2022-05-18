package com.example.pokemon.ui.view.home

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.pokemon.data.model.PokemonResult
import com.example.pokemon.databinding.ItemPokemonBinding
import com.example.pokemon.util.gone
import com.example.pokemon.util.visible

class PokemonAdapter(private val pokemon: (PokemonResult) -> Unit):
    PagingDataAdapter<PokemonResult, PokemonAdapter.PokemonViewHolder>(PokemonDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemBinding = ItemPokemonBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PokemonViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item, pokemon)
    }

    inner class PokemonViewHolder(private val itemBinding: ItemPokemonBinding):
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(
            model: PokemonResult,
            callback: (PokemonResult) -> Unit
        ) {
            itemBinding.apply {
                pokemonName.text = model.name
                Glide.with(root)
                    .load(model.getPokemonImageUrl())
                    .centerCrop()
                    .fitCenter()
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .listener(object: RequestListener<Drawable>{
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressImage.visible()
                            return false
                        }

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progressImage.gone()
                            return false
                        }

                    })
                    .into(pokemonImage)

                root.setOnClickListener {
                    callback.invoke(model)
                }
            }
        }
    }
}


class PokemonDiffCallback:  DiffUtil.ItemCallback<PokemonResult>(){
    override fun areItemsTheSame(oldItem: PokemonResult, newItem: PokemonResult): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: PokemonResult, newItem: PokemonResult): Boolean {
        return oldItem == newItem
    }
}