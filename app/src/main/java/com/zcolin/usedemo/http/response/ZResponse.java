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

import com.zcolin.frame.utils.LogUtil;
import com.zcolin.frame.utils.ToastUtil;
import com.zcolin.usedemo.http.entity.HttpBaseReplyBean;

import okhttp3.Response;


/**
 * 返回网络对象，使用gons解析好，并通过状态码做成功失败分发
 */
public abstract class ZResponse<T extends HttpBaseReplyBean> {
    private String   barMsg;        //进度条上的文字
    private Activity barActy;       //进度条的Activity
    private Class<T> cls;

    public ZResponse(Class<T> cls) {
        this(cls, null);
    }

    /**
     * @param barActy 进度条Atvicity实体
     */
    public ZResponse(Class<T> cls, Activity barActy) {
        this(cls, barActy, null);
    }

    /**
     * @param barActy 进度条Atvicity实体
     * @param barMsg  进度条上 显示的信息
     */
    public ZResponse(Class<T> cls, Activity barActy, String barMsg) {
        this.cls = cls;
        this.barActy = barActy;
        this.barMsg = barMsg;
    }

    public ZResponseProxy generatedProxy() {
        return new ZResponseProxy<T>(cls, this, barActy, barMsg);
    }

    public abstract void onSuccess(Response response, T resObj);

    public void onError(int code, String error) {
        ToastUtil.toastShort(error);
        LogUtil.w("HttpResponse:", error);
    }

    public void onFinished() {
    }
}
