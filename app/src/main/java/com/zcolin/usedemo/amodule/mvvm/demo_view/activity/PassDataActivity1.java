/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-5-26 下午3:36
 * ********************************************************
 */

package com.zcolin.usedemo.amodule.mvvm.demo_view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zcolin.gui.ZEditTextWithClear;
import com.zcolin.usedemo.R;
import com.zcolin.usedemo.amodule.base.BaseActivity;

/**
 * 回传数据的Activity
 */
public class PassDataActivity1 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passdata);
        setToolbarTitle(getClass().getSimpleName());
        initView();
    }

    protected void initView() {
        final ZEditTextWithClear et = getView(R.id.edittext);
        et.setHint("输入需要回传的数据");

        Button btn = getView(R.id.button);
        btn.setText("finishActivity");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PassDataActivity1.this.setResult(RESULT_OK, new Intent().putExtra("data", et.getText()
                                                                                            .toString()));
                PassDataActivity1.this.finish();
            }
        });
    }

}
