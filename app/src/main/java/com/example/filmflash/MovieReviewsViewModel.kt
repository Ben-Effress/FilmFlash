package com.example.filmflash

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import io.noties.markwon.Markwon


class MovieReviewsViewModel(val application: Application, private val movieID: Int): ViewModel() {
    private val _reviewsList = MutableLiveData<MutableList<Review>>()
    val reviewList: LiveData<MutableList<Review>>
        get() = _reviewsList

    init {
        getReviewsList()
    }

    fun getReviewsList() {
        MovieReviewsAPI.retrofitService.getMovieReviewsFromAPI(movieID).enqueue(object :
            Callback<MutableList<Review>> {
            override fun onResponse(
                call: Call<MutableList<Review>>,
                response: Response<MutableList<Review>>
            ) {
                val reviews = response.body()
                val markwon = Markwon.create(application)
                reviews?.forEach{review: Review ->
                    val reviewContent = review.content
                    val markdownContent = reviewContent.replace("\\r\\n", "\n")
                    val spannedContent = markwon.toMarkdown(markdownContent)
                    review.formattedContent = spannedContent
                }
                _reviewsList.value = reviews?.toMutableList()
            }

            override fun onFailure(call: Call<MutableList<Review>>, t: Throwable) {
                Log.i("API", "ERROR: " + t.message)
            }

        })
    }
}