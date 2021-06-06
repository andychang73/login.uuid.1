package com.abstractionizer.login.uuid1.login.services.impl;

import com.abstractionizer.login.uuid1.db.rmdb.entities.User;
import com.abstractionizer.login.uuid1.db.rmdb.mappers.UserMapper;
import com.abstractionizer.login.uuid1.enums.ErrorCode;
import com.abstractionizer.login.uuid1.exceptions.CustomException;
import com.abstractionizer.login.uuid1.login.services.UserService;
import com.abstractionizer.login.uuid1.models.bo.UpdateInfoBo;
import com.abstractionizer.login.uuid1.models.dto.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User create(User user) {
        if(userMapper.insert(user) != 1){
            throw new CustomException(ErrorCode.DATA_INSERT_FAILED);
        }
        return user;
    }

    @Override
    public boolean isUserExists(Integer userId, String username) {
        return userMapper.countByIdOrUsername(userId, username) > 0;
    }

    @Override
    public Optional<User> getByIdOrUsername(Integer userId, String username) {
        return Optional.ofNullable(userMapper.getByIdOrUsername(userId, username));
    }

    @Override
    public void freezeAccount(Integer userId, boolean status) {
        if(userMapper.updateStatus(userId, status) != 1){
            throw new CustomException(ErrorCode.DATA_UPDATE_FAILED);
        }
    }

    @Override
    public void updateLastLoginTime(Integer id, Date now) {
        if(userMapper.updateLastLoginTime(id, now) != 1){
            throw new CustomException(ErrorCode.DATA_UPDATE_FAILED);
        }
    }

    @Override
    public UserInfo updateInfo(Integer userId, UpdateInfoBo bo) {
        if(userMapper.updateUserInfo(userId, bo) != 1){
            throw new CustomException(ErrorCode.DATA_UPDATE_FAILED);
        }
        return userMapper.getUserInfoById(userId);
    }

    @Override
    public void changePassword(Integer id, String password) {
        if(userMapper.changePassword(id, password) != 1){
            throw new CustomException(ErrorCode.DATA_UPDATE_FAILED);
        }
    }
}
