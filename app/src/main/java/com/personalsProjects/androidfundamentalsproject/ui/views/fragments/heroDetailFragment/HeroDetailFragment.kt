package com.personalsProjects.androidfundamentalsproject.ui.views.fragments.heroDetailFragment

import android.graphics.BlendMode
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.blue
import androidx.core.graphics.drawable.DrawableCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.personalsProjects.androidfundamentalsproject.R
import com.personalsProjects.androidfundamentalsproject.data.repository.models.Hero
import com.personalsProjects.androidfundamentalsproject.databinding.FragmentCharactersListBinding
import com.personalsProjects.androidfundamentalsproject.databinding.FragmentDetailHeroBinding
import com.personalsProjects.androidfundamentalsproject.ui.viewmodels.HeroesViewModel
import kotlinx.coroutines.launch

class HeroDetailFragment(): Fragment() {
    private lateinit var binding : FragmentDetailHeroBinding
    private val heroesViewModel: HeroesViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailHeroBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("HeroesFragment", "Hace el onViewCreated")
        setListeners()
        setObservers()
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            heroesViewModel.stateHeroDetails.collect{ stateHeroDetails ->
                when(stateHeroDetails) {
                    is HeroesViewModel.StateHeroDetails.Error -> {
                        Toast.makeText(requireContext(), stateHeroDetails.message, Toast.LENGTH_LONG).show()
                        parentFragmentManager.popBackStack()
                    }
                    is HeroesViewModel.StateHeroDetails.OnHeroUpdated -> {
                        displayHero(stateHeroDetails.heroSelected)
                    }
                    is HeroesViewModel.StateHeroDetails.OnHeroDied -> {
                        Toast.makeText(requireContext(), "Muelto", Toast.LENGTH_LONG).show()
                        parentFragmentManager.popBackStack()
                    }
                    HeroesViewModel.StateHeroDetails.Idle -> Unit
                    is HeroesViewModel.StateHeroDetails.OnHeroSelected -> displayHero(stateHeroDetails.heroSelected)
                }
            }
        }
    }

    private fun displayHero(heroSelected: Hero) {
        with(binding) {
            Glide
                .with(root)
                .load(heroSelected.photo)
                .centerCrop()
                .placeholder(R.drawable.sleeping_goku)
                .into(binding.imageView2)
            progressBar2.progress = heroSelected.currentHealth

            textViewLife.text = "${heroSelected.currentHealth}/${heroSelected.maxHealth} HP"

            //TODO Refactor this
            if(heroSelected.currentHealth == heroSelected.maxHealth){
                val drawable = progressBar2.progressDrawable
                val color = ContextCompat.getColor(requireContext(), R.color.blue)
                DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN)
                DrawableCompat.setTint(drawable, color)
                progressBar2.progressDrawable = drawable
            }else
            if(heroSelected.currentHealth < heroSelected.maxHealth){
                val drawable = progressBar2.progressDrawable
                val color = ContextCompat.getColor(requireContext(), R.color.jade)
                DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN)
                DrawableCompat.setTint(drawable, color)
                progressBar2.progressDrawable = drawable
            }

            if(heroSelected.currentHealth <= heroSelected.maxHealth/2){
                val drawable = progressBar2.progressDrawable
                val color = ContextCompat.getColor(requireContext(), R.color.dark_orange)
                DrawableCompat.setTintMode(drawable, PorterDuff.Mode.SRC_IN)
                DrawableCompat.setTint(drawable, color)
                progressBar2.progressDrawable = drawable
            }
        }

    }

    private fun setListeners() {
        binding.buttonHeal.setOnClickListener {
            heroesViewModel.heal()
        }
        binding.buttonHit.setOnClickListener {
            heroesViewModel.receiveDmg()
        }
    }
}