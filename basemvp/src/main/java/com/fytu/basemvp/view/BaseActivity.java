package com.fytu.basemvp.view;

import android.app.Activity;
import android.os.Bundle;

import com.fytu.basemvp.presenter.BasePresenter;


public abstract class BaseActivity<V,T extends BasePresenter<V>> extends Activity {
    //表示层的引用
    public T articlePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //view接口与preference牵手
        articlePresenter = createPresenter();
        articlePresenter.attachView((V)this);
    }

    //表示层的选择
    protected abstract T createPresenter();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        articlePresenter.deattachView();
    }
}
