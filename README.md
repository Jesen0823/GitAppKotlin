# GitAppKotlin

kotlin project

#### 基于github提供的API做的app开发

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