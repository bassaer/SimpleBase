package com.github.bassaer.simplebase.github

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.bassaer.simplebase.R

class GitHubAdapter(private val repos: MutableList<RepoResponse>): RecyclerView.Adapter<GitHubAdapter.RepoViewHolder>() {

    class RepoViewHolder(cell: View): RecyclerView.ViewHolder(cell) {
        var repoName: TextView = cell.findViewById(R.id.repo_name)
        var repoLanguage: TextView = cell.findViewById(R.id.repo_language)
        var repoStarts: TextView = cell.findViewById(R.id.repo_stars)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val cell = LayoutInflater.from(parent.context).inflate(R.layout.repo_cell, parent, false)
        return RepoViewHolder(cell)
    }

    override fun getItemCount() = repos.size

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        holder.repoName.text = repos[position].name
        holder.repoLanguage.text = repos[position].language
        holder.repoStarts.text = repos[position].star.toString()
    }
}