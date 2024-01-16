package com.personalsProjects.androidfundamentalsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.personalsProjects.androidfundamentalsproject.characterList.ActivityCharactersList
import com.personalsProjects.androidfundamentalsproject.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val viewModel: MainActivityViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObservers()
        binding.button.setOnClickListener({viewModel.onPressLogin("","")
           // ActivityCharactersList.goToCharactersList(this)
        })
        binding.editTextTextEmailAddress.doAfterTextChanged {  }
        binding.editTextTextPassword.doAfterTextChanged {  }
    }

    private fun setObservers() {
        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->
                    when (state) {
                        is MainActivityViewModel.State.Idle -> idle()
                        is MainActivityViewModel.State.Error -> showError(state.message)
                        is MainActivityViewModel.State.Loading -> showLoading(true)
                        is MainActivityViewModel.State.SuccesLogin -> showSuccesLogin()
                    }
            }
        }

    }

    private fun showSuccesLogin() {
       // viewModel.onPressLogin("","")
       // ActivityCharactersList.goToCharactersList(this)
    }

    private fun showLoading(show: Boolean) {
        Log.println(Log.INFO,"Entra en showLoading()", "${show}")

        if (show) {
            binding.button.visibility = View.GONE
            binding.pbLoading.visibility = View.VISIBLE
        }
        else{
            binding.pbLoading.visibility = View.GONE
            binding.button.visibility = View.VISIBLE
        }
    }

    private fun showError(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    private fun idle(){
        Log.println(Log.INFO,"Entra en Idle()", "")

        // showLoading(false)
    }

}