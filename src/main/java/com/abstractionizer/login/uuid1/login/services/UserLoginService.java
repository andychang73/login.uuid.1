package com.abstractionizer.login.uuid1.login.services;

import com.abstractionizer.login.uuid1.models.vo.UserInfoVo;

import java.util.Optional;

public interface UserLoginService {

    boolean isUserLoggedIn(Integer userId);

    void setLoggedInUser(Integer userId);

    boolean authenticate(String enteredPassword, String password);

    Long countLoginFailure(String key);

    Optional<String> generateToken();

    Optional<String> getOldTokenIfExists(String token);

    void setUserLoginToken(String token, UserInfoVo userInfoVo);

    void deleteUserLoginToken(String token);
}
