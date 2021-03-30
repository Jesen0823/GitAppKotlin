package com.jesen.cod.gitappkotlin.network.service

import com.jesen.cod.gitappkotlin.entities.User
import com.jesen.cod.gitappkotlin.entities.User2
import com.jesen.cod.gitappkotlin.network.retrofit
import com.jesen.cod.gitappkotlin.network.retrofitOld
import retrofit2.adapter.rxjava.GitHubPaging
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable


interface UserApi {

    @Deprecated("API旧协议,2020-11-13谷歌官方废弃")
    @GET("/user")
    fun getAuthenticatedUser(): Observable<User>

    @GET("/user")
    fun getAuthenticatedUser2(): Observable<User2>

    @GET("/users")
    fun allUsers(@Query("since") since: Int, @Query("per_page") per_page: Int = 20)
            : Observable<GitHubPaging<User2>>

    @GET("/users/{login}/following")
    fun following(
        @Path("login") login: String, @Query("page") page: Int = 1,
        @Query("per_page") per_page: Int = 20
    ): Observable<GitHubPaging<User2>>

    @GET("/users/{login}/followers")
    fun followers(@Path("login") login: String, @Query("page") page: Int = 1)
            : Observable<GitHubPaging<User2>>
}

object UserService : UserApi by retrofitOld.create(UserApi::class.java)
