package cn.a17cc.joycharge.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * 创建人：luying
 * 创建时间：16/12/17
 * 类说明：
 */

public class ThreeFourTextView extends TextView{
    public ThreeFourTextView(Context context) {
        super(context);
    }

    public ThreeFourTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ThreeFourTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int height = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(widthMeasureSpec) * 4 / 3,
                View.MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, height);

    }
}
