package com.github.bassaer.simplebase.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.github.bassaer.simplebase.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserListFragment: Fragment(), NewUserDialogFragment.NoticeDialogListener {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.userlist_frag, container, false)
        val userListView: RecyclerView = root.findViewById(R.id.user_list)
        val fab: FloatingActionButton? = activity?.findViewById(R.id.new_user_fab)
        fab?.setOnClickListener {
            activity?.let {
                val dialog =  NewUserDialogFragment()
                dialog.setTargetFragment(this, 0)
                dialog.show(it.supportFragmentManager, tag)
            }
        }
        return root
    }
    override fun onClickPositiveButton(input: String) {
        Toast.makeText(requireContext(), input, Toast.LENGTH_SHORT).show()
    }

}