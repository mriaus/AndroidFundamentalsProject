package com.personalsProjects.androidfundamentalsproject.data.repository

import com.personalsProjects.androidfundamentalsproject.data.services.UserService

class UserRepository {
    suspend fun  login(user:String, password:String): String{
       return UserService().login(user,password)
    }

}