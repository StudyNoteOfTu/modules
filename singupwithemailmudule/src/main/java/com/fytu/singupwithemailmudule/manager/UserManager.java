package com.fytu.singupwithemailmudule.manager;

public class UserManager {


    //正则表达式判断密码格式 默认6-10字母或数字
    public static boolean checkPsssword(String passcode){
        return matchRegex(passcode, "[0-9a-zA-Z]{6,10}");
    }

    //正则表达式判断邮箱格式(默认)
    public static boolean checkMail(String mail){
        return matchRegex(mail,"\\w+@\\w+(\\.\\w{2,}){1,2}");
    }

    //正则表达式判断
    public static boolean matchRegex(String target,String regex){
        return target.matches(regex);
    }
}
