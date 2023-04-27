package com.example.filmflash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.filmflash.databinding.FragmentReviewDetailsBinding
import io.noties.markwon.Markwon

class ReviewDetailsFragment : Fragment() {

    private var _binding: FragmentReviewDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MovieReviewsViewModel

    private val args: ReviewDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as AppCompatActivity).supportActionBar?.title =
            (activity as AppCompatActivity).getString(R.string.review_details)
        // Inflate the layout for this fragment
        _binding = FragmentReviewDetailsBinding.inflate(inflater, container, false)
        val content = args.content
        val author = args.author
        val rating = args.rating
        val avatarPath = args.avatarPath
        val markwon = Markwon.create(requireContext())
        val markdownContent = content.replace("\\r\\n", "\n")
        val spannedContent = markwon.toMarkdown(markdownContent)

        binding.apply {
            reviewItemContent.text = spannedContent
            reviewItemUsername.text = author
            Glide.with(requireContext()).load(avatarPath)
                .into(reviewItemProfilePicImage)
        }
        if (rating < 0) {  // rating was null
            binding.reviewItemRatingBar.visibility = View.GONE
        } else {
            binding.reviewItemRatingBar.rating = rating / 2f
        }

        return binding.root
    }
}