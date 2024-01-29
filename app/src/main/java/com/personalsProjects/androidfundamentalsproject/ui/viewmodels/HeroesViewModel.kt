package com.personalsProjects.androidfundamentalsproject.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personalsProjects.androidfundamentalsproject.data.repository.HeroRespository
import com.personalsProjects.androidfundamentalsproject.data.repository.models.Hero

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HeroesViewModel: ViewModel() {

    private val _stateHeroes = MutableStateFlow<StateHeroes>(StateHeroes.Idle)
    val stateHeroes: StateFlow<StateHeroes> = _stateHeroes

    private val _stateHeroDetails = MutableStateFlow<StateHeroDetails>(StateHeroDetails.Idle)
    val stateHeroDetails: StateFlow<StateHeroDetails> = _stateHeroDetails

    private val _uiState = MutableStateFlow<HeroesViewModel.State>(HeroesViewModel.State.Idle())
    val uiState: StateFlow<HeroesViewModel.State> = _uiState

     private var heroList = mutableListOf<Hero>()


    sealed class State {
        class Idle : State()
        class Error() : State()
        class Loading(val isLoading: Boolean) : State()
        class SuccesLoad(val heroes: List<Hero>) : State()
    }

    suspend fun getHeroes(token: String){
        Log.d("TOKEN", "$token")

        viewModelScope.launch(Dispatchers.Main) {
            val heroes = async(Dispatchers.IO) {
                HeroRespository().getHeroes(token = token)
            }.await()
                heroList.addAll(heroes)
            _uiState.value = State.SuccesLoad(heroes)

            Log.d("HeroesActivity", "Heroes obtenidos: $heroes")
         }
     }


    fun setHeroes(heroes: List<Hero>) {
        heroList.clear()
        heroList.addAll(heroes)
        //_stateHeroes.value = StateHeroes.OnHeroesReceived(heroes)
    }

private fun updateHero(hero: Hero) {
    _stateHeroDetails.value = if (hero.isAlive()) {
        _stateHeroDetails.value = StateHeroDetails.OnHeroUpdated(hero)
        StateHeroDetails.Idle
    } else {
        StateHeroDetails.OnHeroDied
    }
}
    //Fragments state
    sealed class StateHeroes {
        data class OnHeroSelected(val hero: Hero) : StateHeroes()
        object OnHeroesUpdated : StateHeroes()
        object Idle : StateHeroes()
    }

    sealed class StateHeroDetails {
        object Idle : StateHeroDetails()
        data class OnHeroSelected(val heroSelected: Hero) : StateHeroDetails()
        data class OnHeroUpdated(val heroSelected: Hero) : StateHeroDetails()
        object OnHeroDied : StateHeroDetails()
        data class Error(val message: String) : StateHeroDetails()
    }
}