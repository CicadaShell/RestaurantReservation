package com.cicada.library.pojo;

import android.os.Bundle;

/**
 * Created by zhangfei on 2017/4/27.
 */

public class ViewPageInfo {

    public final String tag;
    public final Class<?> clss;
    public final String title;
    public final Bundle args;

    public ViewPageInfo(String _title, String _tag, Class<?> _class, Bundle _args) {
        title = _title;
        tag = _tag;
        clss = _class;
        args = _args;
    }

    public String getTag() {
        return tag;
    }

    public String getTitle() {
        return title;
    }

    public Bundle getArgs() {
        return args;
    }
}
