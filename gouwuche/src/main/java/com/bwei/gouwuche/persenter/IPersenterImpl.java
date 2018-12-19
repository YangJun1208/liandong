package com.bwei.gouwuche.persenter;

import com.bwei.gouwuche.callback.MyCallBack;
import com.bwei.gouwuche.model.IModelImpl;
import com.bwei.gouwuche.view.IView;

import java.util.Map;

public class IPersenterImpl implements IPersenter {

    private IModelImpl iModel;
    private IView mIView;

    public IPersenterImpl(IView iView){
        mIView=iView;
        iModel=new IModelImpl();
    }

    @Override
    public void getRequest(String dataUrl, Map<String, String> params, Class clazz) {
        iModel.getRequest(dataUrl, params, clazz, new MyCallBack() {
            @Override
            public void onCallBack(Object data) {
                mIView.onSuccess(data);
            }
        });
    }
    public void deteach(){
        iModel=null;
        mIView=null;
    }
}
