package com.example.gamelist

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gamelist.data.Game
import com.example.gamelist.databinding.ActivityGameDetailBinding

class GameDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameDetailBinding
    private lateinit var sharedPreferences: SharedPreferences

    // Track the selected state of the recommendation buttons
    private var isRecommended: Boolean = false
    private var isNotRecommended: Boolean = false
    private var isPlayed: Boolean = false // Track whether the game is "played" or "not played"
    private lateinit var gameName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("gamePrefs", MODE_PRIVATE)

        // Retrieve the game details from the intent
        val game = intent.getParcelableExtra<Game>("game")

        game?.let {
            gameName = it.name
            // Set game details in the UI
            binding.gameName.text = it.name
            binding.gameDescription.text = it.description

            // Load saved state from SharedPreferences
            loadGameState()

            // Set actions for the "Hráno/Nehráno" buttons
            binding.playedButton.setOnClickListener {
                isPlayed = true
                saveGameState()
                updateButtonStates()
            }

            binding.notPlayedButton.setOnClickListener {
                isPlayed = false
                // Reset recommendations when "Nehráno" is selected
                resetRecommendationButtons()
                saveGameState()
                updateButtonStates()
            }

            // Set actions for the recommendation buttons
            binding.recommendButton.setOnClickListener {
                if (isPlayed) {
                    isRecommended = true
                    isNotRecommended = false
                    saveGameState()
                    updateButtonStyles()
                }
            }

            binding.notRecommendButton.setOnClickListener {
                if (isPlayed) {
                    isNotRecommended = true
                    isRecommended = false
                    saveGameState()
                    updateButtonStyles()
                }
            }

            // Set action for the "Back to Main" button
            binding.backToMainButton.setOnClickListener {
                val intent = Intent(this@GameDetailActivity, MainActivity::class.java)
                startActivity(intent)
                finish() // Optional, to ensure GameDetailActivity is finished when returning
            }
        }
    }

    private fun loadGameState() {
        // Load the saved state from SharedPreferences
        isPlayed = sharedPreferences.getBoolean("$gameName-played", false)
        isRecommended = sharedPreferences.getBoolean("$gameName-recommended", false)
        isNotRecommended = sharedPreferences.getBoolean("$gameName-notRecommended", false)

        updateButtonStates()
    }

    private fun saveGameState() {
        // Save the current state to SharedPreferences
        val editor = sharedPreferences.edit()
        editor.putBoolean("$gameName-played", isPlayed)
        editor.putBoolean("$gameName-recommended", isRecommended)
        editor.putBoolean("$gameName-notRecommended", isNotRecommended)
        editor.apply()
    }

    private fun updateButtonStates() {
        // Enable/Disable recommendation buttons based on "Hráno" state
        binding.recommendButton.isEnabled = isPlayed
        binding.notRecommendButton.isEnabled = isPlayed

        // Update the "Hráno" and "Nehráno" buttons to show the correct active state
        if (isPlayed) {
            binding.playedButton.setBackgroundColor(getColor(android.R.color.holo_green_light))
            binding.notPlayedButton.setBackgroundColor(getColor(android.R.color.darker_gray))
        } else {
            binding.playedButton.setBackgroundColor(getColor(android.R.color.darker_gray))
            binding.notPlayedButton.setBackgroundColor(getColor(android.R.color.holo_red_light))
        }

        // Update the button styles for recommendations
        updateButtonStyles()
    }

    private fun updateButtonStyles() {
        // Update the recommendation button styles to reflect their selected state
        if (isRecommended) {
            binding.recommendButton.setBackgroundColor(getColor(android.R.color.holo_green_light))
            binding.recommendButton.setTextColor(getColor(android.R.color.white))
        } else {
            binding.recommendButton.setBackgroundColor(getColor(android.R.color.darker_gray))
            binding.recommendButton.setTextColor(getColor(android.R.color.black))
        }

        if (isNotRecommended) {
            binding.notRecommendButton.setBackgroundColor(getColor(android.R.color.holo_red_light))
            binding.notRecommendButton.setTextColor(getColor(android.R.color.white))
        } else {
            binding.notRecommendButton.setBackgroundColor(getColor(android.R.color.darker_gray))
            binding.notRecommendButton.setTextColor(getColor(android.R.color.black))
        }
    }

    private fun resetRecommendationButtons() {
        // Reset the recommendation buttons when "Nehráno" is selected
        isRecommended = false
        isNotRecommended = false
        updateButtonStyles()
    }
}
