package com.app.demomvimaster.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.app.demomvimaster.data.model.User
import com.app.demomvimaster.databinding.ItemLayoutBinding


class UserAdapter: ListAdapter<User, UserAdapter.UserViewHolder>(Companion) {
    class UserViewHolder(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    companion object: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem === newItem
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemLayoutBinding.inflate(layoutInflater)

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val currentUser = getItem(position)
        Log.e("MainActivity", currentUser.toString())
        holder.binding.user = currentUser
        holder.binding.executePendingBindings()
    }
}