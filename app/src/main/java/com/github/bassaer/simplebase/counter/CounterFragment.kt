package com.github.bassaer.simplebase.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.bassaer.simplebase.R

class CounterFragment: Fragment() {
    private var count = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.counter_flag, container, false)
        val textView: TextView = view.findViewById(R.id.text)
        val fab: View? = activity?.findViewById(R.id.fab)
        fab?.setOnClickListener {
            textView.text = (++this.count).toString()
        }
        val userId: Int = arguments?.getInt(ARGUMENT_USER_ID) ?: 0
        Toast.makeText(requireContext(), "user -> $userId", Toast.LENGTH_SHORT).show()
        return view
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