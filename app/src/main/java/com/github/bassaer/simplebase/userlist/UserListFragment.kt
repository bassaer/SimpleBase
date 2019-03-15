package com.github.bassaer.simplebase.userlist

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.*
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
import com.github.bassaer.simplebase.util.EmptyRecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class UserListFragment: Fragment(), NewUserDialogFragment.NoticeDialogListener {

    private lateinit var recyclerView: EmptyRecyclerView
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
                    startActivityForResult(intent, COUNTER_REQUEST)
                }
            })
        }
        recyclerView = root.findViewById<EmptyRecyclerView>(R.id.user_list).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            setEmptyView(root.findViewById(R.id.empty_view))
        }
        activity?.findViewById<FloatingActionButton>(R.id.new_user_fab)?.setOnClickListener {
            activity?.let {
                val dialog =  NewUserDialogFragment()
                dialog.setTargetFragment(this, 0)
                dialog.show(it.supportFragmentManager, tag)
            }
        }
        return root
    }

    override fun onClickPositiveButton(input: String) {
        if (input.isEmpty()) {
            Toast.makeText(requireContext(), getText(R.string.ng_message), Toast.LENGTH_SHORT).show()
            return
        }
        val dao = UserDatabase.getInstance(requireContext()).userDao()
        val user = User(name = input, count = 0)
        dao.create(user)
        users.add(user)
        viewAdapter.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != COUNTER_REQUEST || resultCode != RESULT_OK) {
            return
        }
        data?.extras?.let {
            val id = it.getString(CounterFragment.ARGUMENT_USER_ID) ?: return
            val userDao = UserDatabase.getInstance(requireContext()).userDao()
            val updateUser = userDao.findById(id)

            for ((index, user) in users.withIndex()) {
                if (updateUser.id == user.id) {
                    users[index] = updateUser
                    viewAdapter.notifyItemChanged(index)
                    break
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset -> {
                val dao = UserDatabase.getInstance(requireContext()).userDao()
                dao.removeAll()
                users.clear()
                viewAdapter.notifyDataSetChanged()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val COUNTER_REQUEST = 0
    }
}