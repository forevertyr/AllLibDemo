/*
 * *********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     17-1-13 上午11:48
 * ********************************************************
 */
package com.zcolin.usedemo.amodule.base;


import android.os.Bundle;
import android.view.View;

import com.zcolin.usedemo.R;


/**
 * 默认设置了返回图片，实现返回操作,继承此类的子类不必再实现setActionbarTitleBackground，
 * <p/>
 * 如果需要设置标题文字，可以使用setActionbarTitle
 */
public class BaseSecondLevelActivity extends BaseToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        setToolbarLeftBtnText("返回");
//        setToolbarLeftBtnBackground(R.drawable.gui_btn_actionbar_back_tc);
        setToolbarLeftBtnCompoundDrawableLeft(R.drawable.gui_icon_arrow_back);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setToolbarLeftBtnText("返回");
        setToolbarLeftBtnBackground(R.drawable.gui_btn_actionbar_back_sel);
        setToolbarLeftBtnCompoundDrawableLeft(R.drawable.gui_icon_arrow_back);
    }

    @Override
    protected void onToolBarLeftBtnClick() {
        this.finish();
    }
}
