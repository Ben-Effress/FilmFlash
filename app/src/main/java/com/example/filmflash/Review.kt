package com.example.filmflash

data class Review(
    val author: String,
    val authorDetailsAvatarPath: String?,
    val authorDetailsRating: Double?,
    val content: String,
    val url: String
)
