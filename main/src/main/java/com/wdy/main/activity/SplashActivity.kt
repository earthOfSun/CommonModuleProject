package com.wdy.main.activity

import android.os.Bundle
import com.wdy.common.ui.base.BaseMvpActivity
import com.wdy.main.R
import com.wdy.main.presenter.SplashPresenter
import com.wdy.main.view.SplashView

class SplashActivity : BaseMvpActivity<SplashPresenter>(), SplashView {

    override fun initPresenterAndView() {
        mPresenter = SplashPresenter()
        mPresenter.mView = this
    }

    override fun onSuccess(result: String) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

}