package com.github.bassaer.simplebase.counter

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.github.bassaer.simplebase.R
import com.github.bassaer.simplebase.data.User
import com.github.bassaer.simplebase.data.UserDatabase

class CounterFragment: Fragment() {

    lateinit var user: User
    lateinit var textView: TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.counter_flag, container, false)
        textView = view.findViewById(R.id.text)
        val userId: String = arguments?.getString(ARGUMENT_USER_ID) ?: return view
        val dao = UserDatabase.getInstance(requireContext()).userDao()
        user = dao.findById(userId)

        textView.text = user.count.toString()

        val fab: View? = activity?.findViewById(R.id.fab)
        fab?.setOnClickListener {
            user.count += 1
            textView.text = (user.count).toString()
        }
        activity?.onBackPressedDispatcher?.addCallback(this, OnBackPressedCallback {
            val indent = Intent()
            indent.putExtra(ARGUMENT_USER_ID, user.id)
            activity?.setResult(RESULT_OK, indent)
            activity?.finish()
            true
        })
        return view
    }

    override fun onPause() {
        super.onPause()
        val dao = UserDatabase.getInstance(requireContext()).userDao()
        dao.save(user)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset -> {
                user.count = 0
                textView.text = user.count.toString()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val ARGUMENT_USER_ID = "USER_ID"
        fun newInstance(userId: String) =
            CounterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARGUMENT_USER_ID, userId)
                }
            }
    }
}