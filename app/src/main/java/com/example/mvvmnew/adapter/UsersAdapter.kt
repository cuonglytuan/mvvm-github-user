package com.example.mvvmnew.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmnew.adapter.UsersAdapter.ViewHolder
import com.example.mvvmnew.database.entity.UserEntity
import com.example.mvvmnew.databinding.ListUserAtBinding

class UsersAdapter() : PagedListAdapter<UserEntity, ViewHolder>(
    object : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean =
            oldItem == newItem
    }
) {
    class ViewHolder(val binding: ListUserAtBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ListUserAtBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position) ?: return

        holder.binding.user = user
    }
}