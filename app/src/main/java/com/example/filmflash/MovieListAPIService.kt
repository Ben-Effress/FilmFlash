package com.example.filmflash

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Path

private const val BASE_URL = "https://api.themoviedb.org/3/movie/"

private const val API_KEY = "4681e090d7dce827b558f805c05e0c8a"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MovieListAPIService {
    @GET("now_playing?api_key=$API_KEY&page={pageNumber}")
    fun getMovieListFromAPI(@Path("pageNumber") pageNumber: Int):
            Call<MutableList<Movie>>
}

object MovieListAPI {
    val retrofitService : MovieListAPIService by lazy {
        retrofit.create(MovieListAPIService::class.java)
    }
}