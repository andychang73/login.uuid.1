package com.abstractionizer.login.uuid1.login.businesses.impl;

import com.abstractionizer.login.uuid1.db.rmdb.entities.User;
import com.abstractionizer.login.uuid1.enums.ErrorCode;
import com.abstractionizer.login.uuid1.exceptions.CustomException;
import com.abstractionizer.login.uuid1.login.businesses.UserBusiness;
import com.abstractionizer.login.uuid1.login.services.UserLoginService;
import com.abstractionizer.login.uuid1.login.services.UserRegistrationService;
import com.abstractionizer.login.uuid1.login.services.UserService;
import com.abstractionizer.login.uuid1.models.bo.ChangePasswordBo;
import com.abstractionizer.login.uuid1.models.bo.UpdateInfoBo;
import com.abstractionizer.login.uuid1.models.bo.UserLoginBo;
import com.abstractionizer.login.uuid1.models.bo.UserRegisterBo;
import com.abstractionizer.login.uuid1.models.vo.LoginSuccessfulVo;
import com.abstractionizer.login.uuid1.models.dto.UserInfo;
import com.abstractionizer.login.uuid1.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Objects;
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
    public UserInfo validate(String token) {
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

    @Override
    public void changePassword(Integer userId, ChangePasswordBo bo) {
        if(Objects.equals(bo.getOldPassword(), bo.getNewPassword())){
            throw new CustomException(ErrorCode.NEW_OLD_PASSWORD_SAME);
        }
        if(!Objects.equals(bo.getNewPassword(), bo.getConfirmPassword())){
            throw new CustomException(ErrorCode.NEW_PASSWORD_INCONSISTENCY);
        }

        User user = userService.getByIdOrUsername(userId, null).orElseThrow(()-> new CustomException(ErrorCode.USER_NOT_FOUND));
        if(!Objects.equals(user.getPassword(), MD5Util.md5(bo.getOldPassword()))){
            throw new CustomException(ErrorCode.INVALID_CREDENTIAL);
        }

        userService.changePassword(userId, MD5Util.md5(bo.getConfirmPassword()));
    }

    @Override
    public UserInfo updateInfo(Integer userId, UpdateInfoBo bo) {
        if(Objects.isNull(bo.getUsername()) && Objects.isNull(bo.getEmail()) && Objects.isNull(bo.getPhone())){
            throw new CustomException(ErrorCode.NO_DATA_TO_UPDATE);
        }
        return userService.updateInfo(userId, bo);
    }

    @Override
    public void logout(Integer userId, String token) {
        userLoginService.deleteLoggedInUser(userId);
        userLoginService.deleteUserLoginToken(token);
    }

    private UserInfo getUserInfoVo(User user){
        return new UserInfo()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setEmail(user.getEmail())
                .setPhone(user.getPhone() == null ? null : user.getPhone())
                .setStatus(user.getStatus());
    }
}
