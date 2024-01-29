package com.personalsProjects.androidfundamentalsproject.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.personalsProjects.androidfundamentalsproject.data.repository.Persistance
import com.personalsProjects.androidfundamentalsproject.data.repository.models.Hero
import com.personalsProjects.androidfundamentalsproject.databinding.ActivityHeroesBinding
import com.personalsProjects.androidfundamentalsproject.ui.viewmodels.HeroesViewModel
import com.personalsProjects.androidfundamentalsproject.ui.views.fragments.heroesListFragment.HeroesListFragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HeroesActivity : AppCompatActivity() {
    //Cambiar vm
    val viewModel: HeroesViewModel by viewModels()
    lateinit var binding: ActivityHeroesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHeroesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObservers()

        Persistance.getToken(this)?.let {
            lifecycleScope.launch {
                viewModel.getHeroes(it)
            }
        }
    }

    private fun setObservers() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                when (state) {
                    is HeroesViewModel.State.Error -> idle()
                    is HeroesViewModel.State.Idle -> idle()
                    is HeroesViewModel.State.Loading -> idle()
                    is HeroesViewModel.State.SuccesLoad -> showHeroes(state.heroes)
                    else -> {}
                }
            }
        }

    }

    private fun showHeroes(heroes: List<Hero>){
        hideLoading()
        supportFragmentManager.beginTransaction()
            .replace(binding.mainContainer.id, HeroesListFragment(heroes))
            .commit()
    }

    private fun showLoading(){

    }

    private fun idle(){

    }

    private fun hideLoading(){
        binding.textViewLoading.visibility = View.GONE
        binding.progressBar.visibility = View.GONE
    }


    /*
        fun goToHeroDetailsFragment(){
            supportFragmentManager.beginTransaction()
                .replace(binding.mainContainer.id, HeroDetailsFragment())
                .addToBackStack(null)
                .commit()
        }
        */

}
