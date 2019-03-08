package com.github.bassaer.simplebase.userlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.bassaer.simplebase.R
import com.github.bassaer.simplebase.data.User


class UserListAdapter(private val userList: MutableList<User>):
    RecyclerView.Adapter<UserListAdapter.UserViewHolder>(){

    private var onItemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    interface OnItemClickListener {
        fun onClick(user: User)
    }


    class UserViewHolder(cell: View): RecyclerView.ViewHolder(cell) {
        var rootView: LinearLayout = cell.findViewById(R.id.user_cell_root)
        var userView: TextView = cell.findViewById(R.id.cell_name)
        var countView: TextView = cell.findViewById(R.id.cell_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        val cell = LayoutInflater.from(parent.context).inflate(R.layout.user_cell, parent, false)
        return UserViewHolder(cell)
    }

    override fun getItemCount(): Int = userList.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.userView.text = userList[position].name
        holder.countView.text = userList[position].count.toString()
        holder.rootView.setOnClickListener {
            onItemClickListener?.onClick(userList[position])
        }
    }
}