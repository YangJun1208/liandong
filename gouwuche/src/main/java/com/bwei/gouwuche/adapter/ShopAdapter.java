package com.bwei.gouwuche.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwei.gouwuche.R;
import com.bwei.gouwuche.bean.ShopBean;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.ViewHolder> {

    private Context context;
    private List<ShopBean.DataBean> mDatas;

    public ShopAdapter(Context context) {
        this.context = context;
        mDatas=new ArrayList<>();
    }

    public void setList(List<ShopBean.DataBean> list) {
        this.mDatas = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.shop_seller_car_adapter, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        //商家的名字
        viewHolder.textView.setText(mDatas.get(i).getSellerName());

        final ProductsAdapter productsAdapter = new ProductsAdapter(context, mDatas.get(i).getList());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        viewHolder.recyclerView.setLayoutManager(linearLayoutManager);

        viewHolder.recyclerView.setAdapter(productsAdapter);
        //改变商家的选中状态
        viewHolder.checkBox.setChecked(mDatas.get(i).isCheck());

        productsAdapter.setListener(new ProductsAdapter.ShopCallBackListener() {
            @Override
            public void callBack() {
                if(mShopCallBackListener!=null){
                    mShopCallBackListener.callBack(mDatas);
                }
                List<ShopBean.DataBean.ListBean> listBeans = mDatas.get(i).getList();
                //创建一个临时的标志位，用来记录现在点击的状态
                boolean isAllCheck=true;

                for (ShopBean.DataBean.ListBean bean:listBeans){
                    if(!bean.isCheck()){
                        isAllCheck=false;
                        break;
                    }
                }
                //刷新商家的状态
                viewHolder.checkBox.setChecked(isAllCheck);
                mDatas.get(i).setCheck(isAllCheck);
            }
        });

        //监听checkBox的点击事件
        //目的是改变旗下所有商品的选中状态
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //改变自己的一个状态
                mDatas.get(i).setCheck(viewHolder.checkBox.isChecked());
                productsAdapter.selectOrRemoveAll(viewHolder.checkBox.isChecked());
            }
        });
    }
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox checkBox;
        private TextView textView;
        private RecyclerView recyclerView;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            checkBox=itemView.findViewById(R.id.check_shop);
            textView=itemView.findViewById(R.id.tv_shop);
            recyclerView=itemView.findViewById(R.id.recycler_shop);
        }
    }

    private ShopCallBackListener mShopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mShopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callBack(List<ShopBean.DataBean> list);
    }

}
