package com.wdy.main.data


import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 *
 * msg：用户数据操作接口
 */
interface MainService {

    fun checkVersion(requestMap: HashMap<String, Any>): Observable<ResponseBody>
}