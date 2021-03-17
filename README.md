# GitAppKotlin

kotlin project

#### 纯Kotlin语言开发，基于github提供的最新API做了app授权登录以及其他API的使用

##### **谷歌官方**：
*从 2020 年 11 月 13 日起，REST API 和 OAuth 授权 API 的用户名和密码身份验证被弃用，不再有效。*

##### GitHub API 官方文档：<br />

 1.oauth-app鉴权相关：
 [https://docs.github.com/en/free-pro-team@latest/developers/apps/authorizing-oauth-apps#device-flow](https://docs.github.com/en/free-pro-team@latest/developers/apps/authorizing-oauth-apps#device-flow)

 2.oauth-app创建：
 [https://docs.github.com/en/developers/apps/creating-an-oauth-app](https://docs.github.com/en/developers/apps/creating-an-oauth-app)

##### 通过Device flow获取token，主要分为3步：<br />

 **1**、*通过POST接口https://github.com/login/device/code获得device_code、user_code、verification_uri，  
 请求参数为client_id、scope*

 **2**、*WebView打开verification_uri，登录后，输入user_code*

 **3**、*通过POST接口https://github.com/login/oauth/access_token获得access_token、token_type，请求参数  
 为第1步的device_code，以及Oauth app申请的client_id，还有grant_type(固定值，urn\:ietf\:params\:oauth:  
 grant-type:device_code)*

##### 以上步骤拿到token并保存，接下来获取用户信息：<br />

###### 1) 请求地址

>https://api.github.com/user

###### 2) 调用方式：HTTP GET

###### 3) 请求参数（没有额外参数，只有请求头）:

```
Request Header
Content-Type:application/json;
Authorization:token 2c0165433ab95019b328339da6e99dbf85dd900;
```

###### 4) 请求返回结果:

```
{
    "login": "Jesen0823",
    "id": 00436771,
    "node_id": "MDQ6VXNlcjM2NuGTFNzcx",
   ... ...
    "collaborators": 0,
    "two_factor_authentication": false,
    "plan": {
        "name": "free",
        "space": 0006562499,
        "collaborators": 0,
        "private_repos": 10000
    }
}
```