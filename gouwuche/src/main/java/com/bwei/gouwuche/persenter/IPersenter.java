package com.bwei.gouwuche.persenter;

import java.util.Map;

public interface IPersenter {
    void getRequest(String dataUrl, Map<String,String> params,Class clazz);
}
