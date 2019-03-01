package com.wdy.main.data.protocol

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * msg：
 */
@Parcelize
data class Version constructor(

        var versionCode: Int?,

        var description: String?,

        var url: String? = null,

        var isUpdate: Boolean = false,//是否强制更新

        var name: String? = null
) : Parcelable