package com.example.filmflash

import android.text.Spanned
data class Review(
    val author: String,
    val authorDetailsAvatarPath: String?,
    val authorDetailsRating: Double?,
    val content: String,
    var formattedContent: Spanned? = null,
    var contentPreview: String = ""
)
