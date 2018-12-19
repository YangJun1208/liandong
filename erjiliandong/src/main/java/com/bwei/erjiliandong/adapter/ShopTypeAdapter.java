package com.bwei.erjiliandong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.bwei.erjiliandong.R;
import com.bwei.erjiliandong.bean.ShopTypeBean;

import java.util.ArrayList;
import java.util.List;

public class ShopTypeAdapter extends RecyclerView.Adapter<ShopTypeAdapter.MyViewHolder>{
    private Context mContext;
    private List<ShopTypeBean.Data> mList = new ArrayList<>();

    public ShopTypeAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<ShopTypeBean.Data> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.shop_type_adapter, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.mName.setText(mList.get(i).getName());
        myViewHolder.mLinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnClickListener != null){
                    mOnClickListener.onClick(i, mList.get(i).getCid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout mLinearLayout;
        TextView mName;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.tv_shop_type_name);
            mLinearLayout = itemView.findViewById(R.id.ll_shop_type);
        }
    }

    private OnClickListener mOnClickListener;

    public void setOnClickListener(OnClickListener listener) {
        this.mOnClickListener = listener;
    }

    public interface OnClickListener {
        void onClick(int position, String cid);
    }
}
