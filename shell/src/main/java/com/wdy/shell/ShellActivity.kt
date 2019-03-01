package com.wdy.shell

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.wdy.common.common.ARouterPath
import kotlinx.android.synthetic.main.activity_shell.*

class ShellActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shell)
        mBtnMain.setOnClickListener {
            ARouter.getInstance()
                .build(ARouterPath.PATH_MAIN)
                .navigation()
        }
        mBtnBusiness.setOnClickListener{
            ARouter.getInstance()
                .build(ARouterPath.PATH_BUSINESS)
                .navigation()
        }

    }
}
