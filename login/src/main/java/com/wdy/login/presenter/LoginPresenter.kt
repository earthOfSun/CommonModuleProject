package com.wdy.login.presenter




import com.wdy.common.data.domain.PageListJson
import com.wdy.common.ext.execute
import com.wdy.common.presenter.BasePresenter
import com.wdy.common.rx.BaseObserver
import com.wdy.common.rx.ExceptionHelper
import com.wdy.common.utils.JsonUtils
import com.wdy.login.data.LoginService
import com.wdy.login.data.impl.LoginServiceImpl
import com.wdy.login.data.protocol.User
import com.wdy.login.view.LoginView
import okhttp3.ResponseBody

class LoginPresenter :BasePresenter<LoginView>() {
    private var loginService: LoginService = LoginServiceImpl()
    /**
     * 登录
     */
    fun login(requestMap: HashMap<String, Any>){
        if (!checkNetWork()) {
            return
        }
        loginService.login(requestMap)
            .execute(object : BaseObserver<ResponseBody>(mView) {
                override fun onNext(t: ResponseBody) {
                    val string = t.string()
                    val pageListInfo: PageListJson<User>? = JsonUtils.json2PageList(string, User::class.java)
                    if (pageListInfo?.objs != null)
                        (mView as LoginView).onSuccess(pageListInfo?.objs.toString())

                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    ExceptionHelper.handleImsException(mView, e)
                }
            }, mProvider)
    }
    /**
     * 忘记密码
     */
    fun forgetPS(requestMap: HashMap<String, Any>){
        if (!checkNetWork()) {
            return
        }
        loginService.forgetPS(requestMap)
            .execute(object : BaseObserver<ResponseBody>(mView) {
                override fun onNext(t: ResponseBody) {
                    val string = t.string()
                    val pageListInfo: PageListJson<User>? = JsonUtils.json2PageList(string, User::class.java)
                    if (pageListInfo?.objs != null)
                        (mView as LoginView).onSuccess(pageListInfo?.objs.toString())

                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    ExceptionHelper.handleImsException(mView, e)
                }
            }, mProvider)
    }
    /**
     * 注册
     */
    fun register(requestMap: HashMap<String, Any>){
        if (!checkNetWork()) {
            return
        }
        loginService.register(requestMap)
            .execute(object : BaseObserver<ResponseBody>(mView) {
                override fun onNext(t: ResponseBody) {
                    val string = t.string()
                    val pageListInfo: PageListJson<User>? = JsonUtils.json2PageList(string, User::class.java)
                    if (pageListInfo?.objs != null)
                        (mView as LoginView).onSuccess(pageListInfo?.objs.toString())

                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    ExceptionHelper.handleImsException(mView, e)
                }
            }, mProvider)
    }
}