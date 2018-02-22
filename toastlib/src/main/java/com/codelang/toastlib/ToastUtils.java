package com.codelang.toastlib;

import android.content.Context;
import android.widget.Toast;

/**
 * @author wangqi
 * @since 2018/1/6 15:59
 */

public class ToastUtils {

    public static void show(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
    }
}
