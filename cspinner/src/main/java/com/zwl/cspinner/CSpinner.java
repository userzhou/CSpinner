package com.zwl.cspinner;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;

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
    private View mTopView;
    private View contentView;
    private int mTopViewWidth;

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

    public CSpinner setTopView(View view) {
        this.mTopView = view;
        return this;
    }

    public CSpinner setTopWidth(int width) {
        mTopViewWidth = width;
        return this;
    }

    private void initView(Context context) {
        this.mContext = context;
        contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_popuplist, null, false);
        mList = contentView.findViewById(R.id.lv_list);
        setClippingEnabled(false);
        setContentView(contentView);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setFocusable(true);
        mList.setLayoutManager(new LinearLayoutManager(mContext));
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
        int viewWidth = mTopViewWidth;
        setWidth((int) (viewWidth + dp2px(12)));
        boolean b = setViewHeight();
        if (b) {
            mList.setBackgroundResource(R.mipmap.shadow_popbacall);
        } else {
            mList.setBackgroundResource(R.mipmap.shadow_popbac);
        }
        mList.setTranslationX( (int) -dp2px(6));
        showAsDropDown(view,0, (int) dp2px(3));
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

    private boolean setViewHeight() {
        boolean showAtTop = false;
        Rect rect = new Rect();
        mTopView.getGlobalVisibleRect(rect);
        //text距离底部的高度
        int h = getDisplayHeight(mContext) - rect.bottom;
        //list总高度
        int height = (int) ((int) (dp2px(40) * mDatas.size()) + dp2px(10));
        int fheight;

        if (height > (h - 20)) {
            fheight = h - 20;
            if (fheight < mTopView.getMeasuredHeight() * 2) {
                fheight = height < rect.top ? height : rect.top/2;
                showAtTop = true;
            }
        } else {
            fheight = height;
        }

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, fheight);
        params.height = fheight;
        contentView.setLayoutParams(params);
        this.setHeight(fheight);
        return showAtTop;
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
     * 根据手机的分辨率将dp转成为px。
     */
    private float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, Resources.getSystem().getDisplayMetrics());
    }

    public void setmRightIconRes(int rightIconRes) {
        this.mRightIconRes = rightIconRes;
        mAdapter.setRightIconRes(mRightIconRes);
    }
}
