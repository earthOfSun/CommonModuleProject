package com.wdy.login.data.impl


import com.wdy.login.data.LoginService
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * 作者：RockQ on 2018/6/11
 * 邮箱：qingle6616@sina.com
 *
 * msg：
 */
class LoginServiceImpl : LoginService {
    override fun register(requestMap: HashMap<String, Any>): Observable<ResponseBody> {
        return ImsRepository.register(requestMap)
    }

    override fun forgetPS(requestMap: HashMap<String, Any>): Observable<ResponseBody> {
        return ImsRepository.forgetPS(requestMap)
    }


    override fun login(requestMap: HashMap<String, Any>): Observable<ResponseBody> {
        return ImsRepository.login(requestMap)
    }

}
