package com.ztsc.commonutils.combinebitmap;

import android.content.Context;

import com.ztsc.commonutils.combinebitmap.helper.Builder;

public class CombineBitmap {
    public static Builder init(Context context) {
        return new Builder(context);
    }
}
