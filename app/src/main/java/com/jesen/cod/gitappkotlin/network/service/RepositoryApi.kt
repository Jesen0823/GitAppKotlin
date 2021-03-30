package com.jesen.cod.gitappkotlin.network.service

import com.jesen.cod.gitappkotlin.entities.Repository
import com.jesen.cod.gitappkotlin.entities.SearchRepositories
import com.jesen.cod.gitappkotlin.network.retrofit
import com.jesen.cod.gitappkotlin.network.retrofitOld
import retrofit2.adapter.rxjava.GitHubPaging
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

interface RepositoryApi {

    @GET("/users/{owner}/repos?type=all")
    fun listRepositoriesOfUser(@Path("owner") owner: String, @Query("page") page: Int = 1, @Query("per_page") per_page: Int = 20): Observable<GitHubPaging<Repository>>

    @GET("/search/repositories?order=desc&sort=updated")
    fun allRepositories(@Query("page") page: Int = 1, @Query("q") q: String, @Query("per_page") per_page: Int = 20): Observable<SearchRepositories>

    // TODO add a Constant for "forceNetwork"
    @GET("/repos/{owner}/{repo}")
    fun getRepository(@Path("owner") owner: String, @Path("repo") repo: String, @Query("forceNetwork") forceNetwork: Boolean = false): Observable<Repository>
}

object RepositoryService : RepositoryApi by retrofitOld.create(RepositoryApi::class.java)