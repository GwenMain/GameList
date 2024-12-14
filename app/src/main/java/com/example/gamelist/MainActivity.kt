package com.example.gamelist


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamelist.data.Game
import com.example.gamelist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gameAdapter = GameAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = gameAdapter

        val sampleGames = listOf(
            Game("The Legend of Zelda", "An epic adventure game", true, false, true),
            Game("Super Mario Bros", "Classic platformer game", true, true, false)
        )
        gameAdapter.submitList(sampleGames)
    }
}