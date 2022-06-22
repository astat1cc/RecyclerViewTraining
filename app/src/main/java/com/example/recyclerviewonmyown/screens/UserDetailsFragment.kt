package com.example.recyclerviewonmyown.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.recyclerviewonmyown.R
import com.example.recyclerviewonmyown.databinding.FragmentUsersDetailsBinding
import com.example.recyclerviewonmyown.model.User


class UserDetailsFragment : Fragment() {

    lateinit var binding: FragmentUsersDetailsBinding

    private val viewModel: UserDetailsViewModel by viewModels { factory() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadUser(requireArguments().getInt(ARG_USER_ID))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsersDetailsBinding.inflate(inflater, container, false)
        viewModel.user.observe(viewLifecycleOwner, Observer {
            with(binding) {
                nameTV.text = it.name
                detailsTV.text = it.details
                if (it.photo.isNotBlank()) {
                    Glide.with(photoIV.context)
                        .load(it.photo)
                        .placeholder(R.drawable.ic_avatar_circle)
                        .error(R.drawable.ic_avatar_circle)
                        .into(photoIV)
                } else {
                    Glide.with(photoIV.context)
                        .load(R.drawable.ic_avatar_circle)
                        .into(photoIV)
                }
            }
        })

        return binding.root
    }

    companion object {

        const val ARG_USER_ID = "ARG_USER_ID"

        fun newInstance(user: User): UserDetailsFragment {
            val fragment = UserDetailsFragment()
            fragment.arguments = bundleOf(ARG_USER_ID to user.id)
            return fragment
        }
    }
}