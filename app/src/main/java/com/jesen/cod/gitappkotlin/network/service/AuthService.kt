package com.jesen.cod.gitappkotlin.network.service

import com.jesen.cod.gitappkotlin.entities.*
import com.jesen.cod.gitappkotlin.network.retrofit
import com.jesen.cod.gitappkotlin.setting.Configs
import retrofit2.Response
import retrofit2.http.*
import rx.Observable

interface AuthApi {

    @PUT("/authorizations/clients/${Configs.Account.clientId}/{fingerPrint}")
    fun createAuthorization(
        @Body req: AuthorizationReq,
        @Path("fingerPrint") fingerPrint: String = Configs.Account.fingerPrint
    ): Observable<AuthorizationRsp>


    @DELETE("/authorizations/{id}")
    fun deleteAuthorization(@Path("id") id: Int): Observable<Response<Any>>

    @POST("login/device/code")
    fun getDeviceCode(
        @Body req: VerificationCodeReq//,
        //@Query("client_id") client_id: String,
        //@Query("scope") scope: String
    )
            : Observable<VerificationCodeRsp>

    @POST("/login/oauth/access_token?")
    fun deviceAuthorization(
        @Body req: DeviceAuthorizationReq,
        @Query("client_id") client_id: String,
        @Query("device_code") device_code: String,
        @Query("grant_type") grant_type: String
    )
            : Observable<DeviceAuthorizationRsp>

}

object AuthService : AuthApi by retrofit.create(AuthApi::class.java)