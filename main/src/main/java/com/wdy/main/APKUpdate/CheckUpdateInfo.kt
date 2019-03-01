package com.wdy.main.APKUpdate

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.os.Message
import android.support.v7.app.AlertDialog
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import com.wdy.common.data.BaseUrls
import com.wdy.common.utils.*
import com.wdy.main.R
import com.wdy.main.activity.SplashActivity
import com.wdy.main.data.protocol.Version
import com.wdy.main.updateVersion.ManifestUtils
import java.io.File


class CheckUpdateInfo(private val mContext: Context) {

  private var requestId: Long = 0
  private val downloadReceiver: DownloadReceiver
  private var progressDialog: AlertDialog? = null

  var alertDialog: AlertDialog? = null
    private set
//  private val downloadRunnable: DownloadRunnable? = null

  private var progressBar: HorizontalProgressBar? = null
  private val updateHandler = object : Handler() {


    override fun handleMessage(msg: Message) {
      when (msg.what) {
        DownloadManager.STATUS_PENDING -> {
        }
        DownloadManager.STATUS_RUNNING -> {
          val progress = msg.obj as Int
          Log.e("progress", progress.toString() + "")
          progressBar!!.setProgressWithAnimation(progress.toFloat())
        }
        DownloadManager.STATUS_FAILED -> {
        }
        DownloadManager.STATUS_SUCCESSFUL -> {

          val data = msg.data
          val path = data.getString("path")
          val id = data.getString("id")
        }
      }


    }
  }


  init {

    downloadReceiver = DownloadReceiver()
  }


   fun updateApk(versionInfo: Version) {
    val builder = AlertDialog.Builder(mContext, R.style.AlertDialogCustom)
    val message = if (!StringUtils.isNullOrEmpty(versionInfo.description)) versionInfo.description else "版本更新"
    var title = "版本更新"
    if (!NetworkUtils.isWifiConnected(mContext)) {
      title = "版本更新(当前网络不是WIFI!)"
      val spannableString = SpannableStringBuilder(title)
      spannableString.setSpan(
        ForegroundColorSpan(ResourceUtils.getColor(R.color.colorPrimary)),
        5,
        title.length - 1,
        SpannableStringBuilder.SPAN_EXCLUSIVE_EXCLUSIVE
      )
      builder.setTitle(spannableString)
    } else
      builder.setTitle(title)


    builder.setMessage(message)
      .setCancelable(false)
    if (versionInfo.isUpdate) {
      alertDialog = builder.setPositiveButton(mContext.getString(R.string.update)) { dialog, which ->
        if (NetworkUtils.isConnected(mContext)) {
          startDownload(BaseUrls.getBaseUrl() + versionInfo.url!!)
          alertDialog!!.dismiss()
        } else {
          ToastUtils.toast(mContext, "网络未连接!")
        }
      }.show()
    } else {
      alertDialog = builder.setPositiveButton(mContext.getString(R.string.update)) { dialog, which ->
        if (NetworkUtils.isConnected(mContext)) {

          startDownload(BaseUrls.getBaseUrl() + versionInfo.url!!)
          alertDialog!!.dismiss()
        } else {

        }

      }.setNegativeButton(mContext.getString(R.string.cancel)) { dialog, which -> alertDialog!!.dismiss() }.show()
    }
  }


  fun checkVersion(newVersionCode: Int): Boolean {
    val oldVersionCode = ManifestUtils.getVersionCode()
    return newVersionCode > oldVersionCode
  }

  fun installAPK(finishPath: String?) {

    val file = File(finishPath)
    Log.e("downloadreceiver2", finishPath)
    if (file.exists()) {
      APKUtils.install(file, mContext)
      (mContext as SplashActivity).finish()
    } else {
      if (alertDialog != null && alertDialog!!.isShowing) {
        alertDialog!!.dismiss()
        alertDialog = null
      }
    }
  }

  private fun checkPath(path: String?) {
    var path = path
    if (StringUtils.isNullOrEmpty(path))
      return
    if (Build.VERSION_CODES.M < Build.VERSION.SDK_INT) {
      path = queryDownloadedApk(java.lang.Long.valueOf(requestId))
      installAPK(path)
    }

    if (path!!.startsWith("file:")) {
      installAPK(path.substring(5, path.length))
    } else
      installAPK(path)
  }

  //通过downLoadId查询下载的apk，解决6.0以后安装的问题
  fun queryDownloadedApk(downloadId: Long): String? {

    val downloader = mContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

    if (downloadId != -1L) {
      val query = DownloadManager.Query()
      query.setFilterById(downloadId)
      query.setFilterByStatus(DownloadManager.STATUS_SUCCESSFUL)
      val cur = downloader.query(query)
      if (cur != null) {
        if (cur.moveToFirst()) {
          val uriString = cur.getString(cur.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI))
          if (!TextUtils.isEmpty(uriString)) {
            return uriString
          }
        }
        cur.close()
      }
    }
    return null
  }

  private fun createProgressDialog() {
    val builder = AlertDialog.Builder(mContext, R.style.AlertDialogCustom)
    val view = LayoutInflater.from(mContext).inflate(R.layout.update_progress_view, null)
    progressBar = view.findViewById(R.id.progress_bar)
    builder.setView(view)
    builder.setCancelable(false)
    progressDialog = builder.show()
  }


  private fun startDownload(url: String) {
    //获得DownloadManager对象
    val downloadManager = mContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    //获得下载id，这是下载任务生成时的唯一id，可通过此id获得下载信息
    requestId = downloadManager.enqueue(CreateRequest(url))
    createProgressDialog()
    Thread(DownloadRunnable(mContext, url, updateHandler, requestId, downloadManager)).start()
  }

  private fun CreateRequest(url: String?): DownloadManager.Request? {

    if (url == null || url == "") {
      return null
    }
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

  inner class DownloadReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

      if (intent.action === DownloadManager.ACTION_DOWNLOAD_COMPLETE) {
        progressBar!!.setProgressWithAnimation(100f)
        if (progressDialog != null && progressDialog!!.isShowing)
          progressDialog!!.dismiss()
        val id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
        if (id == requestId) {
          val downloadManager = mContext.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
          val uriForDownloadedFile = downloadManager.getUriForDownloadedFile(id)
          checkPath(uriForDownloadedFile.toString())
        }
      }
    }
  }

//  fun installLocalAPK() {
//    val environment = Environment()
//    val directory = environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
//    val systemDownloadPath = directory.getPath()
//    val path = systemDownloadPath + "/apk/ims_zh.apk"
//    val file = File(path)
//    if (file.exists()) {
//      val packageManager = mContext.packageManager
//      val packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES)
//      Log.e("packageinfo", packageInfo.versionName + packageInfo.versionCode + packageInfo.packageName)
//      val versionCode = packageInfo.versionCode
//      val update = isUpdate(ManifestUtils.getVersionCode(), versionCode)
//      if (update) {
//        localInstallDialog(path)
//      } else {
//        file.deleteOnExit()
////        getUpdateInfo()
//      }
//    } else
////      getUpdateInfo()
//  }

  private fun localInstallDialog(path: String) {
    AlertDialog.Builder(mContext, R.style.AlertDialogCustom)
      .setTitle("版本更新")
      .setMessage("亲，新版本免流量安装啦")
      .setCancelable(false)
      .setPositiveButton("安装") { dialog, which -> installAPK(path) }.show()
  }
}
