package com.zwl.cspinner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CSpinner extends PopupWindow {
    private Context mContext;
    private RecyclerView mList;
    private List<String> mDatas = new ArrayList<>();
    private SpinnerAdapter mAdapter;
    private boolean mShowRightIcon;
    private int mChoosedPosition;
    private int mRightIconRes = -1;

    public CSpinner(Context context) {
        super(context);
        initView(context);
    }

    public CSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public CSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        this.mContext = context;
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_popuplist, null, false);
        mList = view.findViewById(R.id.lv_list);
        setContentView(view);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        mList.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new SpinnerAdapter(mContext, mDatas, true);
        mList.setAdapter(mAdapter);
        setClippingEnabled(false);
    }

    public void setData(List<String> data) {
        mDatas.clear();
        mDatas.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(SpinnerAdapter.OnItemClick onItemClick) {
        mAdapter.setOnItemClick(onItemClick);
    }


    public boolean showDropDown(View view) {
        int viewWidth = view.getMeasuredWidth();
        setWidth(viewWidth + dp2px(mContext, 12));
        showAsDropDown(view, -dp2px(mContext, 6), 0);
        return true;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public int getDisplayHeight(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Rect rect = new Rect();
        anchor.getGlobalVisibleRect(rect);
        int h = getDisplayHeight(mContext) - rect.bottom;

        mList.post(() -> {
            int height = mList.getHeight();
            if (height > (h - 20)) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, h - 20);
                params.height = h - 20;
                mList.setLayoutParams(params);
                //this.setHeight(h - 20);
            } else {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
                params.height = height;
                mList.setLayoutParams(params);
                // this.setHeight(height);
            }
        });
        // }
        super.showAsDropDown(anchor, xoff, yoff);
    }

    public void setmShowRightIcon(boolean showRightIcon) {
        this.mShowRightIcon = showRightIcon;
        mAdapter.setmShowChooseedIcon(mShowRightIcon);
    }

    public void setmChoosedPosition(int choosedPosition) {
        this.mChoosedPosition = choosedPosition;
        mAdapter.setmChoosedPosition(mChoosedPosition);
    }

    /**
     * 将dp转px
     *
     * @param context
     * @param dpValue
     * @return
     */
    private int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public void setmRightIconRes(int rightIconRes) {
        this.mRightIconRes = rightIconRes;
        mAdapter.setRightIconRes(mRightIconRes);
    }
}
