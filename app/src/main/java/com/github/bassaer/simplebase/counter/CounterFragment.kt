package com.github.bassaer.simplebase.counter

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.github.bassaer.simplebase.R
import com.github.bassaer.simplebase.data.User
import com.github.bassaer.simplebase.data.UserDatabase

class CounterFragment: Fragment() {
    lateinit var user: User

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.counter_flag, container, false)
        val textView: TextView = view.findViewById(R.id.text)
        val userId: Long? = arguments?.getLong(ARGUMENT_USER_ID)
        if (userId == null) {
            Toast.makeText(requireContext(), getString(R.string.ng_message), Toast.LENGTH_SHORT).show()
            activity?.finish()
        }

        val dao = UserDatabase.getInstance(requireContext()).userDao()
        user = dao.findById(userId!!)

        Toast.makeText(requireContext(), "userid = ${user.id}", Toast.LENGTH_SHORT).show()

        textView.text = user.count.toString()

        val fab: View? = activity?.findViewById(R.id.fab)
        fab?.setOnClickListener {
            user.count += 1
            textView.text = (user.count).toString()
        }
        activity?.addOnBackPressedCallback(this, object : OnBackPressedCallback {
            override fun handleOnBackPressed(): Boolean {
                val indent = Intent()
                indent.putExtra(ARGUMENT_USER_ID, user.id)
                activity?.setResult(RESULT_OK, indent)
                activity?.finish()
                return true
            }

        })
        return view
    }

    override fun onPause() {
        super.onPause()
        val dao = UserDatabase.getInstance(requireContext()).userDao()
        dao.save(user)
    }


    companion object {
        const val ARGUMENT_USER_ID = "USER_ID"
        fun newInstance(userId: Int) =
            CounterFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARGUMENT_USER_ID, userId)
                }
            }
    }
}