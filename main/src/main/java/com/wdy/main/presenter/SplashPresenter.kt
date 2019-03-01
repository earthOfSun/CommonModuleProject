package com.wdy.main.presenter


import com.wdy.common.BaseApplication
import com.wdy.common.ext.execute
import com.wdy.common.presenter.BasePresenter
import com.wdy.common.presenter.view.BaseView
import com.wdy.common.rx.BaseObserver
import com.wdy.common.rx.ExceptionHelper
import com.wdy.common.utils.JsonUtils
import com.wdy.main.APKUpdate.CheckUpdateInfo
import com.wdy.main.data.MainService
import com.wdy.main.data.impl.MainServiceImpl
import com.wdy.main.data.protocol.Version
import okhttp3.ResponseBody


class SplashPresenter : BasePresenter<BaseView>() {
  private var mainService: MainService = MainServiceImpl()

  fun checkVersion(requestMap: HashMap<String, Any>) {
    mainService.checkVersion(requestMap)
      .execute(object : BaseObserver<ResponseBody>(mView) {
        override fun onNext(t: ResponseBody) {
          val string = t.string()
          val json2NormalObject = JsonUtils.json2NormalObject<Version>(string, Version::class.java)
          if (json2NormalObject?.obj != null) {
//                        (mView as BaseView).onSuccess(json2NormalObject.obj.toString())
            val version = json2NormalObject.obj
            val checkUpdateInfo = CheckUpdateInfo(BaseApplication.context)
            val isShouldUpdate = checkUpdateInfo.checkVersion(version!!.versionCode!!)
            if (isShouldUpdate)
              checkUpdateInfo.updateApk(version)
          }
        }

        override fun onError(e: Throwable) {
          super.onError(e)
          ExceptionHelper.handleImsException(mView, e)
        }
      }, mProvider)
  }


}