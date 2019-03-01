package com.wdy.main.data.impl

import com.wdy.main.data.MainService
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * 作者：RockQ on 2018/6/11
 * 邮箱：qingle6616@sina.com
 *
 * msg：
 */
class MainServiceImpl : MainService {
    override fun checkVersion(requestMap: HashMap<String, Any>): Observable<ResponseBody> {
        return ImsRepository.checkVersion(requestMap)
    }




}
