package com.abstractionizer.login.uuid1.login.services;


import com.abstractionizer.login.uuid1.db.rmdb.entities.User;

import java.util.Optional;

public interface UserRegistrationService {

    boolean isUsernameRegistered(String username);

    void setUserRegisterInfo(String token, User user);

    void deleteUserRegisterInto(String token);

    void setUserRegisteredName(String username);

    void deleteUserRegisteredName(String username);

    void sendEmail(String to, String uuid);

    Optional<User> getUserByToken(String token);

}
