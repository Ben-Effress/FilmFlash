package com.example.filmflash

import com.squareup.moshi.Json

data class Movie
    (val adult: Boolean,
     @Json(name = "backdrop_path") val backdropPath: String?,
     @Json(name = "genre_ids") val genreIds: List<Int>,
     val id: Int,
     @Json(name = "original_language") val originalLanguage: String,
     @Json(name = "original_title") val originalTitle: String,
     val overview: String?,
     @Json(name = "poster_path") val posterPath: String?,
     @Json(name = "release_date") val releaseDate: String?,
     val title: String,
     @Json(name = "vote_average") val voteAverage: Double,
     @Json(name = "vote_count") val voteCount: Int)