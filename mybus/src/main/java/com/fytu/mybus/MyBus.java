package com.fytu.mybus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 代理类
 */
public class MyBus {
    //清单 用于存储所有的订阅方法
    private Map<Object, List<MethodManager>> map;

    //volatile修饰的变量不允许县城内部缓存以及重新排序，即直接修改内存
    public static volatile MyBus myBus;

    private Handler handler;

    private ExecutorService executorService;

    private MyBus(){
        map = new HashMap<>();
        handler = new Handler(Looper.getMainLooper());//handler是主线程
        executorService = Executors.newCachedThreadPool();//new一个线程池
    }

    public static MyBus getDefault(){
        if(myBus == null){
            synchronized (MyBus.class){
                if(myBus == null){
                    myBus = new MyBus();
                }
            }
        }
        return myBus;
    }

    /**
     * 从activity中拿到符合订阅方法要求的方法，放进map中
     */
    public void register(Object object){
        List<MethodManager> methodList  = map.get(object);
        if(methodList == null){
            methodList = findAnnotationMethod(object);//拿到activity中的所有方法，找到订阅方法
            map.put(object,methodList);
        }
    }

    /**
     * 到activity中找符合订阅参数的方法，也就是有subscribe的方法
     * @param object
     * @return
     */
    private List<MethodManager> findAnnotationMethod(Object object){
        List<MethodManager> methodList = new ArrayList<>();
        //反射获取
        //获取到activity的class对象
        Class<?> clazz = object.getClass();
        //得到class对象中的所有方法
        Method[] methods = clazz.getDeclaredMethods();//把该类所有的method都传进去，不需要把父类的method放进来
        //循环遍历方法的集合
        for(Method method: methods){
            //获取到方法的注解
            Subscribe subscribe = method.getAnnotation(Subscribe.class);
            if(subscribe == null){
                continue;
            }
            //订阅者参数就一个，直接拿第一条
            Class<?>[] parameterTypes = method.getParameterTypes();
            MethodManager methodManager = new MethodManager(parameterTypes[0],subscribe.threadMode(),method);
            methodList.add(methodManager);

        }


        return methodList;
    }

    //接收消息
    /**
     * 接收消息， 转发给订阅方法,要发到所有的订阅者上
     */
    public void post(final Object setter){
        //通过清单 拿到所有的订阅方法
        Set<Object> objectSet = map.keySet();
        //通过key去遍历我们的map
        for(final Object o : objectSet){
            List<MethodManager> methods = map.get(o);//得到订阅方法List
            if(methods != null){
                for (final MethodManager methodManager : methods) {
                    //判断订阅方法的接收类型是否与发布的类型一致
                    if(methodManager.getType().isAssignableFrom(setter.getClass())){
                        //在这里切换线程
                        switch (methodManager.getThreadMode()){
                            case POSTING:
                                //不需要切线程
                                //执行订阅方法
                                invoke(methodManager,o,setter);
                                break;
                            case MAIN:
                                //如果发布者是子线程，要把它切到主线程中
                                //判断当前是否在主线程
                                if(Looper.getMainLooper() == Looper.getMainLooper()){
                                    //执行订阅方法
                                    invoke(methodManager,o,setter);
                                }else{
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            //执行订阅方法
                                            invoke(methodManager,o,setter);
                                        }
                                    });
                                }
                                break;
                            case BACKGROUND:
                                if(Looper.getMainLooper() == Looper.getMainLooper()){
                                    //切换到子线程
                                    executorService.execute(new Runnable() {
                                        @Override
                                        public void run() {
                                            //执行订阅方法
                                            invoke(methodManager,o,setter);
                                        }
                                    });
                                }else{
                                    //执行订阅方法
                                    invoke(methodManager,o,setter);
                                }
                                break;
                            default:
                                break;
                        }



                    }

                }
            }
        }
    }

    /**
     * 执行订阅者方法
     * @param methodManager
     * @param object
     * @param setter
     */
    private void invoke(MethodManager methodManager,Object object,Object setter){
        Method method = methodManager.getMethod();//获得方法本身
        try {
            method.invoke(object,setter);//第一个参数， 在哪个类中执行,如mainActivity,第二个参数为发布的对象
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }





}














