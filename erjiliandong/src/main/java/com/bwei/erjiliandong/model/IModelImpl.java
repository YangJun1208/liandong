package com.bwei.erjiliandong.model;

import com.bwei.erjiliandong.callback.MyCallBack;
import com.bwei.erjiliandong.okhttp.ICallBack;
import com.bwei.erjiliandong.okhttp.OkHttpUtils;

import java.util.Map;

public class IModelImpl implements IModel {
    @Override
    public void getRequest(String dataUrl, Map<String, String> params, Class clazz, final MyCallBack callBack) {
        OkHttpUtils.getInstance().postRequeue(dataUrl, params, clazz, new ICallBack() {
            @Override
            public void onResponce(Object obj) {
                callBack.onCallBack(obj);
            }

            @Override
            public void onFailure(Exception e) {
                callBack.onCallBack(e);
            }
        });
    }
}
