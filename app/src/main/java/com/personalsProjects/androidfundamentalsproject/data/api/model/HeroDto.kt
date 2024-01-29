package com.personalsProjects.androidfundamentalsproject.data.api.model

data class HeroDto (
    val id: String,
    val photo: String,
    var favorite: Boolean,
    val name: String,
    val description: String,
)