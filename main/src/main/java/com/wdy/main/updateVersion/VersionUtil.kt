package com.wdy.main.updateVersion

import android.app.DownloadManager
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.view.View
import com.wdy.common.BaseApplication
import com.wdy.common.common.ActivityManager
import com.wdy.common.utils.ResourceUtils
import com.wdy.common.widget.dialog.alert.NormalDialog
import com.wdy.common.widget.dialog.alert.OnNegativeClickListener
import com.wdy.common.widget.dialog.alert.OnPositiveClickListener
import com.wdy.main.R
import com.wdy.main.data.protocol.Version


/**
 *author:wdy
 *date:2019/2/28
 *msg:
 */
class VersionUtil {
  private var updateHandle: Handler = Handler()
  fun checkVersion(newVersionCode: Int): Boolean {
    val oldVersionCode = ManifestUtils.getVersionCode()
    return newVersionCode > oldVersionCode
  }

  fun showDialog(version: Version) {
    val message = version.description ?: "应用流畅度优化"
    NormalDialog.Builder(ActivityManager.instance.getTopActivity())
      .setTitle(ResourceUtils.getString(R.string.version_update))
      .setMessage(message)
      .setPositiveText(ResourceUtils.getString(R.string.update))
      .setNegativeText(ResourceUtils.getString(R.string.cancel))
      .setCancelable(false)
      .setOnDismissListener(DialogInterface.OnDismissListener { })
      .setOnNegativeClickListener(object : OnNegativeClickListener {
        override fun onClick(dialog: NormalDialog, view: View) {


        }
      })
      .setOnPositiveClickListener(object : OnPositiveClickListener {
        override fun onClick(dialog: NormalDialog, view: View) {
          startDownload(version.url!!)
        }
      })
      .show()
  }

  private var downloadId: Long? = null
  fun startDownload(url: String) {
    val downloadManager: DownloadManager = BaseApplication.context.getSystemService(Context.DOWNLOAD_SERVICE)
            as DownloadManager
    downloadId = downloadManager.enqueue(createRequest(url))
    Thread(DownloadRunnable(BaseApplication.context, url, downloadId!!, downloadManager))
  }

  private fun createRequest(url: String): DownloadManager.Request {

    val request = DownloadManager.Request(Uri.parse(url))
    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// 隐藏notification
    request.setMimeType("application/vnd.android.package-archive")
    request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI)//设置下载网络环境为wifi
    request.setTitle("振华+版本更新")
    request.setDescription("正在下载")
    request.setDestinationInExternalPublicDir(
      Environment.DIRECTORY_DOWNLOADS,
      "/apk/ims_zh.apk"
    )//指定apk缓存路径，默认是在SD卡中的Download文件夹

    return request
  }


}