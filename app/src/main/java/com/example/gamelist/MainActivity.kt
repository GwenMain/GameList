package com.example.gamelist

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem

import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gamelist.data.Game
import com.example.gamelist.databinding.ActivityMainBinding
import androidx.appcompat.widget.SearchView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var gameAdapter: GameAdapter
    private val allGames = mutableListOf<Game>()
    private val filteredGames = mutableListOf<Game>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar) // Ensure the toolbar is set

        gameAdapter = GameAdapter()
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = gameAdapter

        val sampleGames = listOf(
            Game("The Legend of Zelda", "An epic adventure game", true, false, true, 4.9f, 1998, "Nintendo 64"),
            Game("Super Mario Bros", "Classic platformer game", true, true, false, 4.8f, 1985, "NES"),
            Game("Minecraft", "A sandbox game", false, false, false, 4.7f, 2011, "PC, Console, Mobile"),
            Game("The Witcher 3", "RPG with great storytelling", true, true, false, 4.9f, 2015, "PC, PS4, Xbox One"),
            Game("Overwatch", "Team-based multiplayer first-person shooter", true, false, false, 4.5f, 2016, "PC, PS4, Xbox One"),
            Game("Grand Theft Auto V", "Open world action-adventure", false, true, true, 4.6f, 2013, "PC, PS4, Xbox One"),
            Game("Fortnite", "Online battle royale game", false, true, false, 4.4f, 2017, "PC, PS4, Xbox One, Switch"),
            Game("FIFA 23", "Soccer simulation game", true, false, false, 4.2f, 2023, "PC, PS4, PS5, Xbox One, Xbox Series X/S"),
            Game("League of Legends", "Multiplayer online battle arena", true, true, false, 4.7f, 2009, "PC"),
            Game("Red Dead Redemption 2", "Action-adventure in the Wild West", true, false, true, 4.9f, 2018, "PC, PS4, Xbox One"),
            Game("Cyberpunk 2077", "Open world RPG in a dystopian future", false, true, false, 4.1f, 2020, "PC, PS4, Xbox One"),
            Game("Call of Duty: Modern Warfare", "First-person shooter", false, false, true, 4.6f, 2019, "PC, PS4, Xbox One"),
            Game("Apex Legends", "Battle royale shooter", true, false, true, 4.7f, 2019, "PC, PS4, Xbox One"),
            Game("Hollow Knight", "Action-adventure Metroidvania", true, true, false, 4.8f, 2017, "PC, Switch, PS4, Xbox One"),
            Game("Dark Souls III", "Challenging action RPG", true, false, true, 4.9f, 2016, "PC, PS4, Xbox One")
        )

        allGames.addAll(sampleGames)
        filteredGames.addAll(sampleGames)
        gameAdapter.submitList(filteredGames)

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // Filtrování při stisknutí Enteru
                if (query != null) {
                    val filteredList = sampleGames.filter { game ->
                        game.name.contains(query, ignoreCase = true)
                    }
                    gameAdapter.submitList(filteredList)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filtrování při změně textu
                val filteredList = sampleGames.filter { game ->
                    game.name.contains(newText ?: "", ignoreCase = true)
                }
                gameAdapter.submitList(filteredList)
                return true
            }
        })
    }
}


