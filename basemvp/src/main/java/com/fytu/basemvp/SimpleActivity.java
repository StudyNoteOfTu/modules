package com.fytu.basemvp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.fytu.basemvp.presenter.SimplePresenter;
import com.fytu.basemvp.view.BaseActivity;
import com.fytu.basemvp.view.ISimpleView;


public class SimpleActivity extends BaseActivity<ISimpleView, SimplePresenter<ISimpleView>> implements ISimpleView {

    TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //tv_test = (TextView) findViewById(R.id.tv_test);
        articlePresenter.fetch();
    }


    @Override
    protected SimplePresenter<ISimpleView> createPresenter() {
        return new SimplePresenter<>();
    }

    @Override
    public void loadSimple() {
        //一般情况下这里是显示进度条
        Toast.makeText(this, "loading...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showSimple(String resArticle) {//就是在主线程中的

//        String b =(Looper.getMainLooper().getThread().getId() == Thread.currentThread().getId())? "yes":"no";
//        Log.d("TestThreadtu",b); // 结果为yes
        tv_test.setText(resArticle);
    }


}
