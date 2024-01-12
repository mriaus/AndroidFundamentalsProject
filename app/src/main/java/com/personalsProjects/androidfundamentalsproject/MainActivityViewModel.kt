package com.personalsProjects.androidfundamentalsproject

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainActivityViewModel: ViewModel() {

  private val _uiState = MutableStateFlow("")
    val uiState: StateFlow<String> = _uiState

    fun onEmailChange(){

    }

    fun onPasswordChange(){

    }
    fun onPressLogin(){
        Log.println(Log.INFO,"PULSADO", "El botón se ha pulsado y pilla el vm")

    }
}