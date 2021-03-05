package com.jesen.cod.gitappkotlin.network.service

import com.jesen.cod.gitappkotlin.entities.User
import com.jesen.cod.gitappkotlin.network.retrofit
import retrofit2.http.GET
import rx.Observable


interface UserApi{

    @GET("/user")
    fun getAuthenticatedUser(): Observable<User>
}

object UserService: UserApi by retrofit.create(UserApi::class.java)
