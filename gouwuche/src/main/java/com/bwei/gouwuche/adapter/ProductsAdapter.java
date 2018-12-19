package com.bwei.gouwuche.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bwei.gouwuche.CustomCounterView;
import com.bwei.gouwuche.R;
import com.bwei.gouwuche.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {

    private Context context;
    private List<ShopBean.DataBean.ListBean> mDatas;

    public ProductsAdapter(Context context, List<ShopBean.DataBean.ListBean> mDatas) {
        this.context = context;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.shop_car_adapter, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        String url = mDatas.get(i).getImages().split("\\|")[0].replace("https", "http");
        Glide.with(context).load(url).into(viewHolder.iv_product);

        viewHolder.tv_product_title.setText(mDatas.get(i).getTitle());
        viewHolder.tv_product_price.setText(mDatas.get(i).getPrice()+"");

        //根据我记录的状态，改变勾选
        viewHolder.check_product.setChecked(mDatas.get(i).isCheck());
        //商品的跟商家的有所不同，商品添加了选中改变的监听
        viewHolder.check_product.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //改变自己的状态
                mDatas.get(i).setCheck(isChecked);
                //回调给Acivity，选中的状态被改变了
                if(mShopCallBackListener!=null){
                    mShopCallBackListener.callBack();
                }
            }
        });

        //自定义view里的edit
        viewHolder.custom_product_counter.setData(this,mDatas,i);
        viewHolder.custom_product_counter.setOnCallBack(new CustomCounterView.CallBackListener() {
            @Override
            public void callBack() {
                if(mShopCallBackListener!=null){
                    mShopCallBackListener.callBack();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CheckBox check_product;
        private ImageView iv_product;
        private TextView tv_product_title;
        private TextView tv_product_price;
        private CustomCounterView custom_product_counter;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            check_product=itemView.findViewById(R.id.check_product);
            iv_product=itemView.findViewById(R.id.iv_product);
            tv_product_price=itemView.findViewById(R.id.tv_product_price);
            tv_product_title=itemView.findViewById(R.id.tv_product_title);
            custom_product_counter=itemView.findViewById(R.id.custom_product_counter);
        }
    }

    /**
     * 在我们子商品的adapter中，修改子商品的全选和反选
     *
     * @param isSelectAll
     */
    public void selectOrRemoveAll(boolean isSelectAll) {
        for (ShopBean.DataBean.ListBean listBean : mDatas) {
            listBean.setCheck(isSelectAll);
        }
        notifyDataSetChanged();
    }

    private ShopCallBackListener mShopCallBackListener;

    public void setListener(ShopCallBackListener listener) {
        this.mShopCallBackListener = listener;
    }

    public interface ShopCallBackListener {
        void callBack();
    }
}
