package com.github.bassaer.simplebase.github

import io.reactivex.Observable
import retrofit2.http.GET

interface GitHubService {
    @GET("users/bassaer/repos?sort=pushed")
    fun listRepos(): Observable<List<RepoResponse>>
}