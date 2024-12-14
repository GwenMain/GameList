package com.example.gamelist.data

data class Game(
    val name: String,
    val description: String,
    val played: Boolean,
    val liked: Boolean,
    val disliked: Boolean
)
