package com.haoxi.xgn.model.mvp;

import com.haoxi.xgn.base.BaseView;
import com.haoxi.xgn.bean.RegistBean;

public interface IRegistView extends BaseView {
    void registSuccess(RegistBean registBean);
    void failture(String msg);
}
