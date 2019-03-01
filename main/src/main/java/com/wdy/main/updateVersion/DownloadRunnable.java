package com.wdy.main.updateVersion;

import android.app.DownloadManager;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.*;
import android.os.Process;

public class DownloadRunnable implements Runnable {

    private String url;
    private Handler handler;
    private Context mContext;
    private long requestId;
    private final DownloadManager downloadManager;


    public DownloadRunnable(Context context, String url, Handler handler, long requestId, DownloadManager manager) {
        this.mContext = context;
        this.url = url;
        this.handler = handler;
        this.requestId=requestId;
        downloadManager = manager;
    }
    public DownloadRunnable(Context context, String url, long requestId, DownloadManager manager) {
        this.mContext = context;
        this.url = url;
        this.requestId=requestId;
        downloadManager = manager;
    }

    @Override
    public void run() {
        //设置线程优先级为后台，这样当多个线程并发后很多无关紧要的线程分配的CPU时间将会减少，有利于主线程的处理
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);

        startDownload();
    }

    private long startDownload() {
//        //获得DownloadManager对象
//        DownloadManager downloadManager = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
//        //获得下载id，这是下载任务生成时的唯一id，可通过此id获得下载信息
//        requestId = downloadManager.enqueue(CreateRequest(url));
//        //查询下载信息方法
        queryDownloadProgress();
        return requestId;
    }

    private void queryDownloadProgress() {


        DownloadManager.Query query = new DownloadManager.Query();
        //根据任务编号id查询下载任务信息
        query.setFilterById(requestId);
        try {
            boolean isGoging = true;
            while (isGoging) {
                Cursor cursor = downloadManager.query(query);

                if (cursor != null && cursor.moveToFirst()) {

                    //获得下载状态
                    int state = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS));
                    switch (state) {
                        case DownloadManager.STATUS_SUCCESSFUL://下载成功
                            isGoging = false;
                            Bundle bundle = new Bundle();
                            bundle.putString("path", downloadManager.getUriForDownloadedFile(requestId).toString());
                            bundle.putString("id", String.valueOf(requestId));
                            Message msg = handler.obtainMessage(downloadManager.STATUS_SUCCESSFUL);
                            msg.setData(bundle);
//                            msg.sendToTarget();//发送到主线程，更新ui
                            handler.handleMessage(msg);

                            break;
                        case DownloadManager.STATUS_FAILED://下载失败
                            isGoging = false;
                            handler.obtainMessage(downloadManager.STATUS_FAILED).sendToTarget();//发送到主线程，更新ui
                            break;

                        case DownloadManager.STATUS_RUNNING://下载中

                            /**
                             * 计算下载下载率；
                             */
                            int totalSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
                            int currentSize = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
                            int progress = (int) (((float) currentSize) / ((float) totalSize) * 100);
                            Message message = handler.obtainMessage(downloadManager.STATUS_RUNNING, progress);//发送到主线程，更新ui
                            handler.sendMessage(message);
                            Thread.sleep(1000);
                            break;

                        case DownloadManager.STATUS_PAUSED://下载停止
                            handler.obtainMessage(DownloadManager.STATUS_PAUSED).sendToTarget();
                            break;

                        case DownloadManager.STATUS_PENDING://准备下载
                            handler.sendEmptyMessage(DownloadManager.STATUS_PENDING);
                            break;
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private DownloadManager.Request CreateRequest(String url) {
        if (url == null || url.equals("")) {
            return null;
        }
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);// 隐藏notification

        request.setAllowedNetworkTypes(request.NETWORK_WIFI);//设置下载网络环境为wifi
        request.setTitle("振华维客版本更新");
        request.setDescription("正在下载");
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/cesecsh/ims_zh.apk");//指定apk缓存路径，默认是在SD卡中的Download文件夹

        return request;
    }

}
