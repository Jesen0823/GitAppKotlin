package com.jesen.cod.gitappkotlin.network.service

import com.jesen.cod.gitappkotlin.entities.User
import com.jesen.cod.gitappkotlin.entities.User2
import com.jesen.cod.gitappkotlin.network.retrofit
import retrofit2.http.GET
import rx.Observable


interface UserApi{

    @Deprecated("API旧协议,2020-11-13谷歌官方废弃")
    @GET("/user")
    fun getAuthenticatedUser(): Observable<User>

    @GET("https://api.github.com/user")
    fun getAuthenticatedUser2(): Observable<User2>
}

object UserService: UserApi by retrofit.create(UserApi::class.java)
