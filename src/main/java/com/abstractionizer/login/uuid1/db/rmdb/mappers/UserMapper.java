package com.abstractionizer.login.uuid1.db.rmdb.mappers;

import com.abstractionizer.login.uuid1.db.rmdb.entities.User;
import com.abstractionizer.login.uuid1.models.bo.UpdateInfoBo;
import com.abstractionizer.login.uuid1.models.dto.UserInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    int countByIdOrUsername(@Param("id") Integer id, @Param("username") String username);

    User getByIdOrUsername(@Param("id") Integer id, @Param("username") String username);

    UserInfo getUserInfoById(@Param("id") Integer id);

    int updateStatus(@Param("id") Integer id, @Param("status") boolean status);

    int updateUserInfo(@Param("id") Integer id, @Param("bo")UpdateInfoBo bo);

    int updateLastLoginTime(@Param("id") Integer userId, @Param("lastLoginTime") Date lastLoginTime);

    int changePassword(@Param("id") Integer id, @Param("password") String password);
}
