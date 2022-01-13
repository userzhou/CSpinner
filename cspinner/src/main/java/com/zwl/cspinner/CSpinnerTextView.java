package com.zwl.cspinner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("AppCompatCustomView")
public class CSpinnerTextView extends TextView {
    private Context mContext;
    private List<String> mDatas = new ArrayList<>();
    private int mChoosePosition;
    private CSpinner mSpinner;
    private boolean mShowRightIcon;
    private int mRightIconRes = -1;
    private int mDrawableUp = R.mipmap.icon_select_up;
    private int mDrawableDown = R.mipmap.icon_select_down;
    private OnSpinnerChoosedListener onSpinnerChoosedListener;
    private int mWidth, mHeight;


    public CSpinnerTextView(Context context) {
        super(context);
        init(context);
    }

    public CSpinnerTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CSpinnerTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mSpinner = new CSpinner(context);
        mSpinner.setTopView(this);
        mSpinner.setData(mDatas);
        setCompoundDrawablesWithIntrinsicBounds(0, 0, mDrawableDown, 0);
        initListener();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minWidth = getSuggestedMinimumWidth();
        int minHeight = getSuggestedMinimumHeight();
        mWidth = measureWidth(minWidth, widthMeasureSpec);
        mSpinner.setTopWidth(mWidth);
        mHeight = measureHeight(minHeight, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
    }


    private int measureWidth(int defaultWidth, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultWidth = getPaddingLeft() + getPaddingRight();
                break;
            case MeasureSpec.EXACTLY:
                defaultWidth = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultWidth = Math.max(defaultWidth, specSize);
        }
        return defaultWidth;
    }

    private int measureHeight(int defaultHeight, int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.AT_MOST:
                defaultHeight = getPaddingTop() + getPaddingBottom();
                break;
            case MeasureSpec.EXACTLY:
                defaultHeight = specSize;
                break;
            case MeasureSpec.UNSPECIFIED:
                defaultHeight = Math.max(defaultHeight, specSize);
                break;
        }
        return defaultHeight;
    }

    private void initListener() {
        setOnClickListener(v -> {
            if (mDatas.size() == 0) {
                return;
            }
            if (mSpinner.isShowing()) {
                mSpinner.dismiss();
                setCompoundDrawablesWithIntrinsicBounds(0, 0, mDrawableDown, 0);
            } else {
                mSpinner.showDropDown(v);
                setCompoundDrawablesWithIntrinsicBounds(0, 0, mDrawableUp, 0);
            }
        });
        mSpinner.setOnDismissListener(() -> setCompoundDrawablesWithIntrinsicBounds(0, 0, mDrawableDown, 0));
        mSpinner.setOnItemClickListener(position -> {
            mChoosePosition = position;
            String content = mDatas.get(position);
            setText(content);
            mSpinner.dismiss();
            if (onSpinnerChoosedListener != null) {
                onSpinnerChoosedListener.onChoosed(mChoosePosition, content);
            }
        });
    }

    public CSpinnerTextView setDrawableUp(int drawableUp) {
        this.mDrawableUp = drawableUp;
        return this;
    }

    public CSpinnerTextView setDrawableDown(int drawableDown) {
        this.mDrawableDown = drawableDown;
        setCompoundDrawablesWithIntrinsicBounds(0, 0, mDrawableDown, 0);
        return this;
    }

    public CSpinnerTextView setmShowRightIcon(boolean showRightIcon) {
        this.mShowRightIcon = showRightIcon;
        mSpinner.setmShowRightIcon(mShowRightIcon);
        return this;
    }

    public CSpinnerTextView setmChoosePosition(int choosePosition) {
        if (mDatas.size() == 0) {
            Log.e("CSpinnerTextView", "setmChoosePosition: mDatas size is 0");
            return this;
        }
        this.mChoosePosition = choosePosition;
        mSpinner.setmChoosedPosition(mChoosePosition);
        if (mDatas != null && mDatas.size() > 0) {
            setText(mDatas.get(mChoosePosition));
        }
        return this;
    }

    public int getChoosePosition() {
        return mChoosePosition;
    }

    public CSpinnerTextView setmDatas(List<String> datas) {
        this.mDatas = datas;
        mSpinner.setData(mDatas);
        if (mDatas != null && mDatas.size() > 0) {
            setText(mDatas.get(mChoosePosition));
        }
        return this;
    }

    public CSpinnerTextView setOnSpinnerChoosedListener(OnSpinnerChoosedListener onSpinnerChoosedListener) {
        this.onSpinnerChoosedListener = onSpinnerChoosedListener;
        return this;
    }

    public CSpinnerTextView setmRightIconRes(int rightIconRes) {
        this.mRightIconRes = rightIconRes;
        mSpinner.setmRightIconRes(rightIconRes);
        return this;
    }

    public interface OnSpinnerChoosedListener {
        void onChoosed(int position, String content);
    }
}
