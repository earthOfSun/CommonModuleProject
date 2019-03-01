package com.wdy.commonbusiness.data.impl


import com.wdy.commonbusiness.data.ImsService
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * 作者：RockQ on 2018/6/11
 * 邮箱：qingle6616@sina.com
 *
 * msg：
 */
class ImsServiceImpl : ImsService {


    override fun getErrorHandleInfo(malfunctionId: String): Observable<ResponseBody> {
        return ImsRepository.getErrorDetailHandle(malfunctionId)
    }

    override fun getErrorReview(malfunctionId: String): Observable<ResponseBody> {
        return ImsRepository.getErrorDetailReview(malfunctionId)
    }

    override fun getErrorDetail(malfunctionId: String): Observable<ResponseBody> {
        return ImsRepository.getErrorDetail(malfunctionId)
    }

    override fun getDevice(deviceId: String, projectId: String): Observable<ResponseBody> {
        return ImsRepository.getDevice(deviceId, projectId)
    }

    override fun submitError(isFirstSubmit: Boolean, requestMap: HashMap<String, Any>): Observable<ResponseBody> {
        return ImsRepository.submitError(isFirstSubmit, requestMap)
    }

    override fun getProject(): Observable<ResponseBody> {
        return ImsRepository.getProject()
    }

    override fun getArea(projectId: String): Observable<ResponseBody> {
        return ImsRepository.getAreas(projectId)
    }

    override fun getErrorList(requestMap: HashMap<String, Any>): Observable<ResponseBody> {
        return ImsRepository.getErrorList(requestMap)
    }


}
