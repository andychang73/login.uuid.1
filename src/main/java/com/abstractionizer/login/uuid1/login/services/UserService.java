package com.abstractionizer.login.uuid1.login.services;

import com.abstractionizer.login.uuid1.db.rmdb.entities.User;
import com.abstractionizer.login.uuid1.models.bo.UpdateInfoBo;
import com.abstractionizer.login.uuid1.models.dto.UserInfo;

import java.util.Date;
import java.util.Optional;

public interface UserService {

    User create(User user);

    boolean isUserExists(Integer userId, String username);

    Optional<User> getByIdOrUsername(Integer userId, String username);

    void freezeAccount(Integer userId, boolean status);

    void updateLastLoginTime(Integer id, Date now);

    UserInfo updateInfo(Integer userId, UpdateInfoBo bo);

    void changePassword(Integer id, String password);
}
