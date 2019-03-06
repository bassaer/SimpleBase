package com.github.bassaer.simplebase.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.bassaer.simplebase.R
import com.github.bassaer.simplebase.data.User
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserListFragment: Fragment(), NewUserDialogFragment.NoticeDialogListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var users: MutableList<User>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.userlist_frag, container, false)
        users = arrayListOf(User(0, "user1", 0))
        viewAdapter = UserListAdapter(users)
        viewManager = LinearLayoutManager(requireContext())
        recyclerView = root.findViewById<RecyclerView>(R.id.user_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
        }
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
        users.add(User(users.size, input, 0))
        viewAdapter.notifyDataSetChanged()
        Toast.makeText(requireContext(), getString(R.string.ok_message), Toast.LENGTH_SHORT).show()
    }

}