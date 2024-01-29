package com.personalsProjects.androidfundamentalsproject.ui.views.fragments.heroesListFragment

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.personalsProjects.androidfundamentalsproject.R
import com.personalsProjects.androidfundamentalsproject.data.repository.models.Hero
import com.personalsProjects.androidfundamentalsproject.databinding.ItemHeroBinding

interface MainAdapterCallback{
    fun onHeroClicked(hero: Hero)
}

class HeroesAdapter(private val callback: MainAdapterCallback): RecyclerView.Adapter<HeroesAdapter.MainViewHolder>(){

    private var items = listOf<Hero>()

    inner class MainViewHolder(private val binding: ItemHeroBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Hero) {
            with(binding) {
                tvName.text = item.name
                pbHealth.max = item.maxHealth
                pbHealth.progress = item.currentHealth
                Glide
                    .with(root)
                    .load(item.photo)
                    .centerCrop()
                    .placeholder(R.drawable.sleeping_goku)
                    .into(ivPhoto)
                val bgColorId = if (item.isAlive()) {
                    R.color.white
                } else {
                    R.color.grey
                }
                root.setBackgroundColor(ContextCompat.getColor(root.context, bgColorId))
                root.setOnClickListener {
                    if (item.isAlive()) {
                        callback.onHeroClicked(item)
                    } else {
                        Toast.makeText(
                            binding.root.context,
                            String.format("Muelto", item.name),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        Log.d("Adapter", "Hace el onCreateViewHolder")

        return MainViewHolder(
            ItemHeroBinding.inflate(
                LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun updateList(list: List<Hero>) {
        items = list
        Log.d("Adapter", "Hace el update list")
        notifyDataSetChanged()
    }
}