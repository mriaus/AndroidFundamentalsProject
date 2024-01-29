package com.personalsProjects.androidfundamentalsproject.ui.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import com.personalsProjects.androidfundamentalsproject.data.repository.Persistance
import com.personalsProjects.androidfundamentalsproject.databinding.ActivityHeroesBinding
import com.personalsProjects.androidfundamentalsproject.databinding.ActivityMainBinding
import com.personalsProjects.androidfundamentalsproject.ui.viewmodels.MainActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val viewModel: MainActivityViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setObservers()
        binding.button.setOnClickListener {
            lifecycleScope.launch {
                viewModel.onPressLogin(binding.editTextTextEmailAddress.text.toString(), binding.editTextTextPassword.text.toString())
            }
        }
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
                        is MainActivityViewModel.State.SuccesLogin -> showSuccesLogin(state.token)
                        else -> {}
                    }
            }
        }

    }

    private fun showSuccesLogin(token: String) {
        showLoading(false)
        Persistance.setToken(token, this)
        val intent = Intent(this, HeroesActivity::class.java)
        startActivity(intent)
    }

    private fun showLoading(show: Boolean) {
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
        showLoading(false)
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    private fun idle(){
        showLoading(false)
    }



}