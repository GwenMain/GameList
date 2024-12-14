package com.example.gamelist.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val name: String,
    val description: String,
    val played: Boolean,
    val liked: Boolean,
    val disliked: Boolean,
    val rating: Float,
    val releaseYear: Int,
    val platform: String
) : Parcelable
