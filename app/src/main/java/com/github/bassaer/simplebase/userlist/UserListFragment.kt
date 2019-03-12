package com.github.bassaer.simplebase.userlist

import android.content.Intent
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
import com.github.bassaer.simplebase.counter.CounterActivity
import com.github.bassaer.simplebase.counter.CounterFragment
import com.github.bassaer.simplebase.data.User
import com.github.bassaer.simplebase.data.UserDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserListFragment: Fragment(), NewUserDialogFragment.NoticeDialogListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var users: MutableList<User>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.userlist_frag, container, false)
        val userDao = UserDatabase.getInstance(requireContext()).userDao()
        users = userDao.findAll()
        viewManager = LinearLayoutManager(requireContext())
        viewAdapter = UserListAdapter(users).apply {
            setOnItemClickListener(object : UserListAdapter.OnItemClickListener {
                override fun onClick(user: User) {
                    val intent = Intent(requireContext(), CounterActivity::class.java)
                    intent.putExtra(CounterFragment.ARGUMENT_USER_ID, user.id)
                    startActivity(intent)
                }
            })
        }
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
        val dao = UserDatabase.getInstance(requireContext()).userDao()
        dao.create(User(name = input, count = 0))
        viewAdapter.notifyDataSetChanged()
        Toast.makeText(requireContext(), getString(R.string.ok_message), Toast.LENGTH_SHORT).show()
    }

}