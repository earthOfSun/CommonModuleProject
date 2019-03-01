package com.wdy.login.data

import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 *
 * msg：用户数据操作接口
 */
interface LoginService {


    fun login(requestMap: HashMap<String, Any>): Observable<ResponseBody>
    fun register(requestMap: HashMap<String, Any>): Observable<ResponseBody>
    fun forgetPS(requestMap: HashMap<String, Any>): Observable<ResponseBody>
}