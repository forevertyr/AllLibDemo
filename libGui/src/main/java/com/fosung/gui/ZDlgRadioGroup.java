/*
 * **********************************************************
 *   author   colin
 *   company  fosung
 *   email    wanglin2046@126.com
 *   date     16-10-8 下午4:47
 * *********************************************************
 */
package com.fosung.gui;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 单选框组合弹出框
 */
public class ZDlgRadioGroup extends ZDlg implements View.OnClickListener {

    private TextView                             tvTitle;        //标题
    private ZDialogParamSubmitInterface<Integer> submitInter;    // 点击确定按钮回调接口
    private RadioGroup                           rgChoise;
    private Context                              context;
    private TextView                             tvCancel;
    private TextView                             tvSubmit;

    public ZDlgRadioGroup(Activity context) {
        super(context, R.layout.gui_dlg_radiogroup);
        this.context = context;
        initRes();
    }

    private void initRes() {
        tvTitle = getView(R.id.dialogradiogroup_title);
        rgChoise = getView(R.id.dialogradiogroup_radiogroup);
        tvCancel = getView(R.id.dialog_cancelbutton);
        tvSubmit = getView(R.id.dialog_okbutton);

        tvSubmit.setText("确定");
        tvCancel.setText("取消");
        tvSubmit.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
    }

    public ZDlgRadioGroup setTitle(String str) {
        if (!TextUtils.isEmpty(str)) {
            tvTitle.setVisibility(View.VISIBLE);
            tvTitle.setText(str);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        return this;
    }

    public ZDlgRadioGroup setDatas(ArrayList<String> listData, String defStr) {
        setDatas(listData.toArray(new String[listData.size()]), defStr);
        return this;
    }

    public ZDlgRadioGroup setDatas(String[] attrStr, String defStr) {
        int paddingVer = (int) context.getResources()
                                      .getDimension(R.dimen.gui_dimens_mid);
        int paddingHor = (int) context.getResources()
                                      .getDimension(R.dimen.gui_dimens_big);
        LayoutParams layout = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layout.leftMargin = paddingHor;
        layout.rightMargin = paddingHor;

        rgChoise.removeAllViews();
        rgChoise.setOnCheckedChangeListener(null);
        for (int i = 0; i < attrStr.length; i++) {
            RadioButton btn = new AppCompatRadioButton(context);
            btn.setText(attrStr[i]);
            btn.setTextAppearance(context, R.style.Gui_TextStyle_GrayMid_Normal);
            btn.setPadding(0, paddingVer, 0, paddingVer);
            btn.setBackgroundResource(R.drawable.gui_dlg_radio_sel);
            btn.setId(i + 100);
            if (attrStr[i].equals(defStr)) {
                btn.setChecked(true);
            }
            rgChoise.addView(btn, layout);
        }
        return this;
    }

    /**
     * 添加确定回调接口
     */
    public ZDlgRadioGroup addSubmitListener(ZDialogParamSubmitInterface<Integer> submitInter) {
        this.submitInter = submitInter;
        return this;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dialog_okbutton) {
            if (submitInter != null && rgChoise.getCheckedRadioButtonId() > 0) {
                submitInter.submit(rgChoise.getCheckedRadioButtonId() - 100);
                cancel();
            } 
        }else if (v.getId() == R.id.dialog_cancelbutton) {
            cancel();
        }
    }
}
