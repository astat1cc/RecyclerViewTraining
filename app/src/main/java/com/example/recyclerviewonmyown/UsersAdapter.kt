package com.example.recyclerviewonmyown

import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recyclerviewonmyown.databinding.ItemUserBinding
import com.example.recyclerviewonmyown.model.User

interface UsersActionListener {
    fun onUserMove(user: User, moveBy: Int)

    fun onUserDelete(user: User)

    fun onUserDetails(user: User)
}

class UsersAdapter(
    private val actionListener: UsersActionListener
) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>(), View.OnClickListener {

    var users = emptyList<User>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class UserViewHolder(
        val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {
        val user = v.tag as User
        when (v.id) {
            R.id.moreIV -> showPopupMenu(v)
            else -> actionListener.onUserDetails(user)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        binding.moreIV.setOnClickListener(this)
        binding.root.setOnClickListener(this)

        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = users[position]
        with(holder.binding) {
            nameTV.text = user.name
            companyTV.text = user.company
            if (user.photo.isNotBlank()) {
                Glide.with(photoIV.context)
                    .load(user.photo)
                    .circleCrop()
                    .placeholder(R.drawable.ic_avatar_circle)
                    .error(R.drawable.ic_avatar_circle)
                    .into(photoIV)
            } else {
                photoIV.setImageResource(R.drawable.ic_avatar_circle)
            }
            holder.itemView.tag = user
            moreIV.tag = user
        }
    }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(v.context, v)
        val user = v.tag as User
        val position = users.indexOfFirst { it.id == user.id }
        popupMenu.menu.add(0, MOVE_UP, Menu.NONE, "Move up").apply {
            isEnabled = position > 0
        }
        popupMenu.menu.add(0, MOVE_DOWN, Menu.NONE, "Move down").apply {
            isEnabled = position < users.size - 1
        }
        popupMenu.menu.add(0, DELETE_USER, Menu.NONE, "Delete")

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                MOVE_UP -> actionListener.onUserMove(user, -1)
                MOVE_DOWN -> actionListener.onUserMove(user, 1)
                DELETE_USER -> actionListener.onUserDelete(user)
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    override fun getItemCount(): Int = users.size

    companion object {
        const val MOVE_UP = 1
        const val MOVE_DOWN = 2
        const val DELETE_USER = 3
    }
}