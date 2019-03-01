package com.wdy.commonbusiness.activity

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.wdy.login.R
import com.wdy.commonbusiness.presenter.CBMMainPresenter
import com.wdy.commonbusiness.view.CBMMainView
import com.wdy.common.common.ARouterPath
import com.wdy.common.ui.base.BaseMvpActivity
import com.wdy.common.utils.ResourceUtils
import kotlinx.android.synthetic.main.activity_cbm_main.*

@Route(path = ARouterPath.PATH_BUSINESS)
class CBMMainActivity : BaseMvpActivity<CBMMainPresenter>(),
    CBMMainView {
    override fun onSuccess(result: String) {
        mTvTest.text = result
    }

    override fun initPresenterAndView() {
        mPresenter = CBMMainPresenter()
        mPresenter.mView = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cbm_main)
        val requestMap = HashMap<String, Any>()
        mPresenter.getErrorList(requestMap)
        mTvTest.text=(ResourceUtils.getString(R.string.app_name))
    }
}
