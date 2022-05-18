package com.example.pokemon.ui.view.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemon.data.model.Stats
import com.example.pokemon.databinding.ItemStatsBinding

class StatsAdapter: ListAdapter<Stats, StatsAdapter.StatsViewHolder>(StatsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatsViewHolder {
        val itemBinding = ItemStatsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return StatsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: StatsViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item)
    }

    class StatsViewHolder(private val itemBinding: ItemStatsBinding):
        RecyclerView.ViewHolder(itemBinding.root) {
            fun bind(model: Stats){
                itemBinding.apply {
                    statsName.text = model.stat.name
                    statsProgressbar.progress = model.base_stat
                }

            }
        }
}

class StatsDiffCallback:  DiffUtil.ItemCallback<Stats>(){
    override fun areItemsTheSame(oldItem: Stats, newItem: Stats): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Stats, newItem: Stats): Boolean {
        return oldItem == newItem
    }
}