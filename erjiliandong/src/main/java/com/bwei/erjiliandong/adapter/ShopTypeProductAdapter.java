package com.bwei.erjiliandong.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwei.erjiliandong.R;
import com.bwei.erjiliandong.bean.ShopTypeProductBean;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ShopTypeProductAdapter extends RecyclerView.Adapter<ShopTypeProductAdapter.MyViewHolder> {
    private Context mContext;
    private List<ShopTypeProductBean.Data> mList = new ArrayList<>();

    public ShopTypeProductAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<ShopTypeProductBean.Data> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.shop_type_product_adapter, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        myViewHolder.mName.setText(mList.get(i).getName());

        //右侧使用RecyclerView嵌套展示效果（这里根据真正的需求自行修改）
        final ShopTypeProductLinearAdapter shopTypeProductLinearAdapter = new ShopTypeProductLinearAdapter(mContext);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        myViewHolder.mRecyclerView.setLayoutManager(linearLayoutManager);
        myViewHolder.mRecyclerView.setAdapter(shopTypeProductLinearAdapter);

        myViewHolder.mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        shopTypeProductLinearAdapter.setData(mList.get(i).getList());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView mName;
        RecyclerView mRecyclerView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.tv_shop_type_product_name);
            mRecyclerView = itemView.findViewById(R.id.recyclerview_shop_product);
        }
    }
}
