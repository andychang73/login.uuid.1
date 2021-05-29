package com.abstractionizer.login.uuid1.login.services;

import com.abstractionizer.login.uuid1.db.rmdb.entities.User;

public interface UserService {

    User create(User user);

    boolean isUserExists(Integer userId, String username);
}
