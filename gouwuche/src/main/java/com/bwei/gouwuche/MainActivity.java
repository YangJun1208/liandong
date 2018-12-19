package com.bwei.gouwuche;

import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.bwei.gouwuche.adapter.ShopAdapter;
import com.bwei.gouwuche.bean.ShopBean;
import com.bwei.gouwuche.persenter.IPersenterImpl;
import com.bwei.gouwuche.view.IView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements IView,View.OnClickListener {

    private RecyclerView recyclerView;
    private CheckBox iv_cricle;
    private TextView all_price, sum_price_txt;
    private ShopAdapter shopAdapter;
    private IPersenterImpl iPersenter;
    private List<ShopBean.DataBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycle);
        iv_cricle = findViewById(R.id.iv_cricle);
        iPersenter = new IPersenterImpl(this);
        all_price = findViewById(R.id.all_price);
        sum_price_txt = findViewById(R.id.sum_price_txt);

        iv_cricle.setOnClickListener(this);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        shopAdapter = new ShopAdapter(this);
        recyclerView.setAdapter(shopAdapter);

        shopAdapter.setListener(new ShopAdapter.ShopCallBackListener() {
            @Override
            public void callBack(List<ShopBean.DataBean> list) {
                int num = 0;
                double price = 0;
                int totlaNum = 0;
                for (int a = 0; a < list.size(); a++) {
                    List<ShopBean.DataBean.ListBean> beans = list.get(a).getList();
                    for (int i = 0; i < beans.size(); i++) {
                        totlaNum= totlaNum + beans.get(i).getNum();
                        if (beans.get(i).isCheck()) {
                            price=price+(beans.get(i).getPrice()*beans.get(i).getNum());
                            num=num+beans.get(i).getNum();
                        }
                    }
                }
                if (num < totlaNum) {
                    //不是全部选中
                    iv_cricle.setChecked(false);
                } else {
                    iv_cricle.setChecked(true);
                }
                sum_price_txt.setText("合计:" + price);
                all_price.setText("去结算:(" + num + ")");
            }
        });

        getData();
    }

    private void getData() {
        Map<String, String> map = new HashMap<>();
        map.put("uid", "71");
        iPersenter.getRequest(Apis.TYPE_TITLE, map, ShopBean.class);
    }

    @Override
    public void onSuccess(Object data) {
        if (data instanceof ShopBean) {
            ShopBean shopBean = (ShopBean) data;
            mList = shopBean.getData();
            if (mList != null) {
                mList.remove(0);
                shopAdapter.setList(mList);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cricle:
                checkSeller(iv_cricle.isChecked());
                shopAdapter.notifyDataSetChanged();
                break;
            default:
                break;
        }
    }

    /**
     * 修改选中状态，获取价格和数量
     */
    private void checkSeller(boolean bool) {
        double totalPrice = 0;
        int num = 0;
        for (int a = 0; a < mList.size(); a++) {
            //遍历商家，改变状态
            ShopBean.DataBean dataBean = mList.get(a);
            dataBean.setCheck(bool);

            List<ShopBean.DataBean.ListBean> listAll = mList.get(a).getList();
            for (int i = 0; i < listAll.size(); i++) {
                //遍历商品，改变状态
                listAll.get(i).setCheck(bool);
                totalPrice = totalPrice + (listAll.get(i).getPrice() * listAll.get(i).getNum());
                num = num + listAll.get(i).getNum();
            }
        }
        if (bool) {
            sum_price_txt.setText("合计：" + totalPrice);
            all_price.setText("去结算(" + num + ")");
        } else {
            sum_price_txt.setText("合计：0.00");
            all_price.setText("去结算(0)");
        }

    }
}