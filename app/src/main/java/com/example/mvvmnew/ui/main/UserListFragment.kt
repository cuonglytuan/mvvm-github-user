package com.example.mvvmnew.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mvvmnew.R
import com.example.mvvmnew.adapter.UsersAdapter
import com.example.mvvmnew.databinding.FragmentUserListBinding
import com.wada811.databindingktx.dataBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserListFragment : Fragment(R.layout.fragment_user_list) {

    private val binding: FragmentUserListBinding by dataBinding()
    private val viewModel: UserListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getUsers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel

        setupAdapter()
    }

    private fun setupAdapter() {

        val ordersAdapter = UsersAdapter()

        binding.orderList.adapter = ordersAdapter

        viewModel.userItems.observe(viewLifecycleOwner) {
            ordersAdapter.submitList(it)
        }
    }

    companion object {
        fun newInstance() = UserListFragment()
    }

}