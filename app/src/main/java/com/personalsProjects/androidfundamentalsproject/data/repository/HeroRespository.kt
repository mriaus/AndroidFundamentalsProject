package com.personalsProjects.androidfundamentalsproject.data.repository

import android.util.Log
import com.personalsProjects.androidfundamentalsproject.data.api.model.HeroDto
import com.personalsProjects.androidfundamentalsproject.data.repository.models.Hero
import com.personalsProjects.androidfundamentalsproject.data.services.HeroService

class HeroRespository {

    fun getHeroes(token: String): List<Hero>{
        val heroes = HeroService().getAllHeroes(token)
        Log.d("REPOSITORY", "Heroes obtenidos REPO: $heroes")

        return heroes.map { Hero(it.id, it.photo, it.name, it.description) }
    }
}