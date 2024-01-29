package com.personalsProjects.androidfundamentalsproject.ui.views.fragments.heroesListFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.personalsProjects.androidfundamentalsproject.data.repository.models.Hero
import com.personalsProjects.androidfundamentalsproject.databinding.FragmentCharactersListBinding
import com.personalsProjects.androidfundamentalsproject.ui.viewmodels.HeroesViewModel
import com.personalsProjects.androidfundamentalsproject.ui.views.HeroesActivity
import kotlinx.coroutines.launch

class HeroesListFragment(heroes: List<Hero>): Fragment(), MainAdapterCallback {

    private lateinit var binding : FragmentCharactersListBinding
    private val heroesViewModel: HeroesViewModel by activityViewModels()
    private val adapter = HeroesAdapter(this)
    private val heroList = heroes
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("HeroesFragment", "Hace el onViewCreated")

        setAdapter()
        setObservers()
        setListeners()
        showHeroes(heroes = heroList)
        //loadHeroes()
    }

    override fun onHeroClicked(hero: Hero) {
         Toast.makeText(requireContext(), "Pulsado ${hero.name}", Toast.LENGTH_LONG).show()
    }


    private fun setListeners() {
        binding.healButton.setOnClickListener{
           // heroesViewModel.healAllHeroes()
           // Toast.makeText(requireContext(), getString(R.string.healed), Toast.LENGTH_LONG).show()
            Toast.makeText(requireContext(), "Cura a todos", Toast.LENGTH_LONG).show()
        }
    }

    private fun setAdapter() {
        with(binding){
            rvHeroes.layoutManager = LinearLayoutManager(requireContext())
            rvHeroes.adapter = adapter
        }
    }

    private fun setObservers() {
        viewLifecycleOwner.lifecycleScope.launch {
            heroesViewModel.stateHeroes.collect{ state ->
                when (state) {
                    is HeroesViewModel.StateHeroes.OnHeroSelected -> {
                        //increaseHeroCounter(state.hero)
                        goToHeroDetails()
                    }
                    is HeroesViewModel.StateHeroes.OnHeroesUpdated -> {
                        adapter.notifyDataSetChanged()
                    }
                    is HeroesViewModel.StateHeroes.Idle -> {
                        goToHeroDetails()
                    }

                    else -> {}
                }
            }
        }
    }

    private fun goToHeroDetails() {
        //(activity as? HeroesActivity)?.goToHeroDetailsFragment()
    }

    private fun showHeroes(heroes: List<Hero>) {
        adapter.updateList(heroes)
    }





}