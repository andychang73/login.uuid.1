package com.abstractionizer.login.uuid1.login.services;


import com.abstractionizer.login.uuid1.db.rmdb.entities.User;

public interface UserRegistrationService {

    boolean isUsernameRegistered(String username);

    void setUserRegisterInfo(String token, User user);

    void setUserRegisteredName(String username);

    void sendEmail(String to, String uuid);

}
