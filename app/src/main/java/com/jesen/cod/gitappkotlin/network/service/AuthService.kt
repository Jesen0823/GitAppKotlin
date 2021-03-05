package com.jesen.cod.gitappkotlin.network.service

import com.jesen.cod.gitappkotlin.entities.AuthorizationReq
import com.jesen.cod.gitappkotlin.entities.AuthorizationRsp
import com.jesen.cod.gitappkotlin.network.retrofit
import com.jesen.cod.gitappkotlin.setting.Configs
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.PUT
import retrofit2.http.Path
import rx.Observable

interface AuthApi {

    @PUT("/authorizations/clients/${Configs.Account.clientId}/{fingerPrint}")
    fun createAuthorization(
        @Body req: AuthorizationReq,
        @Path("fingerPrint") fingerPrint: String = Configs.Account.fingerPrint
    ): Observable<AuthorizationRsp>


    @DELETE("/authorizations/{id}")
    fun deleteAuthorization(@Path("id") id: Int): Observable<Response<Any>>

}

object AuthService : AuthApi by retrofit.create(AuthApi::class.java)