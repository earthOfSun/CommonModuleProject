package com.wdy.login.data.protocol

import java.io.Serializable

/**
 * Created by Administrator on 2017/7/14.
 */

class Attachement : Serializable {
    /**
     * 文件类型
     */
    var fileType: String? = null

    /**
     * id
     */
    var id: String? = null
    /**
     * 路径
     */
    var url: String? = null

    companion object {
        val ATTACHEMENT_AUDIO = "audio"
        val ATTACHEMENT_PICTURE = "picture"
        val ATTACHEMENT_VIDEO = "video"
    }
}
