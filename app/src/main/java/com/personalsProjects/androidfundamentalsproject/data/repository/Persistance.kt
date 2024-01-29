package com.personalsProjects.androidfundamentalsproject.data.repository

import android.content.Context

class Persistance {
    companion object {
        private var preferencesName = "token"
        private var USER_TOKEN = "USER_TOKEN"

        fun setToken(token: String, context: Context){
            with(context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).edit()){
                putString(USER_TOKEN,token)
                apply()
            }
        }

        fun getToken(context: Context) = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE).
                getString(USER_TOKEN, "")
    }
}