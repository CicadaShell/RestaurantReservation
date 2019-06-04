package com.njupt.cicada.restaurantreservation.ui;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.Message;

import com.cicada.library.permission.MPermission;
import com.cicada.library.permission.annotation.OnMPermissionDenied;
import com.cicada.library.permission.annotation.OnMPermissionGranted;
import com.cicada.library.utils.DBUtils;
import com.cicada.library.utils.ToastUtils;
import com.njupt.cicada.restaurantreservation.R;
import com.njupt.cicada.restaurantreservation.base.BaseActivity;
import com.njupt.cicada.restaurantreservation.constants.AppConstants;

import java.lang.ref.WeakReference;

/**
 * @Author guocongcong
 * @Date 2019/5/31
 * @Describe
 */
public class LoadingActivity extends BaseActivity {
    /**
     * 权限请求码
     **/
    private final int BASIC_PERMISSION_REQUEST_CODE = 100;

    private final static int HANDLER_START_APP = 0;

    private final static int HANDLER_START_APP_DELAY = 1000;

    private static LoadingHandler loadingHandler;

    @Override
    public void initView() {
        loadingHandler = new LoadingHandler(mContext);
        requestPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void startAPP() {
        DBUtils.createDb(mContext, AppConstants.DATABASE_NAME, AppConstants.DATABASE_VERSION);
        loadingHandler.sendEmptyMessageDelayed(HANDLER_START_APP, HANDLER_START_APP_DELAY);
    }

    /**
     * 基本权限管理
     */
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            MPermission.with(this)
                    .addRequestCode(BASIC_PERMISSION_REQUEST_CODE)
                    .permissions(
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                    .request();
        } else {
            startAPP();
        }
    }

    @OnMPermissionGranted(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionSuccess() {
        startAPP();
    }

    @OnMPermissionDenied(BASIC_PERMISSION_REQUEST_CODE)
    public void onBasicPermissionFailed() {
        ToastUtils.init(ToastUtils.ERROR).shortShow(mContext, "无权限");
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        MPermission.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
        if (requestCode == BASIC_PERMISSION_REQUEST_CODE) {
            if (grantResults != null && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startAPP();
            } else {
                ToastUtils.init(ToastUtils.ERROR).shortShow(mContext, "无权限");
                finish();
            }
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_loading;
    }

    private static class LoadingHandler extends Handler {

        private WeakReference<Context> mContext;

        public LoadingHandler(Context context) {
            mContext = new WeakReference<>(context);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_START_APP:
                    mContext.get().startActivity(LoginActivity.createIntent(mContext.get()));
                    ((Activity) mContext.get()).finish();
                    break;
                default:
                    break;
            }
        }

    }

}
