package com.jesen.cod.gitappkotlin.entities

import android.os.Parcelable
import com.jesen.cod.common.anno.PoKo
import kotlinx.android.parcel.Parcelize

/**
 * [谷歌] 从 2020 年 11 月 13 日起，REST API 和 OAuth 授权 API 的用户名和密码身份验证被弃用，不再有效。
 * 以前的鉴权方式、接口、返回实体都发生了变化，{@link #User}已不符合API返回的实体，重新定义为User2.
 *
 */

@PoKo
@Parcelize
data class BasicUser2(
    var login: String,
    var avatar_url: String,
    var html_url: String
) : Parcelable


@PoKo
@Parcelize
data class User2(
    val avatar_url: String,
    val bio: String?,
    val blog: String,
    val collaborators: Int,
    val company: String?,
    val created_at: String,
    val disk_usage: Int,
    val email: String?,
    val events_url: String,
    val followers: Int,
    val followers_url: String,
    val following: Int,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val hireable: String?,
    val html_url: String,
    val id: Int,
    val location: String?,
    val login: String,
    val name: String?,
    val node_id: String,
    val organizations_url: String,
    val owned_private_repos: Int,
    val plan: Plan,
    val private_gists: Int,
    val public_gists: Int,
    val public_repos: Int,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val total_private_repos: Int,
    val twitter_username: String?,
    val two_factor_authentication: Boolean,
    val type: String,
    val updated_at: String,
    val url: String
) : Parcelable


@PoKo
@Parcelize
data class Plan(
    val collaborators: Int,
    val name: String,
    val private_repos: Int,
    val space: Int
) : Parcelable