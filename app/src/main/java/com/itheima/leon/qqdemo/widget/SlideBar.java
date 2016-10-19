package com.itheima.leon.qqdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.hyphenate.util.DensityUtil;
import com.itheima.leon.qqdemo.R;


/**
 * 创建者:   Leon
 * 创建时间:  2016/10/18 18:02
 * 描述：    TODO
 */
public class SlideBar extends View {
    public static final String TAG = "SlideBar";

    private int mTextSize = 0;

    private static final String[] SECTIONS = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L"
            , "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
    private Paint mPaint;

    private int mCurrentIndex = - 1;

    private float mTextBaseline = 0;

    private OnSlideBarChangeListener mOnSlideBarChangeListener;

    public SlideBar(Context context) {
        this(context, null);
    }

    public SlideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setColor(getResources().getColor(R.color.qq_section_text_color));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(DensityUtil.sp2px(getContext(), 10));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mTextSize = h / SECTIONS.length;
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        float mTextHeight = fontMetrics.descent - fontMetrics.ascent;
        mTextBaseline = mTextSize / 2 + mTextHeight/2 - fontMetrics.descent;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int x = getWidth() / 2;
        for(int i = 0; i < SECTIONS.length; i++) {
            canvas.drawText(SECTIONS[i], x, mTextBaseline, mPaint);
            mTextBaseline += mTextSize;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                int index = (int) (event.getY() / mTextSize);
                if (mOnSlideBarChangeListener != null && mCurrentIndex != index) {
                    mCurrentIndex = index;
                    mOnSlideBarChangeListener.onSectionChange(index, SECTIONS[index]);
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mOnSlideBarChangeListener != null) {
                    mOnSlideBarChangeListener.onSlidingFinish();
                }
                break;
        }
        return true;
    }

    public interface OnSlideBarChangeListener{

        void onSectionChange(int index, String section);

        void onSlidingFinish();

    }

    public void setOnSlidingBarChangeListener(OnSlideBarChangeListener l) {
        mOnSlideBarChangeListener = l;
    }
}
