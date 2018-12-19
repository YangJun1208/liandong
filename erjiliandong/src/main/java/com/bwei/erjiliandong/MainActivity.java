package com.bwei.erjiliandong;

import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bwei.erjiliandong.adapter.ShopTypeAdapter;
import com.bwei.erjiliandong.adapter.ShopTypeProductAdapter;
import com.bwei.erjiliandong.bean.ShopTypeBean;
import com.bwei.erjiliandong.bean.ShopTypeProductBean;
import com.bwei.erjiliandong.persenter.IPersenterImpl;
import com.bwei.erjiliandong.view.IView;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView {

    private IPersenterImpl iPersenter;
    private ShopTypeAdapter mShopTypeAdapter;
    private ShopTypeProductAdapter mShopTypeProductAdapter;
    private RecyclerView mRecyclerViewShopType, mRecyclerViewShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iPersenter=new IPersenterImpl(this);
        initShopTypeView();
        initShopTypeProductView();
        getTypeData();
    }

    private void getTypeData() {
        Map<String, String> map = new HashMap<>();
        iPersenter.getRequest(Apis.URL_PRODUCT_GET_CATAGORY, map, ShopTypeBean.class);
    }

    private void initShopTypeProductView() {

        mRecyclerViewShop = findViewById(R.id.recyclerview_shop_type);
        LinearLayoutManager linearLayoutManagerLeft = new LinearLayoutManager(this);
        linearLayoutManagerLeft.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewShop.setLayoutManager(linearLayoutManagerLeft);
        mRecyclerViewShop.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mShopTypeAdapter = new ShopTypeAdapter(this);
        mRecyclerViewShop.setAdapter(mShopTypeAdapter);

        //添加接口回调，用来接收左侧recyclerView的cid
        mShopTypeAdapter.setOnClickListener(new ShopTypeAdapter.OnClickListener() {
            @Override
            public void onClick(int position, String cid) {
                //拿到cid之后，通过接口获得对应的数据，展示在右侧列表中
                getShopData(cid);
            }
        });
    }

    private void initShopTypeView() {

        mRecyclerViewShopType = findViewById(R.id.recyclerview_shop);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewShopType.setLayoutManager(linearLayoutManager);

        mRecyclerViewShopType.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        mShopTypeProductAdapter = new ShopTypeProductAdapter(this);
        mRecyclerViewShopType.setAdapter(mShopTypeProductAdapter);

    }

    private void getShopData(String cid) {
        Map<String, String> map = new HashMap<>();
        map.put("cid", cid);
        iPersenter.getRequest(Apis.URL_PRODUCT_GET_PRODUCT_CATAGORY, map, ShopTypeProductBean.class);
    }

    @Override
    public void onSuccess(Object data) {

        if (data instanceof ShopTypeBean) {
            //获取数据后，展示左侧列表
            ShopTypeBean shopTypeBean = (ShopTypeBean) data;
            mShopTypeAdapter.setData(shopTypeBean.getData());
        } else if (data instanceof ShopTypeProductBean) {
            //获取数据后，展示右侧列表
            ShopTypeProductBean shopTypeProductBean = (ShopTypeProductBean) data;
            mShopTypeProductAdapter.setData(shopTypeProductBean.getData());

            //将右侧列表滚到顶部(这行不加也无所谓)
            mRecyclerViewShopType.scrollToPosition(0);
        }
    }
}
