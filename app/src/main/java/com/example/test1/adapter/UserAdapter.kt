package com.example.mvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.size.ViewSizeResolver
import com.example.mvvm.databinding.ItemUserBinding
import com.example.mvvm.domain.model.User

class UserAdapter(
    private val itemClick: (User) -> Unit
) : ListAdapter<User, UserViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return UserViewHolder(
            binding = ItemUserBinding.inflate(layoutInflater, parent, false),
            onUserClicked = itemClick
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: User,
                newItem: User
            ): Boolean {
                return oldItem.login == newItem.login && oldItem.avatarUrl == newItem.avatarUrl
            }
        }
    }
}

class UserViewHolder(
    private val binding: ItemUserBinding,
    private val onUserClicked: (User) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(githubUser: User) {
        with(binding) {
            binding.name.text = githubUser.login
            image.load(githubUser.avatarUrl) {
                scale(Scale.FIT)
                size(ViewSizeResolver(root))
            }
            binding.root.setOnClickListener {
                onUserClicked(githubUser)
            }
        }
    }
}