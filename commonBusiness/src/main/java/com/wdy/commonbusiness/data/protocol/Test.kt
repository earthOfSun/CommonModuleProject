package com.wdy.commonbusiness.data.protocol

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 *
 * msg：
 */


@Parcelize
data class Test constructor(

        var regionId: String?,

        var regionName: String?,

        var regionPid: String? = null,

        var isSelect: Boolean = false,

        var regionFullName: String? = null
) : Parcelable