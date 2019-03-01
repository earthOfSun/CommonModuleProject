package com.wdy.commonbusiness.data.impl

import com.wdy.common.data.BaseService
import com.wdy.common.data.RetrofitFactory
import io.reactivex.Observable
import okhttp3.ResponseBody


/**
 *
 * msg：用户相关数据层
 */
object ImsRepository {
    //用户注册
    fun getAreas(projectId: String): Observable<ResponseBody> {
        val requestMap = HashMap<String, Any>()
        requestMap.put("projectId", projectId)
        return RetrofitFactory.instance.create(BaseService::class.java).getMapByAny("region/tree.app", requestMap)
    }

    fun getErrorDetailHandle(malfunctionId: String): Observable<ResponseBody> {
        val requestMap = HashMap<String, Any>()
        requestMap.put("malfunctionId", malfunctionId)
        return RetrofitFactory.instance.create(BaseService::class.java).getMapByAny("malfunction/handle.app", requestMap)
    }

    fun getErrorDetailReview(malfunctionId: String): Observable<ResponseBody> {
        val requestMap = HashMap<String, Any>()
        requestMap.put("malfunctionId", malfunctionId)
        return RetrofitFactory.instance.create(BaseService::class.java).getMapByAny("malfunction/valuation.app", requestMap)
    }

    fun getErrorDetail(malfunctionId: String): Observable<ResponseBody> {
        val requestMap = HashMap<String, Any>()
        requestMap.put("malfunctionId", malfunctionId)
        return RetrofitFactory.instance.create(BaseService::class.java).getMapByAny("malfunction/malfunction.app", requestMap)
    }

    fun getProject(): Observable<ResponseBody> {
        return RetrofitFactory.instance.create(BaseService::class.java)["project/myList.app"]
    }

    fun getDevice(deviceId: String, projectId: String): Observable<ResponseBody> {
        val requestMap = HashMap<String, String>()
        requestMap.put("id", deviceId)
        requestMap.put("projectId", projectId)
        return RetrofitFactory.instance.create(BaseService::class.java).get("device/device.app", requestMap)
    }

    fun submitError(isFirstSubmit: Boolean, requestMap: HashMap<String, Any>): Observable<ResponseBody> {
        return if (!isFirstSubmit) RetrofitFactory.instance.create(BaseService::class.java).postMapByAny("malfunction/edit.app", requestMap)
        else RetrofitFactory.instance.create(BaseService::class.java).postMapByAny("malfunction/add.app", requestMap)
    }


    fun getErrorList(requestMap: HashMap<String, Any>): Observable<ResponseBody> {
        return RetrofitFactory.instance.create(BaseService::class.java).getMapByAny("malfunction/malfunctionPageList.app", requestMap)
    }

}