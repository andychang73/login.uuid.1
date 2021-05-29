package com.abstractionizer.login.uuid1.constants;

public class RedisConstant {

    public static final String USER_REGISTERED_NAME = "LOGIN:UUID:REGISTER:NAME:%s";
    public static final String USER_REGISTER_TOKEN = "LOGIN:UUID:REGISTER:TOKEN:%s";

    public static String getUserRegisteredName(String username){
        return String.format(USER_REGISTERED_NAME, username);
    }

    public static String getUserRegisterToken(String token){
        return String.format(USER_REGISTER_TOKEN, token);
    }
}
