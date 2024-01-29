package com.personalsProjects.androidfundamentalsproject.data.services

import android.util.Log
import com.google.gson.Gson
import com.personalsProjects.androidfundamentalsproject.BASE_URL
import com.personalsProjects.androidfundamentalsproject.Endpoints
import com.personalsProjects.androidfundamentalsproject.data.api.model.HeroDto
import com.personalsProjects.androidfundamentalsproject.data.repository.models.Hero
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class HeroService {
        fun getAllHeroes(token: String): List<HeroDto> {
            var heroList: List<HeroDto> = listOf()
                        val client = OkHttpClient()
                        val url = "${BASE_URL}${Endpoints.HEROES.path}"
                        val formBody = FormBody.Builder()
                            .add("name", "")
                            .build()
                        val request = Request.Builder()
                            .url(url)
                            .post(formBody)
                            .addHeader("Authorization", "Bearer $token")
                            .build()
                        val call = client.newCall(request)
                        val response = call.execute()

                        if (response.isSuccessful) {
                            try {
                                val heroesDto: Array<HeroDto> = Gson().fromJson(response.body?.string(), Array<HeroDto>::class.java)
                                heroList = heroesDto.map { HeroDto(id = it.id, photo = it.photo, description = it.description, name = it.name, favorite = it.favorite) }
                            } catch (ex: Exception) {
                                return heroList
                            }
                        } else {
                            return heroList
                        }
            return heroList
        }
}