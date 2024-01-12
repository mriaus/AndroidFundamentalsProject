package com.personalsProjects.androidfundamentalsproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.widget.doAfterTextChanged
import com.personalsProjects.androidfundamentalsproject.characterList.ActivityCharactersList
import com.personalsProjects.androidfundamentalsproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val viewModel: MainActivityViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener({viewModel.onPressLogin()
            ActivityCharactersList.goToCharactersList(this)
        })
        binding.editTextTextEmailAddress.doAfterTextChanged {  }
        binding.editTextTextPassword.doAfterTextChanged {  }
    }
}