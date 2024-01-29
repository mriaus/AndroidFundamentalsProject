package com.personalsProjects.androidfundamentalsproject

const val BASE_URL = "https://dragonball.keepcoding.education/api"

val contentType = "application/json"

enum class Endpoints(val path: String) {
    LOGIN("/auth/login"),
    HEROES("/heros/all")
}

val LOGIN_ERROR = "LOGIN_ERROR"