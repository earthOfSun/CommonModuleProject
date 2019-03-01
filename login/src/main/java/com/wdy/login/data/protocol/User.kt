package com.wdy.login.data.protocol

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User constructor(
        var description: String? = null,
        var id: String? = null,
        var regionName: String? = null,
        var state: Int? = null,//	1为未提交，2 处理中 ，3 完成， 4 已评价
        var submitTime: Long? = null
) : Parcelable