/*
 * **********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     16-10-21 下午1:54
 * *********************************************************
 */

package com.zcolin.usedemo.http.response;

import android.app.Activity;

import com.zcolin.frame.app.BaseApp;
import com.zcolin.frame.http.response.GsonResponse;
import com.zcolin.frame.utils.LogUtil;
import com.zcolin.frame.utils.NetworkUtil;
import com.zcolin.usedemo.http.entity.HttpBaseReplyBean;

import java.net.SocketTimeoutException;

import okhttp3.Call;
import okhttp3.Response;


/**
 * 返回gson对象，ZResponse的代理，处理数据完成后调用ZResponse的成功失败函数
 */
public class ZResponseProxy<T extends HttpBaseReplyBean> extends GsonResponse<T> {
    private static final String STATUS_CODE_SUCCESS = "success";

    private ZResponse<T> zResponse;

    ZResponseProxy(Class<T> cls, ZResponse<T> zResponse) {
        super(cls);
        this.zResponse = zResponse;
    }

    ZResponseProxy(Class<T> cls, ZResponse<T> zResponse, Activity barActy) {
        super(cls, barActy);
        this.zResponse = zResponse;
    }

    ZResponseProxy(Class<T> cls, ZResponse<T> zResponse, Activity barActy, String barMsg) {
        super(cls, barActy, barMsg);
        this.zResponse = zResponse;
    }

    @Override
    public void onError(int code, Call call, Exception ex) {
        String str;
        if (ex instanceof SocketTimeoutException || code == 0) {
            if (!NetworkUtil.isNetworkAvailable(BaseApp.APP_CONTEXT)) {
                str = "当前无网络连接，请开启网络！";
            } else {
                str = "连接服务器失败, 请检查网络或稍后重试";
            }
            zResponse.onError(0, str);
        } else {
            zResponse.onError(code, LogUtil.ExceptionToString(ex));
        }
    }

    @Override
    public void onSuccess(Response response, T reply) {
        if (reply != null && STATUS_CODE_SUCCESS.equals(reply.status)) {
            zResponse.onSuccess(response, reply);
        } else if (reply == null) {
            zResponse.onError(-1, "json对象解析失败！");
        } else {
            zResponse.onError(reply.error, reply.status);
        }
    }

    @Override
    public void onFinished() {
        super.onFinished();
        zResponse.onFinished();
    }
}
