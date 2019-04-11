package com.fytu.mybus;


import java.lang.reflect.Method;

/**
 * 方法的封装对象，用来封装符合我们订阅方法（打上Subscribe标记的）要求的方法
 */
public class MethodManager {

    //1.方法传入的参数类型,不知道类型，我们就用泛型
    private Class<?> type;

    //2.方法上面的注解参数
    private ThreadMode threadMode;

    //3.方法本身
    private Method method;

    public MethodManager(Class<?> type, ThreadMode threadMode, Method method) {
        this.type = type;
        this.threadMode = threadMode;
        this.method = method;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}
