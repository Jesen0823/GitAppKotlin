package com.jesen.cod.gitappkotlin.network.service

import com.jesen.cod.gitappkotlin.entities.*
import com.jesen.cod.gitappkotlin.network.retrofit
import com.jesen.cod.gitappkotlin.setting.Configs
import retrofit2.Response
import retrofit2.http.*
import rx.Observable

interface AuthApi {
    // 针对旧API（https://api.github.com）的鉴权，已弃用
    @PUT("/authorizations/clients/${Configs.Account.clientId}/{fingerPrint}")
    fun createAuthorization(
        @Body req: AuthorizationReq,
        @Path("fingerPrint") fingerPrint: String = Configs.Account.fingerPrint
    ): Observable<AuthorizationRsp>


    @DELETE("/authorizations/{id}")
    fun deleteAuthorization(@Path("id") id: Int): Observable<Response<Any>>

    /**
     * 第一步，获取device_code,verification_uri
     * 新API(https://github.com/)
     * */
    @POST("login/device/code")
    fun getDeviceCode(
        @Body req: VerificationCodeReq//,
        //@Query("client_id") client_id: String,
        //@Query("scope") scope: String
    ): Observable<VerificationCodeRsp>

    /**
     * 第二步，获取token
     * 新API(https://github.com/)
     * */
    @POST("login/oauth/access_token")
    fun deviceAuthorization(
        @Body req: DeviceAuthorizationReq
    ): Observable<DeviceAuthorizationRsp>
}

object AuthService : AuthApi by retrofit.create(AuthApi::class.java)