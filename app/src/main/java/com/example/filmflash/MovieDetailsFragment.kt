package com.example.filmflash

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.filmflash.databinding.FragmentMovieDetailsBinding

class MovieDetailsFragment : Fragment() {

    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    private var movieId = 0

    private lateinit var viewModel: MovieDetailsViewModel
    private lateinit var reviewsViewModel: MovieReviewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title =
            (activity as AppCompatActivity).getString(R.string.movie_details)
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        arguments?.let {
            movieId = it.getInt("movieId")
        }

        viewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
        viewModel.getMovieInfo(movieId)

        reviewsViewModel = ViewModelProvider(this)[MovieReviewsViewModel::class.java]
        reviewsViewModel.getReviewsList(requireContext(), movieId)

        viewModel.movieInfo.observe(viewLifecycleOwner, Observer {
            binding.apply {
                movieTitle.text = it.title
                movieRatingBar.rating = (it.vote_average / 2).toFloat()
                if (it.backdrop_path == null) {
                    movieBackdrop.setImageResource(R.drawable.logo_horizontal)
                } else {
                    val backdropPath =
                        "https://image.tmdb.org/t/p/original/" + it.backdrop_path.toString()
                    Glide.with(requireContext()).load(backdropPath).into(movieBackdrop)
                }
                val hours = it.runtime / 60
                val mins = it.runtime % 60
                movieLength.text = hours.toString() + "h " + mins.toString() + "m"
                if (it.adult) {
                    movieAdult.text = "Yes"
                } else {
                    movieAdult.text = "No"
                }
                movieRating.text = String.format("%.1f", it.vote_average) + "/10"
                movieVotes.text = it.vote_count.toString() + " votes"
                if (it.tagline.isNullOrBlank()) {
                    movieTagline.visibility = View.GONE
                } else {
                    movieTagline.text = it.tagline
                }
                movieReleaseDate.text = it.release_date
                var genreText = ""
                for ((index, genre) in it.genres.withIndex()) {
                    genreText += genre.name
                    if (index != it.genres.size - 1) {
                        genreText += ", "
                    }
                }
                movieGenres.text = genreText
                movieOverview.text = it.overview
                var languageText = ""
                for ((index, language) in it.spoken_languages.withIndex()) {
                    languageText += language.english_name
                    if (index != it.spoken_languages.size - 1) {
                        languageText += ", "
                    }
                }
                movieLanguages.text = languageText
            }
        })

        reviewsViewModel.reviewList.observe(viewLifecycleOwner, Observer {
            binding.apply {
                reviewsTitle.text = "Reviews (" + it.size.toString() + ")"
                val reviewRV = reviewListRv
                if (it.size > 0) {
                    noReviewsText.visibility = View.GONE
                    reviewRV.visibility = View.VISIBLE
                }
                it?.let {
                    val adapter = MovieReviewsAdapter(it)
                    reviewRV.adapter = adapter
                    adapter.setOnItemClickListener(object :
                        MovieReviewsAdapter.OnItemClickListener {
                        override fun onItemClick(itemView: View?, position: Int) {
                            val content = reviewsViewModel.reviewList.value!![position].content
                            val author = reviewsViewModel.reviewList.value!![position].author
                            val rating =
                                reviewsViewModel.reviewList.value!![position].authorDetails.rating.toFloat()
                            val avatarPath =
                                reviewsViewModel.reviewList.value!![position].authorDetails.avatarPath
                            val action =
                                MovieDetailsFragmentDirections.actionMovieDetailsFragmentToReviewDetailsFragment(
                                    content,
                                    author,
                                    rating,
                                    avatarPath
                                )
                            findNavController().navigate(action)
                        }
                    })
                }
            }
        })

        binding.shareButton.setOnClickListener {
            shareMovie()
        }
        return binding.root
    }

    private fun shareMovie() {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.type = "text/plain"
        shareIntent.putExtra(
            Intent.EXTRA_TEXT, getString(
                R.string.share_template,
                binding.movieTitle.text,
                binding.movieReleaseDate.text
            )
        )
        try {
            startActivity(Intent.createChooser(shareIntent, getString(R.string.share_prompt)))
        } catch (e: Exception) {
            Toast.makeText(context, getString(R.string.share_error), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}