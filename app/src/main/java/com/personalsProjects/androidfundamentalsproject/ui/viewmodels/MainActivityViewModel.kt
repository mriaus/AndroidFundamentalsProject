package com.personalsProjects.androidfundamentalsproject.ui.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.personalsProjects.androidfundamentalsproject.LOGIN_ERROR
import com.personalsProjects.androidfundamentalsproject.data.repository.Persistance
import com.personalsProjects.androidfundamentalsproject.data.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.lang.Exception

class MainActivityViewModel: ViewModel() {

  private val BASE_URL = "https://dragonball.keepcoding.education"
  private var token = ""
  private val _uiState = MutableStateFlow<State>(State.Idle())
  val uiState: StateFlow<State> = _uiState

  sealed class State {
    class Idle : State()
    class Error(val message: String) : State()
    class Loading(val isLoading: Boolean) : State()
    class SuccesLogin(val token: String) : State()
  }

  fun onEmailChange() {

  }

  fun onPasswordChange() {

  }

  suspend fun onPressLogin(user: String, password: String) {
    viewModelScope.launch(Dispatchers.Main) {
      try {
        _uiState.value = State.Loading(true)
        val result = async(Dispatchers.IO) {
          UserRepository().login(user, password)
        }.await()

        if (result !== LOGIN_ERROR) {
          viewModelScope.launch(Dispatchers.Main) {
            _uiState.value = State.SuccesLogin(result)
          }
        } else {
          Log.d("ELSE", "$result")
          viewModelScope.launch(Dispatchers.Main) {
            _uiState.value = State.Error("Error al realizar el login")
          }
        }
      } catch (e: Exception) {
        _uiState.value = State.Error("Error inesperado al realizar el login")
      } finally {
        _uiState.value = State.Loading(false)
      }
    }
  }


}
