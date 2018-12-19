package com.bwei.gouwuche;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bwei.gouwuche.adapter.ProductsAdapter;
import com.bwei.gouwuche.bean.ShopBean;

import java.util.ArrayList;
import java.util.List;

public class CustomCounterView extends RelativeLayout implements View.OnClickListener {

    private Context context;
    private EditText editCar;

    public CustomCounterView(Context context) {
        super(context);
        init(context);
    }

    public CustomCounterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomCounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context=context;
        View view = View.inflate(context, R.layout.shop_car_price_layout, null);
        ImageView addIamge = (ImageView) view.findViewById(R.id.add_car);
        ImageView jianIamge = (ImageView) view.findViewById(R.id.jian_car);
        editCar = (EditText) view.findViewById(R.id.edit_shop_car1);
        addIamge.setOnClickListener(this);
        jianIamge.setOnClickListener(this);
        addView(view);

        editCar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //TODO:改变数量

                try {
                    num= Integer.valueOf(String.valueOf(s));
                    list.get(position).setNum(num);
                }catch (Exception e){
                    list.get(position).setNum(0);
                }

                if(listener!=null){
                    listener.callBack();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private int num;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_car:
                //改变数量，设置数量，改变对象内容，回调，局部刷新
                num++;
                editCar.setText(num + "");
                list.get(position).setNum(num);
                listener.callBack();
                productsAdapter.notifyItemChanged(position);
                break;
            case R.id.jian_car:
                if (num > 1) {
                    num--;
                } else {
                    Toast.makeText(context, "我是有底线的", Toast.LENGTH_LONG).show();
                }
                editCar.setText(num + "");
                list.get(position).setNum(num);
                listener.callBack();
                productsAdapter.notifyItemChanged(position);
                break;
            default:
                break;
        }
    }

    //传递的数据
    private List<ShopBean.DataBean.ListBean> list = new ArrayList<>();
    private int position;
    private ProductsAdapter productsAdapter;

    public void setData(ProductsAdapter productsAdapter, List<ShopBean.DataBean.ListBean> list, int i) {
        this.list = list;
        this.productsAdapter = productsAdapter;
        position = i;
        num = list.get(i).getNum();
        editCar.setText(num + "");
    }


    private CallBackListener listener;

    public void setOnCallBack(CallBackListener listener) {
        this.listener = listener;
    }

    public interface CallBackListener {
        void callBack();
    }
}
