package com.zwl.cspinner;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mContext, LinearLayout.VERTICAL);
        dividerItemDecoration.setDrawable(mContext.getDrawable(R.drawable.decoration_gray_1));
        mList.addItemDecoration(dividerItemDecoration);
        mAdapter = new SpinnerAdapter(mContext, mDatas, true);
        mList.setAdapter(mAdapter);
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

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            Rect outRect1 = new Rect();
            ((Activity) mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect1);
            int h = outRect1.height() - rect.bottom;
            mList.post(() -> {
                int height = mList.getHeight();
                if (height > (h - 20)) {
                    setHeight(h - 20);
                } else {
                    setHeight(height);
                }
            });
        }
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
