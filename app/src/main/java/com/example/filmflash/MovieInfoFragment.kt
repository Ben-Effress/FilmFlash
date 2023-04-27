package com.example.filmflash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.filmflash.databinding.FragmentMovieInfoBinding

class MovieInfoFragment : Fragment() {

    private var _binding: FragmentMovieInfoBinding? = null
    private val binding get() = _binding!!
    private var movieID = 0

    private lateinit var viewModel: MovieInfoViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieInfoBinding.inflate(inflater, container, false)
        arguments?.let {
            movieID = it.getInt("movieID")
        }

        viewModel = ViewModelProvider(this)[MovieInfoViewModel::class.java]
        viewModel.getMovieInfo(movieID)
        val textView = binding.textView
        textView.text = movieID.toString()
        viewModel.movieInfo.observe(this, Observer {
            textView.text = it.title
        })
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}