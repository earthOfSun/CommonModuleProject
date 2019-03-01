package com.wdy.commonbusiness.presenter


import com.wdy.common.data.domain.PageListJson
import com.wdy.common.ext.execute
import com.wdy.common.presenter.BasePresenter
import com.wdy.common.rx.BaseObserver
import com.wdy.common.rx.ExceptionHelper
import com.wdy.common.utils.JsonUtils
import com.wdy.commonbusiness.data.ImsService
import com.wdy.commonbusiness.data.impl.ImsServiceImpl
import com.wdy.commonbusiness.view.CBMMainView
import com.wdy.login.data.protdocol.Error
import okhttp3.ResponseBody

class CBMMainPresenter :BasePresenter<CBMMainView>() {
    private var imsService: ImsService = ImsServiceImpl()
    fun getErrorList(requestMap: HashMap<String, Any>){
        if (!checkNetWork()) {
            return
        }

        imsService.getErrorList(requestMap)
            .execute(object : BaseObserver<ResponseBody>(mView) {
                override fun onNext(t: ResponseBody) {
                    val string = t.string()
                    val pageListInfo: PageListJson<Error>? = JsonUtils.json2PageList(string, Error::class.java)
                    if (pageListInfo?.objs != null)
                        (mView as CBMMainView).onSuccess(pageListInfo?.objs.toString())

                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    ExceptionHelper.handleImsException(mView, e)
                }
            }, mProvider)
    }
}