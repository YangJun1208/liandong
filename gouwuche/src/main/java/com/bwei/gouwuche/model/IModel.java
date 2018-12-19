package com.bwei.gouwuche.model;

import com.bwei.gouwuche.callback.MyCallBack;

import java.util.Map;

public interface IModel {
    void getRequest(String dataUrl, Map<String,String> params, Class clazz, MyCallBack callBack);
}
