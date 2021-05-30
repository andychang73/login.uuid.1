package com.abstractionizer.login.uuid1.login.services;

import com.abstractionizer.login.uuid1.models.dto.UserInfo;

import java.util.Optional;

public interface UserLoginService {

    boolean isUserLoggedIn(Integer userId);

    void setLoggedInUser(Integer userId);

    void deleteLoggedInUser(Integer userId);

    boolean authenticate(String enteredPassword, String password);

    Long countLoginFailure(String key);

    Optional<String> generateToken();

    Optional<String> getOldTokenIfExists(String token);

    void setUserLoginToken(String token, UserInfo userInfo);

    Optional<UserInfo> getUserInfoByToken(String token);

    void deleteUserLoginToken(String token);
}
