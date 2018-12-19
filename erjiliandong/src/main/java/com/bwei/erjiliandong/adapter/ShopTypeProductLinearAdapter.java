package com.bwei.erjiliandong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.erjiliandong.R;
import com.bwei.erjiliandong.bean.ShopTypeProductBean;

import java.util.ArrayList;
import java.util.List;

public class ShopTypeProductLinearAdapter extends RecyclerView.Adapter<ShopTypeProductLinearAdapter.MyViewHolder>{
    private Context mContext;
    private List<ShopTypeProductBean.Data.ProductData> mList = new ArrayList<>();

    public ShopTypeProductLinearAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<ShopTypeProductBean.Data.ProductData> list){
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.shop_type_product_linear_adapter, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        Glide.with(mContext).load(mList.get(i).getIcon()).into(myViewHolder.mImage);
        myViewHolder.mName.setText(mList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        ImageView mImage;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.tv_shop_type_product_linear);
            mImage = itemView.findViewById(R.id.iv_shop_type_product_linear);
        }
    }
}

