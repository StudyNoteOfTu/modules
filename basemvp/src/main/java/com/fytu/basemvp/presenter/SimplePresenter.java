package com.fytu.basemvp.presenter;


import com.fytu.basemvp.model.ISimpleModel;
import com.fytu.basemvp.model.SimpleModelImpl;
import com.fytu.basemvp.view.ISimpleView;

/**
 * 表示层
 */
public class SimplePresenter<T extends ISimpleView> extends BasePresenter<T>{
    //1.View层的引用
//    IArticleView articleView;

    //2.model层的应用
    ISimpleModel simpleModel = new SimpleModelImpl();
    //3.构造方法
    public SimplePresenter(){//传参不用model，而要view 因为开始视图层会是一个activity，activity是不会new出来的而model是可以new出来的
//        this.articleView = view;
//        mViewRef = new WeakReference<T>(view);
        //现在两个引用都拉在手上了
    }
    //笔记： 这个view是可以外部传进来的，而model是需要（也可以）new一个出来的，我们就要在model中加个实现


    // model获得数据，通过回调 数据给到presenter， presenter再调用view的引用（通过fetch（）传给activity）view层拿到数据

    //4.执行操作(ui逻辑)
    public void fetch(){//拿数据
        if(mViewRef.get() != null){
            mViewRef.get().loadSimple();//获取数据
            if(simpleModel != null){
                simpleModel.loadSimple(new ISimpleModel.SimpleOnLoadListener() {//加载数据
                    @Override
                    public void onComplete(String result) { //把model的loadArticle的数据回调到这里

                        if(mViewRef.get() != null){
                            mViewRef.get().showSimple(result);//view层拿到数据
                        }

                    }
                });
            }
        }
    }




}
