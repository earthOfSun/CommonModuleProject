package com.wdy.login.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.wdy.login.R
import com.wdy.login.presenter.LoginPresenter
import com.wdy.login.view.LoginView
import com.wdy.common.common.ARouterPath
import com.wdy.common.ui.base.BaseMvpActivity
import com.wdy.common.utils.ResourceUtils
import kotlinx.android.synthetic.main.activity_login.*

@Route(path = ARouterPath.PATH_LOGIN)
class LoginActivity : BaseMvpActivity<LoginPresenter>(),LoginView {
    override fun onSuccess(result: String) {
        mTvTest.text = result
    }

    override fun initPresenterAndView() {
        mPresenter = LoginPresenter()
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mTvTest.text=(ResourceUtils.getString(R.string.app_name))
        mBtnLogin.setOnClickListener{
            val requestMap = HashMap<String, Any>()
            mPresenter.login(requestMap)
        }
    }
}
