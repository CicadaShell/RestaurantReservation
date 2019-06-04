package com.njupt.cicada.restaurantreservation.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.cicada.library.utils.DisplayUtils;
import com.njupt.cicada.restaurantreservation.R;

import butterknife.ButterKnife;

/**
 * @Author guocongcong
 * @Date 2018/7/30
 * @Describe
 */
public abstract class BaseDialog extends Dialog {

    protected Context mContext;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.MyDialog);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        setCanceledOnTouchOutside(false);
        ButterKnife.bind(this);

        // 将对话框的大小按屏幕大小的百分比设置
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.width = (int) (DisplayUtils.getScreenWidth(mContext) * 0.8); //设置宽度
        getWindow().setAttributes(lp);

        initView();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        hintKeyBoard();
    }

    /**
     * 隐藏软键盘
     */
    private void hintKeyBoard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 初始化页面
     *
     * @return
     */
    public abstract void initView();

    /**
     * 获取布局文件的id
     *
     * @return
     */
    public abstract int getLayoutId();

}
