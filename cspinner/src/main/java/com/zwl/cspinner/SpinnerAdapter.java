package com.zwl.cspinner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SpinnerAdapter extends RecyclerView.Adapter<SpinnerAdapter.SpinnerHolder> {
    private Context mContext;
    private List<String> mDatas = new ArrayList<>();
    private boolean mShowChooseedIcon;
    private int mChoosedPosition;
    private int rightIconRes = -1;
    private OnItemClick onItemClick;


    public SpinnerAdapter(Context mContext, List<String> mDatas, boolean showChooseedIcon) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        this.mShowChooseedIcon = showChooseedIcon;
    }

    @NonNull
    @Override
    public SpinnerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
        return new SpinnerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SpinnerHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvItemText.setText(mDatas.get(position));
        if (mShowChooseedIcon && mChoosedPosition == position) {
            if (rightIconRes != -1) {
                holder.ivItemImage.setImageResource(rightIconRes);
            }
            holder.ivItemImage.setVisibility(View.VISIBLE);
        } else {
            holder.ivItemImage.setVisibility(View.GONE);
        }
        holder.tvItemText.setOnClickListener(v -> {
            if (onItemClick != null) {
                onItemClick.onItemClick(position);
                mChoosedPosition = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void setOnItemClick(OnItemClick onItemClick) {
        this.onItemClick = onItemClick;
    }

    public void setmShowChooseedIcon(boolean showChooseedIcon) {
        this.mShowChooseedIcon = showChooseedIcon;
        notifyDataSetChanged();
    }

    public void setmChoosedPosition(int choosedPositionn) {
        this.mChoosedPosition = choosedPositionn;
        notifyDataSetChanged();
    }

    public void setRightIconRes(int rightIconRes) {
        this.rightIconRes = rightIconRes;
        notifyDataSetChanged();
    }


    public class SpinnerHolder extends RecyclerView.ViewHolder {
        TextView tvItemText;
        ImageView ivItemImage;

        public SpinnerHolder(@NonNull View itemView) {
            super(itemView);
            tvItemText = itemView.findViewById(R.id.tv_spinner_itemtext);
            ivItemImage = itemView.findViewById(R.id.iv_spinner_itemicon);
        }
    }


    public interface OnItemClick {
        void onItemClick(int position);
    }
}
