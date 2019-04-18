package com.github.bassaer.simplebase.github

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
import com.github.bassaer.simplebase.util.EmptyRecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class GithubFragment : Fragment(){

    private lateinit var recyclerView: EmptyRecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.github_flag, container, false)
        val repos = mutableListOf("repo1", "repo2", "repo3")
        viewAdapter = GitHubAdapter(repos)
        recyclerView = root.findViewById<EmptyRecyclerView>(R.id.repo_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            setEmptyView(root.findViewById(R.id.empty_view))
        }
        activity?.findViewById<FloatingActionButton>(R.id.reload_button)?.setOnClickListener {
            Toast.makeText(requireContext(), "Searching ...", Toast.LENGTH_SHORT).show()
        }

        return root
    }
}