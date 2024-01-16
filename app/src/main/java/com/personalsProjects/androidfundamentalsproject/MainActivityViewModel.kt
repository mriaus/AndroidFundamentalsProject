package com.personalsProjects.androidfundamentalsproject

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.Credentials
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivityViewModel: ViewModel() {

  private val BASE_URL = "https://dragonball.keepcoding.education"
  private var token = ""
  private val _uiState = MutableStateFlow<State>(State.Idle())
    val uiState: StateFlow<State> = _uiState

  sealed class State{
    class Idle: State()
    class Error(val message: String) : State()
    class Loading(val isLoading: Boolean): State()
    class SuccesLogin: State()
  }
    fun onEmailChange(){

    }

    fun onPasswordChange(){

    }
    fun onPressLogin(user: String, password: String){
      _uiState.value = State.Loading(true)
      viewModelScope.launch(Dispatchers.IO) {
        val client = OkHttpClient()
        val url = "${BASE_URL}/api/auth/login"
        val credentials = Credentials.basic("riau222@gmail.com","123456..")
        val formBody = FormBody.Builder().build() //Hace que sea post
        val request = Request.Builder()
          .url(url)
          .addHeader("Authorization", credentials)
          .post(formBody)
          .build()

        val call = client.newCall(request)
        val response = call.execute()
        if(response.isSuccessful){
          //Importante hacer esto para que no se pierda el body
          _uiState.value = response.body?.let {
            token = it.string()
            Log.println(Log.INFO,"Hace succeslogin", "")

            State.SuccesLogin()
          }  ?: State.Error("Sin respuesta al realizar el login")


          //Añadir guardado de token

        }else{
          _uiState.value = State.Error("Error al realizar el login")
        }
        Log.println(Log.INFO,"Acabado login", "${response}")
        Log.println(Log.INFO,"TOKEN login", token)

      }
      _uiState.value = State.Idle()
    }


}