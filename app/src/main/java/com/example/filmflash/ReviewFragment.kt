package com.example.filmflash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.filmflash.databinding.FragmentReviewDetailsBinding

class ReviewFragment : Fragment() {

    private var _binding: FragmentReviewDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieReviewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReviewDetailsBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this)[MovieReviewsViewModel::class.java]



        return binding.root
    }


}