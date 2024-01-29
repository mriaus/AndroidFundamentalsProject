package com.personalsProjects.androidfundamentalsproject.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personalsProjects.androidfundamentalsproject.data.repository.HeroRespository

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HeroesViewModel: ViewModel() {

    suspend fun getHeroes(token: String){
        Log.d("TOKEN", "$token")

        viewModelScope.launch(Dispatchers.Main) {

            val heroes = async(Dispatchers.IO) {
                HeroRespository().getHeroes(token = token)
            }.await()
             Log.d("HeroesActivity", "Heroes obtenidos: $heroes")
         }
     }
}