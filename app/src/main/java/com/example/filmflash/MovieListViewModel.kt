package com.example.filmflash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieListViewModel: ViewModel() {
    private val _movieList = MutableLiveData<MutableList<Movie>>()
    val movieList : LiveData<MutableList<Movie>>
        get() = _movieList

    init {
        getMovieList()
    }

    fun getMovieList() {
        MovieListAPI.retrofitService.getMovieListFromAPI(1).enqueue(object:
            Callback<MutableList<Movie>> {
            override fun onResponse(call: Call<MutableList<Movie>>, response: Response<MutableList<Movie>>) {
                _movieList.value = response.body()
            }

            override fun onFailure(call: Call<MutableList<Movie>>, t: Throwable) {
                Log.i("API", "ERROR: " + t.message)
            }

        })
    }
}