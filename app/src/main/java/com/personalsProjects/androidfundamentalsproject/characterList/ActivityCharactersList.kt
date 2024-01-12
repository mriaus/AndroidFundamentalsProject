package com.personalsProjects.androidfundamentalsproject.characterList

import android.content.Context
import android.content.Intent
import com.personalsProjects.androidfundamentalsproject.databinding.ActivityCharactersListBinding
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class ActivityCharactersList : AppCompatActivity() {

    companion object {
        fun goToCharactersList(context: Context){
            val intent = Intent(context, ActivityCharactersList::class.java)
            context.startActivity(intent)
        }
    }

    lateinit private var binding: ActivityCharactersListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCharactersListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logOutButton.setOnClickListener({

        })

    }
}