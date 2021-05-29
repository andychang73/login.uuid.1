package com.abstractionizer.login.uuid1.constants;

public class RedisConstant {

    public static final String USER_REGISTERED_NAME = "LOGIN:UUID:REGISTER:NAME:%s";
    public static final String USER_REGISTER_TOKEN = "LOGIN:UUID:REGISTER:TOKEN:%s";
    public static final String USER_LOGGED_IN = "LOGIN:UUID:USER:LOGGED_IN:%s";
    public static final String LOGIN_FAILURE_COUNT = "LOGIN:FAILURE:COUNT:%s";
    public static final String LOGIN_TOKEN = "LOGIN:LOGIN:TOKEN:%s";

    public static String getUserRegisteredName(String username){
        return String.format(USER_REGISTERED_NAME, username);
    }

    public static String getUserRegisterToken(String token){
        return String.format(USER_REGISTER_TOKEN, token);
    }

    public static String getUserLoggedIn(Integer userId){
        return String.format(USER_LOGGED_IN, userId);
    }

    public static String getLoginFailureCount(String username){
        return String.format(LOGIN_FAILURE_COUNT, username);
    }

    public static String getLoginToken(String token){
        return String.format(LOGIN_TOKEN, token);
    }
}
