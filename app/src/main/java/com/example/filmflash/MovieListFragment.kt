package com.example.filmflash

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.filmflash.databinding.FragmentMovieListBinding

class MovieListFragment : Fragment() {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[MovieListViewModel::class.java]


        Log.i("Movie List", viewModel.movieList.toString())
        viewModel.movieList.observe(viewLifecycleOwner, Observer { movieList ->
            val recyclerView = binding.movieOverviewRv
            movieList?.let {
                val adapter = MovieListAdapter(it)
                recyclerView.adapter = adapter
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}