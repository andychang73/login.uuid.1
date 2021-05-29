package com.abstractionizer.login.uuid1.login.businesses.impl;

import com.abstractionizer.login.uuid1.db.rmdb.entities.User;
import com.abstractionizer.login.uuid1.enums.ErrorCode;
import com.abstractionizer.login.uuid1.exceptions.CustomException;
import com.abstractionizer.login.uuid1.login.businesses.UserBusiness;
import com.abstractionizer.login.uuid1.login.services.UserRegistrationService;
import com.abstractionizer.login.uuid1.login.services.UserService;
import com.abstractionizer.login.uuid1.models.bo.UserRegisterBo;
import com.abstractionizer.login.uuid1.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class UserBusinessImpl implements UserBusiness {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Override
    public synchronized void register(UserRegisterBo bo) {
        if(userRegistrationService.isUsernameRegistered(bo.getUsername()) || userService.isUserExists(null, bo.getUsername())){
            throw new CustomException(ErrorCode.USERNAME_EXISTS);
        }

        User user = new User()
                .setUsername(bo.getUsername())
                .setPassword(MD5Util.md5(bo.getPassword()))
                .setEmail(bo.getEmail())
                .setPhone(bo.getPhone() == null ? null : bo.getPhone())
                .setStatus(true);

        String uuid = UUID.randomUUID().toString();
        userRegistrationService.setUserRegisterInfo(uuid, user);
        userRegistrationService.setUserRegisteredName(bo.getUsername());
        userRegistrationService.sendEmail(bo.getEmail(), uuid);
    }
}
