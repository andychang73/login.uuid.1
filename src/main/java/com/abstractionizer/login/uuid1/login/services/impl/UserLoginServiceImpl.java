package com.abstractionizer.login.uuid1.login.services.impl;

import com.abstractionizer.login.uuid1.constants.RedisConstant;
import com.abstractionizer.login.uuid1.enums.ErrorCode;
import com.abstractionizer.login.uuid1.exceptions.CustomException;
import com.abstractionizer.login.uuid1.login.services.UserLoginService;
import com.abstractionizer.login.uuid1.models.dto.UserInfo;
import com.abstractionizer.login.uuid1.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.abstractionizer.login.uuid1.constants.RedisConstant.getLoginToken;
import static com.abstractionizer.login.uuid1.constants.RedisConstant.getUserLoggedIn;

@Slf4j
@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private RedisUtil redisUtil;

    private final Long tokenValidity = 5L;

    @Override
    public boolean isUserLoggedIn(Integer userId) {
        return redisUtil.isKeyExists(getUserLoggedIn(userId));
    }

    @Override
    public void setLoggedInUser(Integer userId) {
        if(!redisUtil.set(getUserLoggedIn(userId), userId, tokenValidity, TimeUnit.MINUTES)){
            throw new CustomException(ErrorCode.DATA_INSERT_FAILED);
        }
    }

    @Override
    public void deleteLoggedInUser(Integer userId) {
        if(!redisUtil.deleteKey(getUserLoggedIn(userId))){
            throw new CustomException(ErrorCode.DATA_INSERT_FAILED);
        }
    }

    @Override
    public boolean authenticate(String enteredPassword, String password) {
        if(!Objects.equals(enteredPassword, password)){
            return false;
        }
        return true;
    }

    @Override
    public Long countLoginFailure(String key) {
        Long count = 1L;
        Long duration = 5L;
        if(redisUtil.isKeyExists(key)){
            return redisUtil.increment(key, count);
        }
        redisUtil.set(key, count, duration, TimeUnit.MINUTES);
        return count;
    }

    @Override
    public Optional<String> generateToken() {
        String token;
        int count = 0;

        while(true){
            count++;
            token = UUID.randomUUID().toString();
            if(!redisUtil.isKeyExists(RedisConstant.getLoginToken(token))){
                break;
            }
            token = null;
            if(count >= 3){
                break;
            }
        }
        return Optional.ofNullable(token);
    }

    @Override
    public Optional<String> getOldTokenIfExists(String token) {
        return Optional.ofNullable(redisUtil.get(RedisConstant.getLoginToken(token), String.class));
    }

    @Override
    public void setUserLoginToken(String token, UserInfo userInfo) {
        if(!redisUtil.set(getLoginToken(token), userInfo, tokenValidity, TimeUnit.MINUTES)){
            throw new CustomException(ErrorCode.DATA_INSERT_FAILED);
        }
    }

    @Override
    public Optional<UserInfo> getUserInfoByToken(String token) {
        return Optional.ofNullable(redisUtil.get(getLoginToken(token), UserInfo.class));
    }

    @Override
    public void deleteUserLoginToken(String token) {
        if(!redisUtil.deleteKey(getLoginToken(token))){
            throw new CustomException(ErrorCode.DATA_DELETION_FAILED);
        }
    }
}
