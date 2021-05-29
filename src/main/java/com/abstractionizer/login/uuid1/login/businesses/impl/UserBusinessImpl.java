package com.abstractionizer.login.uuid1.login.businesses.impl;

import com.abstractionizer.login.uuid1.db.rmdb.entities.User;
import com.abstractionizer.login.uuid1.enums.ErrorCode;
import com.abstractionizer.login.uuid1.exceptions.CustomException;
import com.abstractionizer.login.uuid1.login.businesses.UserBusiness;
import com.abstractionizer.login.uuid1.login.services.UserLoginService;
import com.abstractionizer.login.uuid1.login.services.UserRegistrationService;
import com.abstractionizer.login.uuid1.login.services.UserService;
import com.abstractionizer.login.uuid1.models.bo.UserLoginBo;
import com.abstractionizer.login.uuid1.models.bo.UserRegisterBo;
import com.abstractionizer.login.uuid1.models.vo.LoginSuccessfulVo;
import com.abstractionizer.login.uuid1.models.vo.UserInfoVo;
import com.abstractionizer.login.uuid1.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static com.abstractionizer.login.uuid1.constants.RedisConstant.getLoginFailureCount;

@Slf4j
@Service
public class UserBusinessImpl implements UserBusiness {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRegistrationService userRegistrationService;

    @Autowired
    private UserLoginService userLoginService;

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

    @Override
    public UserInfoVo validate(String token) {
        if(token.isEmpty()){
            throw new CustomException(ErrorCode.INVALID_TOKEN);
        }
        User user = userRegistrationService.getUserByToken(token).orElseThrow(()-> new CustomException(ErrorCode.ACCOUNT_VALIDATION_EXPIRED));

        userService.create(user);
        userRegistrationService.deleteUserRegisterInto(token);
        userRegistrationService.deleteUserRegisteredName(user.getUsername());

        return getUserInfoVo(user);
    }

    @Override
    public LoginSuccessfulVo login(UserLoginBo bo) {
        User user = userService.getByIdOrUsername(null, bo.getUsername()).orElseThrow(() -> new CustomException(ErrorCode.INVALID_CREDENTIAL));

        if(!user.getStatus()){
            throw new CustomException(ErrorCode.ACCOUNT_FROZEN);
        }

        if(!userLoginService.authenticate(MD5Util.md5(bo.getPassword()), user.getPassword())){
            if(userLoginService.countLoginFailure(getLoginFailureCount(bo.getUsername())) >= 3){
                userService.freezeAccount(user.getId(), false);
                throw new CustomException(ErrorCode.ACCOUNT_FROZEN);
            }
            throw new CustomException(ErrorCode.INVALID_CREDENTIAL);
        }

        if(userLoginService.isUserLoggedIn(user.getId())){
            throw new CustomException(ErrorCode.USER_LOGGED_IN);
        }

        final String token = userLoginService.generateToken().orElseThrow(() -> new CustomException(ErrorCode.TOKEN_GENERATION_FAILED));
        userLoginService.getOldTokenIfExists(token).ifPresent(t -> userLoginService.deleteUserLoginToken(t));

        userLoginService.setUserLoginToken(token, getUserInfoVo(user));
        userLoginService.setLoggedInUser(user.getId());
        userService.updateLastLoginTime(user.getId(), new Date());

        return new LoginSuccessfulVo(token);
    }

    private UserInfoVo getUserInfoVo(User user){
        return new UserInfoVo()
                .setUserId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setPhone(user.getPhone() == null ? null : user.getPhone())
                .setStatus(user.getStatus());
    }
}
