package com.personalsProjects.androidfundamentalsproject.data.services

import android.util.Log
import com.personalsProjects.androidfundamentalsproject.BASE_URL
import com.personalsProjects.androidfundamentalsproject.Endpoints
import com.personalsProjects.androidfundamentalsproject.LOGIN_ERROR
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class UserService {

   fun login(user:String, password: String): String {
        val client = OkHttpClient()
        val url = "${BASE_URL}${Endpoints.LOGIN.path}"
        val credentials = Credentials.basic(user, password)
        val formBody = FormBody.Builder().build() //Hace que sea post
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", credentials)
            .post(formBody)
            .build()

        val call = client.newCall(request)
        val response = call.execute()
        Log.d("RESPONSE SERVICE", "$response")


        if (response.isSuccessful) {
            //Importante hacer esto para que no se pierda el body
            response.body?.let {
                var token = it.string()
                return token
            }

        } else {
            return LOGIN_ERROR
        }
        return LOGIN_ERROR
    }
}