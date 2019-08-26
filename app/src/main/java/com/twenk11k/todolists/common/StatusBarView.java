package com.twenk11k.todolists.common;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;
import com.twenk11k.todolists.utils.Utils;


public class StatusBarView extends View {

    public StatusBarView(Context context) {
        super(context);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public StatusBarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public boolean canDrawBehindStatusBar() {
        return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (canDrawBehindStatusBar()) {
            setMeasuredDimension(widthMeasureSpec, (int) Utils.getStatHeight(getContext()));
        } else {
            setMeasuredDimension(0, 0);
        }
    }

}