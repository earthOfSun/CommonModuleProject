package com.wdy.login.data.impl

import com.wdy.common.data.BaseService
import com.wdy.common.data.RetrofitFactory
import io.reactivex.Observable
import okhttp3.ResponseBody


/**
 *
 * msg：用户相关数据层
 */
object ImsRepository {


    fun login(requestMap: HashMap<String, Any>): Observable<ResponseBody> {
        return RetrofitFactory.instance.create(BaseService::class.java).getMapByAny("malfunction/malfunctionPageList.app", requestMap)
    }

    fun register(requestMap: HashMap<String, Any>): Observable<ResponseBody> {
        return RetrofitFactory.instance.create(BaseService::class.java).getMapByAny("malfunction/malfunctionPageList.app", requestMap)
    }

    fun forgetPS(requestMap: HashMap<String, Any>): Observable<ResponseBody> {
        return RetrofitFactory.instance.create(BaseService::class.java).getMapByAny("malfunction/malfunctionPageList.app", requestMap)
    }


}