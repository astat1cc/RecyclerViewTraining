package com.example.recyclerviewonmyown.screens

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewonmyown.UsersActionListener
import com.example.recyclerviewonmyown.UsersAdapter
import com.example.recyclerviewonmyown.databinding.FragmentUsersListBinding
import com.example.recyclerviewonmyown.model.User

class UsersListFragment : Fragment() {

    lateinit var binding: FragmentUsersListBinding
    lateinit var adapter: UsersAdapter

    val viewModel: UsersListViewModel by viewModels { factory() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUsersListBinding.inflate(inflater, container, false)
        adapter = UsersAdapter(object : UsersActionListener {
            override fun onUserMove(user: User, moveBy: Int) {
                viewModel.moveUser(user, moveBy)
            }

            override fun onUserDelete(user: User) {
                viewModel.deleteUser(user)
            }

            override fun onUserDetails(user: User) {
                navigator().showDetails(user)
            }
        })
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.users.observe(viewLifecycleOwner, Observer {
            adapter.users = it
        })

        return binding.root
    }
}