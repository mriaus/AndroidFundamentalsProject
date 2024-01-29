package com.personalsProjects.androidfundamentalsproject.data.repository.models

data class Hero(
    val id: String,
    val photo: String,
    val name: String,
    val description: String,
    var currentHealth: Int = 100,
    val maxHealth: Int = 100
){
    fun isAlive() = currentHealth > 0
    var isSelected = false
}
