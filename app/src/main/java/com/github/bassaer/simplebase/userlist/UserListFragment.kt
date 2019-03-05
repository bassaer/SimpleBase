package com.github.bassaer.simplebase.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.github.bassaer.simplebase.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserListFragment: Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.userlist_frag, container, false)
        val userListView: RecyclerView = root.findViewById(R.id.user_list)
        val fab: FloatingActionButton? = activity?.findViewById(R.id.new_user_fab)
        fab?.setOnClickListener {
            val dialog =  NewUserDialogFragment()
            activity?.let {
                dialog.show(it.supportFragmentManager, "test")
            }
        }
        return root
    }
}