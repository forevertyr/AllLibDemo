package com.zcolin.usedemo.amodule.demo_http;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zcolin.frame.http.response.StringResponse;
import com.zcolin.usedemo.R;
import com.zcolin.usedemo.amodule.base.BaseSecondLevelActivity;
import com.zcolin.usedemo.amodule.main.entity.BaiduWeather;
import com.zcolin.usedemo.http.HttpUrl;
import com.zcolin.usedemo.http.HttpUtil;
import com.zcolin.usedemo.http.entity.HttpCommonReply;
import com.zcolin.usedemo.http.response.ZResponse;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Response;


/**
 * HttpDemo
 */
public class HttpDemoActivity extends BaseSecondLevelActivity implements View.OnClickListener {
    private LinearLayout llContent;
    private TextView     textView;
    private ArrayList<Button> listButton = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_http_db);

        setToolbarTitle("HttpDemo");
        init();
    }

    private void init() {
        llContent = getView(R.id.ll_content);
        textView = getView(R.id.textview);
        textView.setMovementMethod(new ScrollingMovementMethod());
        listButton.add(addButton("获取百度数据"));
        listButton.add(addButton("获取对象"));
        listButton.add(addButton("Post数据(请看代码，无效果)"));
        listButton.add(addButton("上传文件(请看代码，无效果)"));

        for (Button btn : listButton) {
            btn.setOnClickListener(this);
        }
    }

    private Button addButton(String text) {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        Button button = new Button(mActivity);
        button.setText(text);
        button.setGravity(Gravity.CENTER);
        button.setAllCaps(false);
        llContent.addView(button, params);
        return button;
    }

    /**
     * 获取为经解析的字符串
     * <p>
     * 此处为Demo，实际使用一般使用{@link #getObject()}
     */
    public void getBaiduStringData() {
        HttpUtil.get(HttpUrl.URL_BAIDU_TEST, new StringResponse(mActivity, "正在获取数据") {
            @Override
            public void onError(int code, Call call, Exception e) {
                //TODO 错误处理  ToastUtil.toastShort(LogUtil.ExceptionToString(e));
            }

            @Override
            public void onSuccess(Response response, String resObj) {
                textView.setText(resObj);
            }
        });
    }


    /**
     * 获取对象
     * 实际使用时一般使用此方法，而不是直接解析字符窜
     * <p>
     * ZResponse的参数，第二个参数是显示旋转进度条则传入，第三个参数是进度条文字，如果不需要进度条则只需要传入第一个参数
     */
    public void getObject() {
        HttpUtil.get(HttpUrl.URL_BAIDU_TEST, new ZResponse<BaiduWeather>(BaiduWeather.class, mActivity, "正在获取数据……") {
            @Override
            public void onError(int code, String error) {
                //TODO 错误处理   ToastUtil.toastShort(error);
            }

            @Override
            public void onSuccess(Response response, BaiduWeather resObj) {
                if (resObj.results.size() > 0) {
                    BaiduWeather.ResultsBean bean = resObj.results.get(0);
                    textView.setText("city:" + bean.currentCity + " pm25:" + bean.pm25);
                }
            }
        });
    }

    /**
     * POST请求， 返回Gson序列化的对象
     * <p>
     * 带有progressBar
     */
    public void postWithBarResponse() {
        HashMap<String, String> params = new HashMap<>();
        params.put("param1", "sss");
        HttpUtil.post(HttpUrl.URL_BAIDU_TEST, params, new ZResponse<HttpCommonReply>(HttpCommonReply.class, mActivity, "正在加载……") {
            @Override
            public void onSuccess(Response response, HttpCommonReply httpBaseBean) {
                textView.setText(String.valueOf(httpBaseBean));
            }

            @Override
            public void onError(int code, String error) {
                textView.setText(error);
            }
        });
    }

    /**
     * 上传文件，可以和其他参数一起上传，也可以单独上传
     */
    public void uploadFile() {
        HashMap<String, String> params = new HashMap<>();
        params.put("param1", "sss");
        HashMap<String, File> fileParams = new HashMap<>();
        fileParams.put("key", new File(""));
        HttpUtil.uploadFile(HttpUrl.URL_BAIDU_TEST, params, fileParams, new ZResponse<HttpCommonReply>(HttpCommonReply.class, mActivity, "正在加载……") {
            @Override
            public void onSuccess(Response response, HttpCommonReply httpBaseBean) {
            }

            @Override
            public void onError(int code, String error) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == listButton.get(0)) {
            getBaiduStringData();
        } else if (v == listButton.get(1)) {
            getObject();
        }
    }
}
