package com.personalsProjects.androidfundamentalsproject.ui.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.personalsProjects.androidfundamentalsproject.data.repository.Persistance
import com.personalsProjects.androidfundamentalsproject.databinding.ActivityCharactersListBinding
import com.personalsProjects.androidfundamentalsproject.ui.viewmodels.HeroesViewModel
import kotlinx.coroutines.launch

class HeroesActivity : AppCompatActivity() {
    //Cambiar vm
    val viewModel: HeroesViewModel by viewModels()
    lateinit var binding: ActivityCharactersListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactersListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Persistance.getToken(this)?.let {
            lifecycleScope.launch {
                viewModel.getHeroes(it)
            }
        }
    }

    fun getToken(){
    }
 /*
    private fun goToHeroesFragment(){
        supportFragmentManager.beginTransaction()
            .replace(binding.mainContainer.id, HeroesFragment())
            .commit()
    }

    fun goToHeroDetailsFragment(){
        supportFragmentManager.beginTransaction()
            .replace(binding.mainContainer.id, HeroDetailsFragment())
            .addToBackStack(null)
            .commit()
    }
    */

}
