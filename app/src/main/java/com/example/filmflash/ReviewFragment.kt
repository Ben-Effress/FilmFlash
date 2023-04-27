package com.example.filmflash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.filmflash.databinding.FragmentReviewDetailsBinding
import io.noties.markwon.Markwon

class ReviewFragment : Fragment() {

    private var _binding: FragmentReviewDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieReviewsViewModel

    private val args: ReviewFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentReviewDetailsBinding.inflate(inflater, container, false)
        val content = args.content
        val author = args.author
        (activity as AppCompatActivity).supportActionBar?.title = author + "'s Review"
        val rating = args.rating
        val avatarPath = args.avatarPath
        val markwon = Markwon.create(requireContext())
        val markdownContent = content.replace("\\r\\n", "\n")
        val spannedContent = markwon.toMarkdown(markdownContent)

        binding.apply {
            reviewItemContent.text = spannedContent
            reviewItemUsername.text = author
            reviewItemRatingBar.rating = rating
            Glide.with(requireContext()).load(avatarPath)
                .into(reviewItemProfilePicImage)
        }



        return binding.root
    }


}