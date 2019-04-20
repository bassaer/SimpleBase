package com.github.bassaer.simplebase.github

import android.os.Bundle
import android.util.Log
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
import com.squareup.moshi.Moshi
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class GitHubFragment : Fragment(){

    companion object {
        const val TAG = "GitHubFragment"
        const val API_URL = "https://api.github.com/"
    }

    private var repos = mutableListOf<RepoResponse>()
    private var viewAdapter: RecyclerView.Adapter<*> = GitHubAdapter(repos)
    private lateinit var recyclerView: EmptyRecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.github_flag, container, false)
        viewAdapter = GitHubAdapter(repos)
        recyclerView = root.findViewById<EmptyRecyclerView>(R.id.repo_list).apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = viewAdapter
            addItemDecoration(DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL))
            setEmptyView(root.findViewById(R.id.empty_view))
        }
        activity?.findViewById<FloatingActionButton>(R.id.reload_button)?.setOnClickListener {
            readRepos()

        }
        readRepos()
        return root
    }

    private fun readRepos() {
        client().listRepos()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<RepoResponse>> {
                override fun onComplete() {
                    viewAdapter.notifyDataSetChanged()
                }

                override fun onSubscribe(d: Disposable) {
                    Toast.makeText(requireContext(), getString(R.string.loading_message), Toast.LENGTH_SHORT).show()
                    repos.clear()
                }

                override fun onNext(list: List<RepoResponse>) {
                    for (repo in list) {
                        repos.add(repo)
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(requireContext(), getString(R.string.ng_message), Toast.LENGTH_SHORT).show()
                    Log.d(TAG, e.localizedMessage)
                }
            })
    }

    private fun client(): GitHubService {
        val moshi = Moshi.Builder().build()
        val okClient = OkHttpClient()
        val retrofit = Retrofit.Builder()
            .client(okClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(API_URL)
            .build()
        return retrofit.create(GitHubService::class.java)
    }
}