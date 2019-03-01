package com.wdy.commonbusiness.data

import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 *
 * msg：用户数据操作接口
 */
interface ImsService {
    //用户登陆
    fun getArea(projectId: String): Observable<ResponseBody>

    fun getErrorDetail(malfunctionId: String): Observable<ResponseBody>

    fun getErrorHandleInfo(malfunctionId: String): Observable<ResponseBody>

    fun getErrorReview(malfunctionId: String): Observable<ResponseBody>

    fun getProject(): Observable<ResponseBody>

    fun getDevice(deviceId: String, projectId: String): Observable<ResponseBody>

    fun submitError(isFirstSubmit: Boolean, requestMap: HashMap<String, Any>): Observable<ResponseBody>

    fun getErrorList(requestMap: HashMap<String, Any>): Observable<ResponseBody>
}